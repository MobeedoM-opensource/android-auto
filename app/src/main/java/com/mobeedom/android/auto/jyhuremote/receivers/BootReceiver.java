package com.mobeedom.android.auto.jyhuremote.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Skips all because the startup logic is fully handled inside Application
    }
}
