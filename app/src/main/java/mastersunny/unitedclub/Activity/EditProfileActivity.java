package mastersunny.unitedclub.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView first_name, last_name, phone_number, email, password;
    private ImageView change_cover_image;
    public static final int PICK_IMAGE = 1;
    private String TAG = "EditProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        initLayout();
    }

    private void initLayout() {
        first_name = findViewById(R.id.first_name);
        first_name.setTypeface(Constants.getMediumFace(this));
        last_name = findViewById(R.id.last_name);
        last_name.setTypeface(Constants.getMediumFace(this));
        phone_number = findViewById(R.id.phone_number);
        phone_number.setTypeface(Constants.getMediumFace(this));
        email = findViewById(R.id.email);
        email.setTypeface(Constants.getMediumFace(this));
        change_cover_image = findViewById(R.id.change_cover_image);
        change_cover_image.setOnClickListener(this);

        LinearLayout linearLayout = findViewById(R.id.profile_info_layout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            view.setOnClickListener(this);
        }

        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.change_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                break;
            case R.id.back_button:
                EditProfileActivity.this.finish();
                break;
            case R.id.change_password:
                startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.change_cover_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

            } catch (Exception e) {

            }
            //TODO: action
        }
    }
}
