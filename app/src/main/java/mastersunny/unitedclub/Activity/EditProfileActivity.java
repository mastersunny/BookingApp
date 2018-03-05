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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView first_name, last_name, email;
    private ImageView change_cover_image, cover_image;
    public static final int PICK_IMAGE = 1;
    private String TAG = "EditProfileActivity";
    private File destFile;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);

        initLayout();
        updateLayout();
    }

    private void initLayout() {
        first_name = findViewById(R.id.first_name);
        first_name.setTypeface(Constants.getMediumFace(this));
        last_name = findViewById(R.id.last_name);
        last_name.setTypeface(Constants.getMediumFace(this));
        email = findViewById(R.id.email);
        email.setTypeface(Constants.getMediumFace(this));
        change_cover_image = findViewById(R.id.change_cover_image);
        change_cover_image.setOnClickListener(this);
        cover_image = findViewById(R.id.cover_image);

        updateInfo();

        LinearLayout linearLayout = findViewById(R.id.profile_info_layout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            view.setOnClickListener(this);
        }

        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.change_phone_number).setOnClickListener(this);
    }

    private void updateLayout() {
        first_name.setText(preferences.getString(Constants.FIRST_NAME, ""));
        last_name.setText(preferences.getString(Constants.LAST_NAME, ""));
        email.setText(preferences.getString(Constants.EMAIL, ""));
    }

    private void updateInfo() {
        if (destFile != null && destFile.exists()) {
            Constants.loadImage(EditProfileActivity.this, destFile.getAbsolutePath(), cover_image);
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
            case R.id.change_cover_image:
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showPhotoChooserDialog();
                } else {
                    requestPermission(this);
                }
                break;
        }
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
                Log.i(TAG, "Uri: " + uri.toString());

                try {
                    String rootPath = Constants.getRootDirectory();
                    deleteFiles(new File(rootPath));
                    destFile = new File(rootPath + File.separator + "cover_image" + ".jpg");
                    destFile.createNewFile();
                    new AsyncCaller().execute(new File(getRealPathFromURI(uri)), destFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    void deleteFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                child.delete();
            }
        }
    }

    private class AsyncCaller extends AsyncTask<File, Void, Boolean> {
        ProgressDialog pdLoading = new ProgressDialog(EditProfileActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("Loading...");
            pdLoading.show();
        }

        @Override
        protected Boolean doInBackground(File... files) {
            try {
                File sourceFile = files[0];
                File destFile = files[1];
                Log.d(TAG, "" + sourceFile + " " + destFile);
                if (!files[0].exists()) {
                    return false;
                }

                FileChannel source = null;
                FileChannel destination = null;
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();
                if (destination != null && source != null) {
                    destination.transferFrom(source, 0, source.size());
                }
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Log.d(TAG, "result " + result + " destFile " + destFile.getAbsolutePath());
            if (result) {
                Constants.loadImage(EditProfileActivity.this, destFile.getAbsolutePath(), cover_image);
            }

            pdLoading.dismiss();
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
