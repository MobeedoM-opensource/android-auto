package com.mobeedom.android.auto.jyhuremote;

import android.os.Bundle;
import android.widget.Toast;

import com.mobeedom.android.auto.jyhuremote.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class USBReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, String.format("Starting accessibility service: action %s", getIntent().getAction()), Toast.LENGTH_LONG).show();
        Util.startAccessibilityService();
        finish();
    }
}
