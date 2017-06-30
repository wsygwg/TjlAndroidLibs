package cn.com.hiss.www.multilib.utils;

import android.util.Log;

import cn.com.hiss.www.multilib.utils.release.HissServerPara;
import cn.com.hiss.www.multilib.utils.release.HissServerType;

/**
 * Created by junliang on 2017/1/16.
 */

public class LongLog {
    //可以全局控制是否打印log日志
    private static boolean isPrintLog = (HissServerPara.serverType == HissServerType.TEST) ? true : false;

    private static int LOG_MAXLENGTH = 2000;

    public static void Logout(String msg) {
        if (isPrintLog) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e("Logou___" + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e("Logou___" + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void Logout(String type, String msg) {

        if (isPrintLog) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(type + "___" + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(type + "___" + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
