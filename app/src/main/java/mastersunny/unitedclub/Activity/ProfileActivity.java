package mastersunny.unitedclub.Activity;

import android.content.Intent;
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
                ProfileActivity.this.finish();
                break;
            case R.id.change_password:
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
                break;
        }
    }
}
