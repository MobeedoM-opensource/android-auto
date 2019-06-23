package com.mobeedom.android.test.testhu04;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.mobeedom.android.test.testhu04.services.HUService;
import com.syu.car.CarService;
import com.syu.ipc.FinalMainServer;
import com.syu.ipc.data.FinalSound;
import com.syu.steer.CarServiceForSteer;

public class App extends Application {
    public static final String LOG_TAG = "MY_HUREMOTE";
    private static App mInstance;

    private CarService mService;
    private CarServiceForSteer mServiceSteer;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mService = CarService.getmService(getBaseContext());
        mServiceSteer = CarServiceForSteer.getmService(getBaseContext());

        startCustomLooper();
    }

    public static CarService getService() {
        return mInstance.mService;
    }

    public static CarServiceForSteer getServiceSteer() {
        return mInstance.mServiceSteer;
    }

    public void volUp() {
        volManager(FinalSound.VOL_UP);
    }

    public void volDown() {
        volManager(FinalSound.VOL_DOWN);
    }

    public void volMute() {
        volManager(FinalSound.VOL_MUTE_SWITCH);
    }

    public void volLevel(int level) {
        if(level < 0)
            level = 0;
        if(level > 36)
            level = 36;

        volManager(level);
    }

    private void volManager(final int command) {
        App.getService().getTools().sendInt(FinalMainServer.MODULE_CODE_SOUND, 0, command);
    }

    public void customModuleManager(final int module, final int command, final int... params) {
        App.getService().getTools().sendInt(module, command, params);
    }

    public void customModule2Manager(final int module, final int command, final int... params) {
        App.getServiceSteer().getTools().sendInt(module, command, params);
    }

    private void startCustomLooper() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = () -> {
            HUService.startService(this);
        };
        mainHandler.post(myRunnable);
    }

    public static App getInstance() {
        return mInstance;
    }

}
