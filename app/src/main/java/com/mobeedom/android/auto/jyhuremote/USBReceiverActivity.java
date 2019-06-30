package com.mobeedom.android.auto.jyhuremote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mobeedom.android.auto.jyhuremote.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class USBReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Util.startAccessibilityService();
        Toast.makeText(this, String.format("Starting accessibility service: action %s", intent.getAction()), Toast.LENGTH_SHORT).show();
        finish();
    }
}
