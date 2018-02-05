package mastersunny.unitedclub.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView first_name, last_name, phone_number, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

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
        password = findViewById(R.id.password);
        password.setTypeface(Constants.getMediumFace(this));

        LinearLayout linearLayout = findViewById(R.id.profile_info_layout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            view.setOnClickListener(this);
        }

        findViewById(R.id.back_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                break;
            case R.id.first_name_layout:
                EditProfileActivity.start(v.getContext(), "First Name", "Doe");
                break;
            case R.id.last_name_layout:
                EditProfileActivity.start(v.getContext(), "Last Name", "Doe");
                break;
            case R.id.phone_number_layout:
                EditProfileActivity.start(v.getContext(), "Phone Number", "01728923792");
                break;
            case R.id.email_layout:
                EditProfileActivity.start(v.getContext(), "Email", "Doe@gmail.com");
                break;
            case R.id.password_layout:
                EditProfileActivity.start(v.getContext(), "Enter a new password", "Doe");
                break;
            case R.id.back_button:
                ProfileActivity.this.finish();
                break;
        }
    }
}
