package com.mobeedom.android.auto.jyhuremote;

import android.os.Bundle;

import com.mobeedom.android.auto.jyhuremote.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class USBReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.startAccessibilityService();
        finish();
    }
}
