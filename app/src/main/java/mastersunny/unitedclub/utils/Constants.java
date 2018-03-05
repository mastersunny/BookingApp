package mastersunny.unitedclub.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.security.PublicKey;

import mastersunny.unitedclub.Listener.ClickListener;
import mastersunny.unitedclub.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 1/20/2018.
 */

public class Constants {

    public static boolean debugOn = true;

    public static String TITLE = "title";
    public static String MESSAGE = "message";
    public static String TRANSACTION_ID = "transaction_id";
    public static String IMG_URL = "img_url";
    public static String TRANSACTION_DATE = "created_at";

    public static final String TOPIC_GLOBAL = "global";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final int REQUEST_TIMEOUT = 5000;

    public static final int STATUS_UNPAID = 0;
    public static final int STATUS_PAID = 1;

    public static String TRANSACTION_ALL = "all";
    public static String TRANSACTION_PAID = "paid";
    public static String TRANSACTION_DUE = "unpaid";
    public static String TRANSACTION_PENDING = "pending";

    public static final int store_search = 1;
    public static final int category_search = 2;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    public static final String prefs = "prefs";
    public static final String STORE_ID = "store_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String COVER_IMAGE_URL = "cover_image_url";
    public static String accessToken = "abcd";
    public static final String TRANSACTION_DTO = "transaction_dto";

    public static Typeface getRegularFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "avenirltstd_regular.otf");
        return face;
    }

    public static Typeface getMediumFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "avenirltstd_medium.otf");
        return face;
    }

    public static final String ITEM_DTO = "item_dto";
    public static final String STORE_DTO = "store_dto";
    public static final String STORE_OFFER_DTO = "store_offer_dto";
    public static final String USER_DTO = "user_dto";
    public static final String CATEGORY_DTO = "category_dto";
    public static final String SEARCH_TYPE = "search_type";

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void showDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog_layout);

        TextView text = dialog.findViewById(R.id.message);
        text.setText(message);

        TextView ok_button = dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDialog(Context context, String message, final ClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog_layout);

        TextView text = dialog.findViewById(R.id.message);
        text.setText(message);

        TextView ok_button = dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ok();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void debugLog(String TAG, String message) {
        if (debugOn) {
            Log.d(TAG, "" + message);
        }
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(Constants.prefs, MODE_PRIVATE).getString(ACCESS_TOKEN, "");
    }

    public static String getRootDirectory() {
        String rootPath = "";
        try {
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "united_club";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rootPath;
        }
    }

    public static void showNotificationDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_dialog_layout);

        TextView text = dialog.findViewById(R.id.total_amount);
        text.setText(message);

        TextView ok_button = dialog.findViewById(R.id.accept);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
