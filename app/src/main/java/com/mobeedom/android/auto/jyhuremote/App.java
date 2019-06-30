package com.mobeedom.android.auto.jyhuremote;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.mobeedom.android.auto.jyhuremote.services.HUService;
import com.mobeedom.android.auto.jyhuremote.services.ToggleService;
import com.mobeedom.android.auto.jyhuremote.util.Util;
import com.syu.car.CarService;
import com.syu.ipc.FinalMainServer;
import com.syu.ipc.data.FinalMain;
import com.syu.ipc.data.FinalRadio;
import com.syu.ipc.data.FinalSound;
import com.syu.steer.CarServiceForSteer;

public class App extends Application {
    public static final String LOG_TAG = "MY_HUREMOTE";
    private static App mInstance;

    private CarService mService;
    private CarServiceForSteer mServiceSteer;
    private ToggleService mToggleService = ToggleService.getInstance();

    private String mCurrentForegroundPackage = "";

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mService = CarService.getmService(getBaseContext());
        mServiceSteer = CarServiceForSteer.getmService(getBaseContext());
        mToggleService = ToggleService.getInstance();
//        startCustomLooper();
    }

    public String getCurrentForegroundPackage() {
        // to avoid NPE
        return ""+mCurrentForegroundPackage;
    }

    public void setCurrentForegroundPackage(String currentForegroundPackage) {
        mCurrentForegroundPackage = currentForegroundPackage;
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

    public void radioSeekDown() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_RADIO, FinalRadio.C_SEEK_DOWN);
    }
    public void radioSeekUp() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_RADIO, FinalRadio.C_SEEK_UP);
    }

    public void mediaPlayPause() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_KEY, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    public void mediaNext() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_KEY, KeyEvent.KEYCODE_MEDIA_NEXT);
    }
    public void mediaPrev() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_KEY, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
    }

    public void mediaSF() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_KEY, KeyEvent.KEYCODE_MEDIA_SKIP_FORWARD);
    }

    public void mediaSB() {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_KEY, KeyEvent.KEYCODE_MEDIA_SKIP_BACKWARD);
    }

    public void playerCommand(int cmd) {
        App.getServiceSteer().getTools().sendInt(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_VA_CMD, cmd);
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
}
