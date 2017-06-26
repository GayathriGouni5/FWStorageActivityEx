package org.example.gayathri.fwstorageactivityex;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "org.example.gayathri.fwstorageactivityex.action.FOO";
    public static final String ACTION_BAZ = "org.example.gayathri.fwstorageactivityex.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "org.example.gayathri.fwstorageactivityex.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "org.example.gayathri.fwstorageactivityex.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent ni = new Intent(this,Main2Activity.class);
        PendingIntent p = PendingIntent.getActivity(this,0,ni,0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("Title")
                .setContentText("R.string.notification_message")
                .setSmallIcon(R.drawable.ball_100x100)
                .setContentIntent(p)
                .setTicker(getText(R.string.app_name))
                .build();
        startForeground(333,n);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            try{
                Log.d(TAG, "onHandleIntent: sleeping");
                Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
