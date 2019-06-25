package com.syu.car;

import android.content.Context;
import android.util.Log;
import com.syu.remote.Callback.OnRefreshLisenter;
import com.syu.remote.RemoteTools;
import com.syu.util.JTools;

public class CarService {
    static CarService mService;
    final int[] BT_LOOK_CODE = new int[]{9};
    final int[] CANBUS_LOOK_CODE = new int[]{1012, 1000, 1005};
    final int[] DVD_LOOK_CODE = new int[]{26, 25};
    final int[] IPOD_LOOK_CODE = new int[]{14, 15};
    final int[] MCU_LOOK_CODE = new int[]{40, 0, 31, 9, 10, 11, 45};
    final int[] SOUND_LOOK_CODE = new int[]{2, 3};
    int mAppId = 0;
    Context mContext;
    RemoteTools mSubTools;
    RemoteTools mTools;
    int mVol = -1;

    /* renamed from: com.syu.car.CarService$1 */
    class C14311 implements OnRefreshLisenter {
        C14311() {
        }

        public void onRefresh(int updateCode, int[] ints, float[] flts, String[] strs) {
            if (updateCode == 2 && JTools.check(ints, 1)) {
                CarService.this.mVol = ints[0];
            }
        }
    }

    /* renamed from: com.syu.car.CarService$2 */
    class C14322 implements OnRefreshLisenter {
        C14322() {
        }

        public void onRefresh(int updateCode, int[] ints, float[] flts, String[] strs) {
            if (updateCode == 40 && JTools.check(ints, 1)) {
                Log.e("temp", "========================= onRefresh temp == " + ints[0]);
            }
        }
    }

    /* renamed from: com.syu.car.CarService$3 */
    class C14333 implements OnRefreshLisenter {
        C14333() {
        }

        public void onRefresh(int updateCode, int[] ints, float[] flts, String[] strs) {
            if (updateCode == 0 && JTools.check(ints, 1)) {
                Log.e("temp", "========================= onRefresh appid == " + ints[0]);
                CarService.this.mAppId = ints[0];
            }
        }
    }

    public static CarService getmService(Context context) {
        if (context == null) {
            return null;
        }
        if (mService == null) {
            mService = new CarService(context);
        }
        return mService;
    }

    private CarService(Context context) {
        this.mContext = context.getApplicationContext();
        this.mTools = new RemoteTools(this.mContext);
        enableModule(this.mTools);
        addDefaultLisenter(this.mTools);
        this.mTools.bind();
        this.mSubTools = new RemoteTools(context, "com.syu.ss.toolkit", "com.syu.ss");
        enableModule();
        this.mSubTools.bind();
    }

    private void enableModule(RemoteTools tools) {
        tools.enableModule(0, this.MCU_LOOK_CODE);
        tools.enableModule(1, this.MCU_LOOK_CODE);
        tools.enableModule(4, this.SOUND_LOOK_CODE);
        tools.enableModule(5, this.IPOD_LOOK_CODE);
        tools.enableModule(2, this.BT_LOOK_CODE);
        tools.enableModule(7, this.CANBUS_LOOK_CODE);
    }

    private void enableModule() {
        this.mSubTools.enableModule(0, 0, 4);
    }

    void addDefaultLisenter(RemoteTools tools) {
        tools.addRefreshLisenter(4, new C14311(), this.SOUND_LOOK_CODE);
        tools.addRefreshLisenter(0, new C14322(), 40);
        tools.addRefreshLisenter(0, new C14333(), 0);
    }

    public RemoteTools getTools() {
        return this.mTools;
    }

    public RemoteTools getSubTools() {
        return this.mSubTools;
    }
}