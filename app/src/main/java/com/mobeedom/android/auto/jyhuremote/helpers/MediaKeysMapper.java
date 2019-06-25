package com.mobeedom.android.auto.jyhuremote.helpers;

import android.view.KeyEvent;

import com.mobeedom.android.auto.jyhuremote.App;

public class MediaKeysMapper {
    public static void translateFromKeyboard(int keyCode, boolean isRadio) {
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
                if(isRadio)
                    App.getInstance().radioNext();
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                if(isRadio)
                    App.getInstance().radioPrev();
                break;
        }

    }
}
