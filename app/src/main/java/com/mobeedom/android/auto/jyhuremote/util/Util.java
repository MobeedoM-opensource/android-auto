package com.mobeedom.android.auto.jyhuremote.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class Util {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        Log.v(LOG_TAG, String.format("Util.scheduleJob: "));
    }

    public static void startAccessibilityService() {
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c","settings put secure enabled_accessibility_services"
                    , "com.mobeedom.android.auto.jyhuremote/com.mobeedom.android.auto.jyhuremote.services.ToggleService"});
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in enableAccessibilityService", e);
        }
    }

}