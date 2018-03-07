package mastersunny.unitedclub.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;

import mastersunny.unitedclub.Model.RestModel;
import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.CircleImageView;
import mastersunny.unitedclub.utils.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView first_name, last_name, email;
    private ImageView change_profile_image;
    private CircleImageView profile_image;
    public static final int PICK_IMAGE = 1;
    private String TAG = "EditProfileActivity";
    private UserDTO userDTO;
    private Button save_change;
    private ApiInterface apiInterface;

    public static void start(Context context, UserDTO userDTO) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.USER_DTO, userDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getIntentData();
        initLayout();
        if (userDTO != null) {
            updateInfo();
        } else {
            loadData();
        }
    }

    private void getIntentData() {
        userDTO = (UserDTO) getIntent().getSerializableExtra(Constants.USER_DTO);
    }

    private void initLayout() {
        save_change = findViewById(R.id.save_change);
        save_change.setOnClickListener(this);
        first_name = findViewById(R.id.first_name);
        first_name.setTypeface(Constants.getMediumFace(this));
        last_name = findViewById(R.id.last_name);
        last_name.setTypeface(Constants.getMediumFace(this));
        email = findViewById(R.id.email);
        email.setTypeface(Constants.getMediumFace(this));
        change_profile_image = findViewById(R.id.change_profile_image);
        change_profile_image.setOnClickListener(this);
        profile_image = findViewById(R.id.profile_image);

        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.change_phone_number).setOnClickListener(this);
    }

    private void updateInfo() {
        first_name.setText(userDTO.getFirstName());
        last_name.setText(userDTO.getLastName());
        email.setText(userDTO.getEmail());
        if (!TextUtils.isEmpty(userDTO.getImgUrl())) {
            String imgUrl = ApiClient.BASE_URL + "" + userDTO.getImgUrl();
            Constants.loadImage(EditProfileActivity.this, imgUrl, profile_image);
        }
    }

    private static void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.permission_storage))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    Constants.REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != Constants.REQUEST_WRITE_EXTERNAL_STORAGE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showPhotoChooserDialog();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                break;
            case R.id.back_button:
                EditProfileActivity.this.finish();
                break;
            case R.id.change_phone_number:
                startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.change_profile_image:
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showPhotoChooserDialog();
                } else {
                    requestPermission(this);
                }
                break;
            case R.id.save_change:
                saveUserInfo();
                break;
        }
    }

    private void saveUserInfo() {
        String firstName = first_name.getText().toString().trim();
        String lastName = last_name.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();

        if (checkString(firstName) || checkString(lastName) || checkString(emailAddress)) {
            Toast.makeText(EditProfileActivity.this,
                    " Firstname, Lastname and Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            apiInterface.updateUserInfo(firstName, lastName, emailAddress,
                    userDTO.getPhoneNumber(), Constants.accessToken).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    Constants.debugLog(TAG, response + "");
                    if (response != null && response.isSuccessful() && response.body().getMetaData().isData()) {
                        userDTO = response.body().getUserDTO();
                        updateInfo();
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    private boolean checkString(String string) {
        if (TextUtils.isEmpty(string)) {
            return true;
        }
        return false;
    }

    private void showPhotoChooserDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_choose_options, null);
        alertDialog.setView(view);

        TextView take_photo = view.findViewById(R.id.take_photo);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                }

            }
        });
        TextView browse_photo = view.findViewById(R.id.browse_photo);
        browse_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);

            }
        });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                String rootPath = Constants.getRootDirectory();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                File destFile = new File(rootPath + File.separator + timestamp + ".jpg");
                FileOutputStream out = null;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    out = new FileOutputStream(destFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
                    sendToServer(destFile);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendToServer(File destFile) {
        try {
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), destFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("upload", destFile.getName(), reqFile);
            RequestBody accessToken = RequestBody.create(MediaType.parse("text/plain"), Constants.accessToken);
            apiInterface.updateProfileImage(image, accessToken).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    if (response != null && response.isSuccessful() && response.body() != null) {
                        Constants.debugLog(TAG, response.body().getUserDTO().toString());
                        Constants.loadImage(EditProfileActivity.this, ApiClient.BASE_URL + response.body().getUserDTO().getImgUrl(),
                                profile_image);
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    private void loadData() {

    }
}
