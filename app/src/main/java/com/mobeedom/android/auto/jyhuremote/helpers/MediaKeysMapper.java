package com.mobeedom.android.auto.jyhuremote.helpers;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mobeedom.android.auto.jyhuremote.App;
import com.mobeedom.android.auto.jyhuremote.services.ToggleService;
import com.syu.ipc.FinalMainServer;
import com.syu.ipc.data.FinalBt;
import com.syu.ipc.data.FinalMain;

import java.io.IOException;

import static com.mobeedom.android.auto.jyhuremote.App.LOG_TAG;

public class MediaKeysMapper {
    public static final String SYU_RADIO_PACKAGE="com.syu.radio";
    public static final String SYU_MUSIC_PACKAGE="com.syu.music";
    public static final String SYU_BT_PACKAGE="com.syu.bt";
    public static final String SYU_GENERIC_PACKAGE="com.syu";

    public static boolean lastPlayState = true;

    public static void translateFromKeyboard(int keyCode, String fgPackage) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                App.getInstance().volMute();
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                App.getInstance().volUp();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                App.getInstance().volDown();
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
//                Toast.makeText(App.getInstance(), "NEXT", Toast.LENGTH_SHORT).show();
                if(SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioNext();
                else if(fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_SKIPF);
                else
                    App.getInstance().mediaNext();
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
//                Toast.makeText(App.getInstance(), "PREVIOUS", Toast.LENGTH_SHORT).show();
                if(SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioPrev();
                else if(fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_SKIPB);
                else
                    App.getInstance().mediaPrev();
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
//                Toast.makeText(App.getInstance(), "PLAY/PAUSE", Toast.LENGTH_SHORT).show();
                if(SYU_MUSIC_PACKAGE.equals(fgPackage)) {
                    App.getInstance().playerCommand(lastPlayState ? FinalMain.VA_CMD_KEY_PAUSE:FinalMain.VA_CMD_KEY_PLAY);
                    lastPlayState = !lastPlayState;
                } else
                    App.getInstance().mediaPlayPause();
                break;
            case KeyEvent.KEYCODE_F1: // GPS
                Toast.makeText(App.getInstance(), "GPS", Toast.LENGTH_SHORT).show();
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_JUMP_PAGE, FinalMain.PAGE_NAVI);
                break;
            case KeyEvent.KEYCODE_F2:
                Toast.makeText(App.getInstance(), "CALL", Toast.LENGTH_SHORT).show();
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_BT, FinalBt.C_DIAL, 0);
                break;
            case KeyEvent.KEYCODE_F3:
                Toast.makeText(App.getInstance(), "ENDCALL", Toast.LENGTH_SHORT).show();
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_BT, FinalBt.C_HANG, 0);
                break;
            case KeyEvent.KEYCODE_F4: // M: Toggle recents
                Toast.makeText(App.getInstance(), "M", Toast.LENGTH_SHORT).show();
                ToggleService.getInstance().doAction();
                break;
        }

    }

    private static void execKeyCode(int keyCode) {
        try {
            Runtime.getRuntime().exec("input keyevent " + keyCode);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in execKeyCode", e);
        }
    }
}
