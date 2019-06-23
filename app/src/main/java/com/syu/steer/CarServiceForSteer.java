package com.syu.steer;

import android.content.Context;

import com.syu.remote.Callback;
import com.syu.remote.RemoteTools;
import com.syu.util.JTools;

public class CarServiceForSteer {

        public static final int[] STEER_LOOK_CODE;
        static CarServiceForSteer mService;
        final int[] CANBUS_LOOK_CODE = new int[0];
        final int[] DVD_LOOK_CODE = new int[]{26};
        final int[] IPOD_LOOK_CODE = new int[]{14};
        final int[] MCU_LOOK_CODE;
        final int[] RADIO_LOOK_CODE;
        final int[] SOUND_LOOK_CODE = new int[]{2, 3};
        Context mContext;
        int mCurrentADC;
        int[] mCurrentAdcs = new int[6];
        OnAdcChangeLisenter mLisenter;
        RemoteTools mTools;
        int mVol = -1;

        public interface OnAdcChangeLisenter {
            void onChanged(boolean z);
        }

        /* renamed from: com.syu.steer.CarServiceForSteer$1 */
        class C01481 implements Callback.OnRefreshLisenter {
            C01481() {
            }

            public void onRefresh(int updateCode, int[] ints, float[] flts, String[] strs) {
                if (updateCode == 3 && JTools.check(ints, 1)) {
                    CarServiceForSteer.this.mCurrentAdcs[ints[0]] = ints[1];
                    if (CarServiceForSteer.this.mLisenter != null) {
                        CarServiceForSteer.this.mLisenter.onChanged(CarServiceForSteer.this.adcVaild());
                    }
                }
            }
        }

        static {
            int[] iArr = new int[5];
            iArr[1] = 1;
            iArr[2] = 2;
            iArr[3] = 3;
            iArr[4] = 4;
            STEER_LOOK_CODE = iArr;
        }

        public static CarServiceForSteer getmService(Context context) {
            if (context == null) {
                return null;
            }
            if (mService == null) {
                mService = new CarServiceForSteer(context);
            }
            return mService;
        }

        private CarServiceForSteer(Context context) {
            int[] iArr = new int[5];
            iArr[1] = 31;
            iArr[2] = 9;
            iArr[3] = 10;
            iArr[4] = 11;
            this.MCU_LOOK_CODE = iArr;
            iArr = new int[25];
            iArr[0] = 2;
            iArr[1] = 19;
            iArr[3] = 3;
            iArr[4] = 4;
            iArr[5] = 100;
            iArr[6] = 16;
            iArr[7] = 1;
            iArr[8] = 23;
            iArr[9] = 5;
            iArr[10] = 6;
            iArr[11] = 14;
            iArr[12] = 15;
            iArr[13] = 10;
            iArr[14] = 12;
            iArr[15] = 7;
            iArr[16] = 9;
            iArr[17] = 11;
            iArr[18] = 13;
            iArr[19] = 8;
            iArr[20] = 20;
            iArr[21] = 22;
            iArr[22] = 17;
            iArr[23] = 18;
            iArr[24] = 21;
            this.RADIO_LOOK_CODE = iArr;
            this.mContext = context.getApplicationContext();
            this.mTools = new RemoteTools(this.mContext);
            enableModule(this.mTools);
            addDefaultLisenter(this.mTools);
            this.mTools.bind();
        }

        public void enableModule(int module) {
            if (this.mTools != null) {
                switch (module) {
                    case 0:
                        this.mTools.enableModule(0, this.MCU_LOOK_CODE);
                        return;
                    case 1:
                        this.mTools.enableModule(1, this.RADIO_LOOK_CODE);
                        return;
                    case 4:
                        this.mTools.enableModule(4, this.SOUND_LOOK_CODE);
                        return;
                    case 5:
                        this.mTools.enableModule(5, this.IPOD_LOOK_CODE);
                        return;
                    case 7:
                        this.mTools.enableModule(7, this.CANBUS_LOOK_CODE);
                        return;
                    case 10:
                        this.mTools.enableModule(10, STEER_LOOK_CODE);
                        return;
                    default:
                        return;
                }
            }
        }

        private void enableModule(RemoteTools tools) {
            tools.enableModule(10, STEER_LOOK_CODE);
            enableModule(1);
            enableModule(4);
            enableModule(0);
        }

        void addDefaultLisenter(RemoteTools tools) {
            tools.addRefreshLisenter(10, new C01481(), 3);
        }

        public boolean adcVaild() {
            boolean b = false;
            for (int val : this.mCurrentAdcs) {
                if (val <= 0 || val > 240) {
                    b = false;
                } else {
                    b = true;
                }
                if (b) {
                    return b;
                }
            }
            return b;
        }

        public int getCurrentADC() {
            return this.mCurrentADC;
        }

        public RemoteTools getTools() {
            return this.mTools;
        }

        public void setOnAdcChangeLisenter(OnAdcChangeLisenter mLisenter) {
            this.mLisenter = mLisenter;
        }
}
