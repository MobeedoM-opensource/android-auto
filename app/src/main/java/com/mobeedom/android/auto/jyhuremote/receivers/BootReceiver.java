package com.mobeedom.android.auto.jyhuremote.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.mobeedom.android.auto.jyhuremote.util.Util;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG, String.format("BootReceiver.onReceive: "));
        Util.startAccessibilityService();
        Toast.makeText(context, "Starting accessibility service: from boot receiver", Toast.LENGTH_SHORT).show();
    }
}
