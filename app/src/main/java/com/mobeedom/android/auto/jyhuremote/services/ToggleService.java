package com.mobeedom.android.auto.jyhuremote.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class ToggleService extends AccessibilityService {
    private static ToggleService mInstance;
    public ToggleService() {
        mInstance = this;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static ToggleService getInstance() {
        return mInstance;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            Log.v(LOG_TAG, String.format("ToggleService.onKeyEvent: "));
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                Log.d("Hello", "KeyUp");
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Log.d("Hello", "KeyDown");
            }
            return true;
        } else {
            return super.onKeyEvent(event);
        }
    }

    public void doAction() {
        performGlobalAction(GLOBAL_ACTION_RECENTS);

//        performGlobalAction(GLOBAL_ACTION_RECENTS);
//        performGlobalAction(GLOBAL_ACTION_BACK)
//        performGlobalAction(GLOBAL_ACTION_HOME)
//        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS)
//        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
//        performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS)
//        performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN)
    }
}
