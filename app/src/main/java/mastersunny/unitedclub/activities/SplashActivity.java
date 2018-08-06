package mastersunny.unitedclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;


/**
 * Created by ASUS on 1/29/2018.
 */

public class SplashActivity extends AppCompatActivity {

    public String TAG = SplashActivity.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 100;
    private Handler handler;
    public static int APP_REQUEST_CODE = 99;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);

//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo("com.inflack.unitel", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Constants.("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("name not found", e.toString());
//        } catch (Exception e) {
//            Log.e("name not found", e.toString());
//        }

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                AccessToken accessToken = AccountKit.getCurrentAccessToken();

//                if (accessToken != null) {
//                    Constants.debugLog(TAG, accessToken.getToken());
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mainIntent);
                finish();
//                } else {
//                    phoneLogin();
//                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void phoneLogin() {
        final Intent intent = new Intent(SplashActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();

                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }

            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}