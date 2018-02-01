package mastersunny.unitedclub.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by ASUS on 1/20/2018.
 */

public class Constants {

    public static Typeface getRegularFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "AVENIRLTSTD-REGULAR.OTF");
        return face;
    }

    public static Typeface getMediumFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "AVENIRLTSTD-MEDIUM.otf");
        return face;
    }

    public static final String ITEM_DTO = "item_dto";
    public static final String STORE_DTO = "store_dto";

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void showPaymentDialog(Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Payment")
                .setMessage("Do you want to make this payment?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
