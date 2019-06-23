package com.mobeedom.android.test.testhu04;

import android.os.Bundle;

public class TaskerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decodeIntent(getIntent());
    }
}
