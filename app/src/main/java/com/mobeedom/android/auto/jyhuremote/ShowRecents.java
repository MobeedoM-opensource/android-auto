package com.mobeedom.android.auto.jyhuremote;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.mobeedom.android.auto.jyhuremote.services.ToggleService;

import androidx.appcompat.app.AppCompatActivity;

public class ShowRecents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToggleService service = ((App)getApplication()).getToggleService();
        if(service != null) {
            service.doAction();
        } else {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent, 0);
        }
        finish();
    }


}
