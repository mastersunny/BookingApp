package mastersunny.rooms.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mastersunny.rooms.listeners.ConfirmListener;
import mastersunny.rooms.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 1/20/2018.
 */

public class Constants {

    public static boolean debugOn = true;

    static String TAG = "Constants";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");


    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String FIREBASE_REFRESH_TOKEN = "firebase_refresh_token";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final int REQUEST_TIMEOUT = 10000;

    public static final String prefs = "prefs";
    static final String ACCESS_TOKEN = "access_token";
    public static final String COVER_IMAGE_URL = "cover_image_url";
    public static String accessToken = "abcd";
    public static final String ROOM_BOOKING_DTO = "room_booking_dto";
    public static final String ROOM_DTO = "room_dto";
    public static final String BOOKING_PENDING = "booking_pending";

    public static final String PREF_COOKIES = "pref_cookies";


    public static Typeface getRegularFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "avenirltstd_regular.otf");
        return face;
    }

    public static Typeface getMediumFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "avenirltstd_medium.otf");
        return face;
    }

    public static final String STORE_DTO = "store_dto";
    public static final String STORE_OFFER_DTO = "store_offer_dto";
    public static final String USER_DTO = "user_dto";
    public static final String USER_NAME = "user_name";
    public static final String CATEGORY_DTO = "category_dto";

    public static Date startDate = new Date();
    public static Date endDate = new Date();
    public static int roomQty = 1;
    public static int adultQty = 1;
    public static int childQty;
    public static double latitude = 0;
    public static double longitude = 0;
    public static String placeName = "";


    public static void loadImage(Context context, String imageUrl, final ImageView imageView) {
        ViewTarget viewTarget = new ViewTarget<ImageView, GlideDrawable>(imageView) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
//                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource.getCurrent());
            }
        };
        Glide.with(imageView.getContext()).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewTarget);
    }

    public static void loadNormalWay(String url, final ImageView iv) {
        ViewTarget viewTarget = new ViewTarget<ImageView, GlideDrawable>(iv) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                iv.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                iv.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                iv.setImageDrawable(resource.getCurrent());
            }
        };
        Glide.with(iv.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewTarget);
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

    public static void showDialog(Context context, String message, final ConfirmListener listener) {
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

    public static void showNotificationDialog(Context context, String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static Calendar addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal;
    }

    public static String calculateDate(int year, int month, int day) {
        try {
            String dateInString = String.format("%02d", day) + "-" + String.format("%02d", month) + "-" + year;
            Date date = Constants.sdf.parse(dateInString);
            String[] strings = date.toString().split(" ");
            return (strings[0] + ", " + strings[1] + " " + strings[2]);
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
        return "";
    }

    public static final int PERMISSION_LOCATION = 111;
    public static final int PERMISSION_READ_SMS = 222;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 333;
    public static final int ROOM_DATE_REQUEST_CODE = 444;

    public static int REQUEST_FACEBOOK_LOGIN_CODE = 199;
    public static int totalGuest = 0;
}
