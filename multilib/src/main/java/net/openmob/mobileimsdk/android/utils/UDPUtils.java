package net.openmob.mobileimsdk.android.utils;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPUtils {
    private static final String TAG = UDPUtils.class.getSimpleName();

    public static boolean send(DatagramSocket skt, byte[] d, int dataLen) {
        boolean ret = false;
        if (skt != null && d != null) {
            try {
                return send(skt, new DatagramPacket(d, dataLen));
            } catch (Exception e) {
                Log.e(TAG, "【IMCORE】send方法中》》发送UDP数据报文时出错了：remoteIp=" + skt.getInetAddress() + ", remotePort=" + skt.getPort() + ".原因是：" + e.getMessage(), e);
            }
        } else {
            Log.e(TAG, "【IMCORE】send方法中》》无效的参数：skt=" + skt);//
            // 解决google统计报告的bug: NullPointerException (@UDPUtils:send:30) {AsyncTask #4} +", d="+d+", remoteIp="+skt.getInetAddress()+", remotePort="+skt.getPort());
        }
        return ret;
    }

    public static synchronized boolean send(DatagramSocket skt, DatagramPacket p) {
        boolean ret = false;
        if ((skt != null) && (p != null)) {
            if (skt.isConnected()) {
                try {
                    skt.send(p);
                    ret = true;
                } catch (Exception e) {
                    Log.e(TAG, "【IMCORE】send方法中》》发送UDP数据报文时出错了，原因是：" + e.getMessage(), e);
                }
            }
        } else {
            Log.e(TAG, "【IMCORE】在send()UDP数据报时没有成功执行，原因是：skt==null || p == null!");
        }
        return ret;
    }
}