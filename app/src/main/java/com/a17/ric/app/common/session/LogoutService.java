package com.a17.ric.app.common.session;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by admin 4 on 25/08/2017.
 * Automatically Logout user within 8 HRS inactive from the APP
 * TODO: Test this onevery ACTIViTY
 */

public class LogoutService extends Service {

    public static CountDownTimer timer;
    private static final String TAG = LogoutService.class.getSimpleName();
    private static final int FIVE_MINS = 10*60*48000; // 8 Hrs (28,800,000 millesecond = 480 mins)
     //private static final int FIVE_MINS = 10*60*500; // 5 mins

    private static Handler handler;
    private static Runnable r;

    public LogoutService() { }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(LogoutService.this, "user inactive",
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Runnable() user inactive");

                // Clear PRefs
                // UserSessionManager mSession = UserSessionManager.getInstance(LogoutService.this);
                // mSession.clearPrefs();

                // Logout
                Intent i = new Intent(LogoutService.this, null /*LoginActivity2.class*/);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        startHandler();
    }

    public static void onUserInteraction() {
        stopHandler();//stop first and then start
        startHandler();
    }

    public static void stopHandler() {
        if (handler != null)
            handler.removeCallbacks(r);
    }

    public static void startHandler() {
        if (handler != null)
            handler.postDelayed(r, FIVE_MINS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        /*for (int i = 0; i < 3; i++) {
            long endTime = System.currentTimeMillis() +
                    10 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            Log.i(TAG, "Service running");
        }*/


        /*timer = new CountDownTimer(FIVE_MINS, 1000) {
            public void onTick(long millisUntilFinished) {
                //Some code
                Log.v(TAG, "Service Started");
            }

            public void onFinish() {
                Log.v(TAG, "Call Logout by Service");
                // Code for Logout
                stopSelf();
            }
        };*/
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }
}
