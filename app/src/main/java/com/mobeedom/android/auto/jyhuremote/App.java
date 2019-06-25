package com.mobeedom.android.auto.jyhuremote;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mobeedom.android.auto.jyhuremote.services.HUService;
import com.mobeedom.android.auto.jyhuremote.services.ToggleService;
import com.mobeedom.android.auto.jyhuremote.util.Util;
import com.syu.car.CarService;
import com.syu.ipc.FinalMainServer;
import com.syu.ipc.IRemoteToolkit;
import com.syu.ipc.data.FinalRadio;
import com.syu.ipc.data.FinalSound;
import com.syu.ipcself.IConnStateListener;
import com.syu.steer.CarServiceForSteer;
import com.syu.util.InterfaceApp;

public class App extends Application implements InterfaceApp, IConnStateListener {
    public static final String LOG_TAG = "MY_HUREMOTE";
    private static App mInstance;

    private CarService mService;
    private CarServiceForSteer mServiceSteer;
    private ToggleService mToggleService = ToggleService.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mService = CarService.getmService(getBaseContext());
        mServiceSteer = CarServiceForSteer.getmService(getBaseContext());
        mToggleService = ToggleService.getInstance();

//        startCustomLooper();
    }


    public ToggleService getToggleService() {
        if(mToggleService == null)
            mToggleService = ToggleService.getInstance();

        return mToggleService;
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

    public void radioNext() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_RADIO, FinalRadio.C_NEXT_CHANNEL);
    }

    public void radioPrev() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_RADIO, FinalRadio.C_PREV_CHANNEL);
    }

    public void customModuleManager(final int module, final int command, final int... params) {
        if(module == 1 || module == 10)
            App.getServiceSteer().getTools().sendInt(module, command, params);
        else
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

        Util.scheduleJob(this);
    }

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void cmdObdFlagStop(int i) {
        Log.d(LOG_TAG, String.format("App.cmdObdFlagStop: "));
    }

    @Override
    public int getCameraId() {
        Log.v(LOG_TAG, String.format("App.getCameraId: "));
        return 0;
    }

    @Override
    public boolean isAppTop() {
        Log.v(LOG_TAG, String.format("App.isAppTop: "));
        return false;
    }

    @Override
    public void notify_startCamera() {
        Log.v(LOG_TAG, String.format("App.notify_startCamera: "));
    }

    @Override
    public void notify_stopCamera() {
        Log.v(LOG_TAG, String.format("App.notify_stopCamera: "));
    }

    @Override
    public void onConnected_Main() {
        Log.v(LOG_TAG, String.format("App.onConnected_Main: "));
    }

    @Override
    public void onConnected_Sound() {
        Log.v(LOG_TAG, String.format("App.onConnected_Sound: "));
    }

    @Override
    public void requestAppIdRight() {
        Log.v(LOG_TAG, String.format("App.requestAppIdRight: "));
    }

    @Override
    public void setCameraCallBack() {
        Log.v(LOG_TAG, String.format("App.setCameraCallBack: "));
    }

    @Override
    public void setPreviewFormat() {
        Log.v(LOG_TAG, String.format("App.setPreviewFormat: "));
    }

    @Override
    public String updateOsdInfo_Dvd(int[] iArr) {
        Log.v(LOG_TAG, String.format("App.updateOsdInfo_Dvd: "));
        return null;
    }

    @Override
    public void onConnected(IRemoteToolkit iRemoteToolkit) {
        Log.v(LOG_TAG, String.format("App.onConnected: "));
    }

    @Override
    public void onDisconnected() {
        Log.v(LOG_TAG, String.format("App.onDisconnected: "));
    }
}
