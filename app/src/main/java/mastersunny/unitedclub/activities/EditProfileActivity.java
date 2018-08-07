package mastersunny.unitedclub.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import mastersunny.unitedclub.models.RestModel;
import mastersunny.unitedclub.models.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
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
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;
    private String TAG = "EditProfileActivity";
    private UserDTO userDTO;
    private Button save_change;
    private ApiInterface apiInterface;
    int PERMISSION_ALL = 1001;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressBar);
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
        first_name.setText(userDTO.getName());
        last_name.setText(userDTO.getName());
        email.setText(userDTO.getEmail());
        if (!TextUtils.isEmpty(userDTO.getProfileImage())) {
            String imgUrl = ApiClient.BASE_URL + "" + userDTO.getProfileImage();
            Constants.loadImage(EditProfileActivity.this, imgUrl, profile_image);
        }
    }

    private void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.permission_storage))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_ALL && requestCode != RESULT_OK) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            showPhotoChooserDialog();
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
                startActivity(new Intent(EditProfileActivity.this, ChangePhoneActivity.class));
                break;
            case R.id.change_profile_image:
                if (!hasPermissions(this, PERMISSIONS)) {
                    requestPermission(this);
                } else {
                    showPhotoChooserDialog();
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
            progressBar.setVisibility(View.VISIBLE);
            apiInterface.updateUserInfo(firstName, lastName, emailAddress,
                    userDTO.getPhoneNumber(), Constants.accessToken).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    progressBar.setVisibility(View.GONE);
                    Constants.debugLog(TAG, response + "");
                    if (response != null && response.isSuccessful() && response.body().getMetaData().isData()) {
                        userDTO = response.body().getUserDTO();
                        updateInfo();
                        Toast.makeText(EditProfileActivity.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Constants.debugLog(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.photo_choose_options);

        TextView take_photo = dialog.findViewById(R.id.take_photo);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    return;
                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        TextView browse_photo = dialog.findViewById(R.id.browse_photo);
        browse_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);

            }
        });
        dialog.show();
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
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                String rootPath = Constants.getRootDirectory();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                File destFile = new File(rootPath + File.separator + timestamp + ".jpg");
                FileOutputStream out = null;
                try {
                    Bundle extras = resultData.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
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
                    if (response != null && response.isSuccessful() && response.body() != null &&
                            response.body().getMetaData().isSuccess()) {
                        Constants.debugLog(TAG, response.body().getMetaData().toString());
                        Constants.loadImage(EditProfileActivity.this, ApiClient.BASE_URL + response.body().getUserDTO().getProfileImage(),
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
