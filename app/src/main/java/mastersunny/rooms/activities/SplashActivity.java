package mastersunny.rooms.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.child_view)
    View child_view;

    @BindView(R.id.parent_view)
    View parent_view;

    private ObjectAnimator animation;

    private Handler handler = new Handler();

    AccessToken accessToken = AccountKit.getCurrentAccessToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {

        parent_view.post(new Runnable() {
            @Override
            public void run() {
                animation = ObjectAnimator.ofFloat(child_view, "translationX", (parent_view.getWidth() + child_view.getWidth()));
                animation.setDuration(1250);
                animation.setRepeatCount(Animation.INFINITE);
                animation.start();
            }
        });

        handler.postDelayed(splashRunnable, 2500);

    }

    Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {
            if (accessToken != null) {
                goToMyLoggedInActivity();
            } else {
                Intent intent = new Intent(SplashActivity.this, SplashActivity2.class);
                startActivity(intent);
                finish();
            }


        }
    };

    private void cancelAnimation() {
        if (animation != null) {
            animation.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(splashRunnable);
        cancelAnimation();
    }

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
