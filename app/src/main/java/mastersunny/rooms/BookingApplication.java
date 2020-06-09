package mastersunny.rooms;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class BookingApplication extends Application implements Application.ActivityLifecycleCallbacks{

    private static WeakReference<Context> mContext;
    private static Activity currentActivity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static BookingApplication getInstance(Context context) {
        return ((BookingApplication) context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<>(getApplicationContext());
        registerActivityLifecycleCallbacks(this);
    }

    public static Context getContext() {
        return mContext.get();
    }

    public static boolean isConnectedToInternet() {
        ConnectivityManager conMgr = null;
        boolean connected = false;

        try {
            conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connected = (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected());
            return connected;
        } catch (Exception e) {
            // Do nothing, since failed cases will be handled outside of this scope.
        }
        return connected;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }


}
