package com.mobeedom.android.auto.jyhuremote.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.mobeedom.android.auto.jyhuremote.helpers.NotificationHelper;

import androidx.annotation.RequiresApi;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;


public class HUService extends Service implements ServiceWorker.Listener {
    protected ServiceWorker mAppChecker;

    private PowerManager powerManager;
    private String mCurrentLauncher;
    private Looper mServiceLooper;
    private int mLastDisableFlag = 0;
    private long mLastCheck = 0;

    public HUService() {
    }

    public static void startService(Context context) {
        Log.v(LOG_TAG, String.format("MyService.startService: "));
        Intent intent = new Intent(context, HUService.class);
        intent.setAction("START");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        Log.d(LOG_TAG, String.format("MyService.onCreate: "));
        NotificationHelper.startForeground(this);

        HandlerThread thread = new HandlerThread("MyServiceArguments", THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mAppChecker = new ServiceWorker(this, mServiceLooper);

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        final IntentFilter screenFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenEventsReceiver, screenFilter, null, new Handler(mServiceLooper));

    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, String.format("MyService.onStartCommand: "));
        mAppChecker
                .whenOther(this)
                .withTickListener(this)
                .start(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, String.format("MyService.onDestroy: "));
        try {
            unregisterReceiver(screenEventsReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    BroadcastReceiver screenEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                // TODO: check if interactive
                Log.v(LOG_TAG, String.format("MyService.onReceive: SCREEN ON"));
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.v(LOG_TAG, String.format("MyService.onReceive: SCREEN OFF"));
            }
        }
    };

    @Override
    public boolean onTimerTick(String lastProcess, boolean wasNotified) {
//        Log.v(LOG_TAG, String.format("HUService.onTimerTick: "));
        return false;
    }

    @Override
    public void onForeground(String process) {

    }
}
