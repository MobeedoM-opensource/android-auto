package com.mobeedom.android.auto.jyhuremote;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(App.LOG_TAG, String.format("MainActivity.onCreate: "));
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        findViewById(R.id.btnDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance().volDown();
            }
        });

        findViewById(R.id.btnUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance().volUp();
            }
        });

        findViewById(R.id.btnMute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance().volMute();
            }
        });
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });

        findViewById(R.id.btnExec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int module = Integer.valueOf(((EditText)findViewById(R.id.module)).getText().toString());
                    int command = Integer.valueOf(((EditText)findViewById(R.id.command)).getText().toString());
                    String pp = ((EditText)findViewById(R.id.param)).getText().toString();
                    try {
                        App.getServiceSteer().getTools().sendInt(module, command, Integer.parseInt(pp));
                    } catch (NumberFormatException e) {
                        App.getServiceSteer().getTools().sendStr(module, command, pp);
                    }
                } catch (NumberFormatException e) {
                    Log.e(App.LOG_TAG, "Error in onClick", e);
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
