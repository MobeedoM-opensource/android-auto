package com.mobeedom.android.auto.jyhuremote.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class Util {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        Log.v(LOG_TAG, String.format("Util.scheduleJob: "));
    }

    public static void startAccessibilityService() {
        new Handler().postDelayed(() -> startAccessibilityServiceNoDelay(), 500);
    }

    public static void startAccessibilityServiceNoDelay() {
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c","settings put secure enabled_accessibility_services"
                    , "com.mobeedom.android.auto.jyhuremote/com.mobeedom.android.auto.jyhuremote.services.ToggleService"});
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in enableAccessibilityService", e);
        }
    }

    public static void triggerRootRequest() {
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c","echo", "zz"});
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in onCreate", e);
        }
    }

    public static boolean in(String key, String... values) {
        for (String value : values) {
            if(key.equals(value))
                return true;
        }
        return false;
    }
}