package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static String KEY = "key";
    public static String VALUE = "value";
    private TextView input_text, input_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        KEY = getIntent().getStringExtra(KEY);
        VALUE = getIntent().getStringExtra(VALUE);
    }

    public static void start(Context context, String key, String value) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra(KEY, key);
        intent.putExtra(VALUE, value);
        context.startActivity(intent);
    }

    private void initLayout() {
        input_text = findViewById(R.id.input_text);
        input_value = findViewById(R.id.input_value);
        input_text.setText(KEY);
        input_value.setText(VALUE);

        findViewById(R.id.back_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                EditProfileActivity.this.finish();
                break;
        }
    }
}
