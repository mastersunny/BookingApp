package mastersunny.unitedclub.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mastersunny.unitedclub.activities.TransactionDetailsActivity;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.NotificationUtils;

/**
 * Created by ASUS on 2/26/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {
            Constants.debugLog(TAG, "Notification Body: " + remoteMessage.getData());
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                handleDataMessage(jsonObject);
            } catch (Exception e) {
                Constants.debugLog(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject jsonObject) {
        try {
            int transactionId = jsonObject.getInt(Constants.TRANSACTION_ID);
            String title = jsonObject.getString(Constants.TITLE);
            String message = jsonObject.getString(Constants.MESSAGE);
            String imgUrl = jsonObject.getString(Constants.IMG_URL);
            String transactionDate = jsonObject.getString(Constants.TRANSACTION_DATE);

            Constants.debugLog(TAG, "title " + title + " tansactionId " + transactionId
                    + " message " + message);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra(Constants.TRANSACTION_ID, transactionId);
                pushNotification.putExtra(Constants.TITLE, title);
                pushNotification.putExtra(Constants.MESSAGE, message);
                pushNotification.putExtra(Constants.IMG_URL, imgUrl);
                pushNotification.putExtra(Constants.TRANSACTION_DATE, transactionDate);

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();


            } else {
                Intent resultIntent = new Intent(getApplicationContext(), TransactionDetailsActivity.class);
                resultIntent.putExtra(Constants.TRANSACTION_ID, transactionId);
                resultIntent.putExtra(Constants.TITLE, title);
                resultIntent.putExtra(Constants.MESSAGE, message);
                resultIntent.putExtra(Constants.IMG_URL, imgUrl);
                resultIntent.putExtra(Constants.TRANSACTION_DATE, transactionDate);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent
                        .getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.united_icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setWhen(getTimeMilliSec(transactionDate))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(0, mBuilder.build());

                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }


    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
