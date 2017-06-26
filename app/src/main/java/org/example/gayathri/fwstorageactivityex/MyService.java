package org.example.gayathri.fwstorageactivityex;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public class MyService extends Service {
    private Looper l1;
    private ServiceHandler s1;
    public final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try{
                Thread.sleep(5000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message m1 = s1.obtainMessage();
        m1.arg1 = startId;
        s1.sendMessage(m1);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",Thread.MIN_PRIORITY);
        thread.start();
        l1 = thread.getLooper();
        s1 = new ServiceHandler(l1);


    }
}
