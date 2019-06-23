package com.mobeedom.android.auto.jyhuremote.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;


public class ServiceWorker {
    static final int DEFAULT_TIMEOUT = 1000;

    static ServiceWorker mInstance;

    int timeout = DEFAULT_TIMEOUT;
    ScheduledExecutorService service;
    Runnable runnable;
    Listener unregisteredPackageListener;
    Listener anyPackageListener;
    Listener timerTickListener;
    Map<String, Listener> listeners;
    Handler handler;

    String lastDetected;
    private long lastPing = 0;

    public interface Listener {
        boolean onTimerTick(String lastProcess, boolean wasNotified);
        void onForeground(String process);
    }

    public ServiceWorker(Context context) {
        this(context, Looper.getMainLooper());
    }

    public ServiceWorker(Context context, Looper looper) {
        listeners = new HashMap<>();
        handler = new Handler(looper);
        mInstance = this;
    }

    public static String getLastDetected() {
        return mInstance != null ? mInstance.lastDetected:null;
    }

    public static boolean isRunning() {
        return mInstance != null && mInstance.isStarted();
    }

    public ServiceWorker timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public ServiceWorker when(String packageName, Listener listener) {
        listeners.put(packageName, listener);
        return this;
    }

    @Deprecated
    public ServiceWorker other(Listener listener) {
        return whenOther(listener);
    }

    public ServiceWorker whenOther(Listener listener) {
        unregisteredPackageListener = listener;
        return this;
    }

    public ServiceWorker whenAny(Listener listener) {
        anyPackageListener = listener;
        return this;
    }

    public ServiceWorker withTickListener(Listener listener) {
        timerTickListener = listener;
        return this;
    }

    public boolean isStarted() {
        return runnable != null && service != null && !service.isShutdown();
    }

    public void start(Context context) {
        runnable = createRunnable(context.getApplicationContext());
        service = new ScheduledThreadPoolExecutor(1);
        service.schedule(runnable, timeout, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (service != null) {
            service.shutdownNow();
            service = null;
        }
        runnable = null;
    }

    private Runnable createRunnable(final Context context) {
        return new Runnable() {
            @Override
            public void run() {
                getForegroundAppAndNotify(context);
                service.schedule(createRunnable(context), timeout, TimeUnit.MILLISECONDS);
            }
        };
    }

    private void getForegroundAppAndNotify(Context context) {
        boolean wasNotified = false;

        if(timerTickListener != null) {
            callOnTimerTickListener(timerTickListener, lastDetected, wasNotified);
        } else if(System.currentTimeMillis() - lastPing > 60000){
            lastPing = System.currentTimeMillis();
            Log.v(LOG_TAG, String.format("TestHU04 running"));
        }
    }

    void callListener(final Listener listener, final String packageName) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onForeground(packageName);
            }
        });
    }

    void callOnTimerTickListener(final Listener listener, final String packageName, final boolean wasNotified) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onTimerTick(packageName, wasNotified);
            }
        });
    }
}