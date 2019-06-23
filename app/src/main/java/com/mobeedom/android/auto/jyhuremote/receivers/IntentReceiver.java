package com.mobeedom.android.auto.jyhuremote.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class IntentReceiver extends BroadcastReceiver {
    public static final String INTENT_VOLUP = "com.mobeedom.android.hu.VOL_UP";
    public static final String INTENT_VOLDOWN = "com.mobeedom.android.hu.VOL_DOWN";
    public static final String INTENT_VOLMUTE = "com.mobeedom.android.hu.VOL_MUTE_SWITCH";
    public static final String INTENT_VOL_LEVEL = "com.mobeedom.android.hu.VOL_LEVEL";
    public static final String INTENT_PARAM = "PARAM";
    public static final String INTENT_CUSTOM_MODULE = "com.mobeedom.android.hu.CUSTOM_MODULE";
    public static final String INTENT_CUSTOM_MODULE2 = "com.mobeedom.android.hu.CUSTOM_MODULE2";
    public static final String INTENT_MODULE = "MODULE";
    public static final String INTENT_CODE = "CODE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Intent receiver: received intent");

        if (intent.getData() == null)
            return;

        String data = intent.getData().toString().replace("package:", "");
        String action = intent.getAction();

        Log.d(LOG_TAG, "Action: " + action);
        Log.d(LOG_TAG, "Data: " + data);
    }
}