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
    public static final String SYU_RADIO_PACKAGE = "com.syu.radio";
    public static final String SYU_MUSIC_PACKAGE = "com.syu.music";
    public static final String SYU_BT_PACKAGE = "com.syu.bt";
    public static final String SYU_GENERIC_PACKAGE = "com.syu";
    public static final String NETFLIX_PACKAGE = "com.netflix.mediaclient";

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
                if (SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioNext();
                else if (fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_SKIPF);
                else
                    App.getInstance().mediaNext();
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                if (SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioPrev();
                else if (fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_SKIPB);
                else
                    App.getInstance().mediaPrev();
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                if (SYU_MUSIC_PACKAGE.equals(fgPackage)) {
                    App.getInstance().playerCommand(lastPlayState ? FinalMain.VA_CMD_KEY_PAUSE : FinalMain.VA_CMD_KEY_PLAY);
                    lastPlayState = !lastPlayState;
                } else if (fgPackage.startsWith(NETFLIX_PACKAGE))
                    execKeyCode(KeyEvent.KEYCODE_ENTER); // NOT Working :-(
                else
                    App.getInstance().mediaPlayPause();
                break;
            case KeyEvent.KEYCODE_F1: // GPS
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_JUMP_PAGE, FinalMain.PAGE_NAVI);
                break;
            case KeyEvent.KEYCODE_F2: // CALL
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_BT, FinalBt.C_PICKUP, 0);
                break;
            case KeyEvent.KEYCODE_F3: // END CALL
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_BT, FinalBt.C_HANG, 0);
                break;
            case KeyEvent.KEYCODE_F4: // M: Toggle recents
                execKeyCode(KeyEvent.KEYCODE_VOICE_ASSIST);
                break;
            // ****************
            // START LONG PRESS KEYCODES
            // ****************
            case KeyEvent.KEYCODE_F5: // GPS Long Press
                Toast.makeText(App.getInstance(), String.valueOf(keyCode), Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_F6: // CALL Long Press
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_MAIN, FinalMain.C_VA_CMD, FinalMain.VA_CMD_PHONE_CONTACT);
                execKeyCode(KeyEvent.KEYCODE_CALL);
                break;
            case KeyEvent.KEYCODE_F7: // ENDCALL Long Press
                App.getInstance().customModuleManager(FinalMainServer.MODULE_CODE_BT, FinalBt.C_HANG, 0);
                break;
            case KeyEvent.KEYCODE_F8: // M Long Press
                ToggleService.getInstance().doAction();
                break;
            case KeyEvent.KEYCODE_F9: // MEDIA_PLAY/PAUSE Long Press
                Toast.makeText(App.getInstance(), String.valueOf(keyCode), Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_F10: // VOL_UP Long Press
                Toast.makeText(App.getInstance(), String.valueOf(keyCode), Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_F11: // VOL_DOWN Long Press
                Toast.makeText(App.getInstance(), String.valueOf(keyCode), Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_F12: // VOL_MUTE Long Press
                Toast.makeText(App.getInstance(), String.valueOf(keyCode), Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_SHIFT_RIGHT: // MEDIA_NEXT Long Press
                if (SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioSeekUp();
                else if (fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_FF);
                else
                    App.getInstance().mediaSF();
                break;
            case KeyEvent.KEYCODE_SHIFT_LEFT: // MEDIA_PREV Long Press
                if (SYU_RADIO_PACKAGE.equals(fgPackage))
                    App.getInstance().radioSeekDown();
                else if (fgPackage.startsWith(SYU_GENERIC_PACKAGE))
                    App.getInstance().playerCommand(FinalMain.VA_CMD_KEY_FB);
                else
                    App.getInstance().mediaSB();
                break;
        }

    }

    private static void execKeyCode(int keyCode) {
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c","input keyevent " + keyCode});
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in execKeyCode", e);
        }
    }
}
