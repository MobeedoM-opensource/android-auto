package com.mobeedom.android.auto.jyhuremote.services;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.mobeedom.android.auto.jyhuremote.App;
import com.mobeedom.android.auto.jyhuremote.helpers.MediaKeysMapper;

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
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                if(!event.getPackageName().toString().startsWith("android") && !event.getPackageName().toString().startsWith("com.android.systemui"))
                    App.getInstance().setCurrentForegroundPackage(event.getPackageName().toString());

                ActivityInfo activityInfo = tryGetActivity(componentName);
                boolean isActivity = activityInfo != null;
                if (isActivity)
                    Log.v(LOG_TAG, String.format("ToggleService.onAccessibilityEvent: %s", componentName.flattenToShortString()));
                else
                    Log.v(LOG_TAG, String.format("ToggleService.onAccessibilityEvent: %s", event.getPackageName().toString()));
            }
        }

    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            Log.v(LOG_TAG, String.format("ToggleService.onKeyEvent UP: %d", keyCode));
            MediaKeysMapper.translateFromKeyboard(keyCode, App.getInstance().getCurrentForegroundPackage());
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
