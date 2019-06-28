package com.mobeedom.android.auto.jyhuremote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mobeedom.android.auto.jyhuremote.receivers.IntentReceiver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean decodeIntent(Intent intent) {
        Log.v(App.LOG_TAG, String.format("MainActivity.decodeIntent: "));
        if(intent.hasExtra(IntentReceiver.INTENT_VOLUP)) {
            App.getInstance().volUp();
            finish();
            return true;
        }
        if(intent.hasExtra(IntentReceiver.INTENT_VOLDOWN)) {
            App.getInstance().volDown();;
            finish();
            return true;
        }
        if(intent.hasExtra(IntentReceiver.INTENT_VOLMUTE)) {
            App.getInstance().volMute();
            finish();
            return true;
        }
        if(intent.hasExtra(IntentReceiver.INTENT_VOL_LEVEL)) {
            App.getInstance().volLevel(intent.getIntExtra(IntentReceiver.INTENT_VOL_LEVEL, 10));
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (decodeIntent(intent))
            finish();
    }
}
