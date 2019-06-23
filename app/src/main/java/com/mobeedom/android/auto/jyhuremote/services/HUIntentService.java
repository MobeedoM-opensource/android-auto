package com.mobeedom.android.auto.jyhuremote.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.mobeedom.android.auto.jyhuremote.App;
import com.mobeedom.android.auto.jyhuremote.helpers.NotificationHelper;
import com.mobeedom.android.auto.jyhuremote.receivers.IntentReceiver;
import com.syu.ipc.FinalMainServer;
import com.syu.ipc.data.FinalSound;

import androidx.annotation.RequiresApi;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HUIntentService extends IntentService {
    public HUIntentService() {
        super("HUIntentService");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        NotificationHelper.startForeground(this);

        Log.v(LOG_TAG, String.format("HUIntentService.onCreate: "));
    }

    /**
     * example am start-foreground-service -a com.mobeedom.android.hu.VOL_UP
     * example am start-foreground-service -a com.mobeedom.android.hu.CUSTOM_MODULE -ei "MODULE" 4 -ei "CODE" 0 -ei "PARAM" 17
     * example am start-foreground-service -n com.mobeedom.android.test.testhu04/.services.HUIntentService -a com.mobeedom.android.hu.CUSTOM_MODULE --ei "MODULE" 4 --ei "CODE" 0 --ei "PARAM" 17
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, String.format("HUIntentService.onHandleIntent: %s", intent.getAction()));

        if (intent != null) {
            final String action = intent.getAction();
            if (IntentReceiver.INTENT_VOLUP.equals(action)) {
                App.getInstance().volUp();
            } else if (IntentReceiver.INTENT_VOLDOWN.equals(action)) {
                App.getInstance().volDown();;
            } else if (IntentReceiver.INTENT_VOLMUTE.equals(action)) {
                App.getInstance().volMute();;
            } else if (IntentReceiver.INTENT_VOL_LEVEL.equals(action)) {
                App.getInstance().volLevel(intent.getIntExtra(IntentReceiver.INTENT_PARAM, 10));
            } else if (IntentReceiver.INTENT_CUSTOM_MODULE.equals(action)) {
                try {
                    App.getInstance().customModuleManager(
                            intent.getIntExtra(IntentReceiver.INTENT_MODULE, FinalMainServer.MODULE_CODE_SOUND),
                            intent.getIntExtra(IntentReceiver.INTENT_CODE, 0),
                            intent.getIntExtra(IntentReceiver.INTENT_PARAM, FinalSound.VOL_MUTE_SWITCH));
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "Error in onHandleIntent", e);
                }
            } else if (IntentReceiver.INTENT_CUSTOM_MODULE2.equals(action)) {
                try {
                    App.getInstance().customModule2Manager(
                            intent.getIntExtra(IntentReceiver.INTENT_MODULE, FinalSound.VOL_MUTE_SWITCH),
                            intent.getIntExtra(IntentReceiver.INTENT_CODE, 0),
                            intent.getIntExtra(IntentReceiver.INTENT_PARAM, 0));
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "Error in onHandleIntent", e);
                }
            }
        }
    }
}
