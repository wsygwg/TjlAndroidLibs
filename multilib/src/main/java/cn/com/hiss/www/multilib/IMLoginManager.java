package cn.com.hiss.www.multilib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;

/**
 * Created by wuyanzhe on 2017/3/7.
 */

public class IMLoginManager {
    private static final String TAG = IMLoginManager.class.getSimpleName();
    /**
     * 收到服务端的登陆完成反馈时要通知的观察者（因登陆是异步实现，本观察者将由
     * ChatBaseEvent 事件的处理者在收到服务端的登陆反馈后通知之）
     */
//    private Observer onLoginSucessObserver = null;
    private OnSendResult result;

    public void setResult(OnSendResult result) {
        this.result = result;
    }

    public interface OnSendResult {
        void onSuccess();

        void onFailed();
    }

    private IMLoginManager(OnSendResult result) {
        this.result = result;
    }

    private static IMLoginManager manager;

    public static IMLoginManager getInstance(OnSendResult result) {
        if (manager == null) {
            manager = new IMLoginManager(result);
        } else {
            manager.setResult(result);
        }
        return manager;
    }

    /**
     * IM登陆处理。
     */
    public void doLogin(Context con, String loginName, String loginPw) {
        if (!CheckNetworkState(con))
            return;
        // * 设置好服务端反馈的登陆结果观察者（当客户端收到服务端反馈过来的登陆消息时将被通知）
//        IMClientManager.getInstance(con).getBaseEventListener().setLoginOkForLaunchObserver(onLoginSucessObserver);
        // 异步提交登陆名和密码
        new LocalUDPDataSender.SendLoginDataAsync(con, loginName, loginPw) {
            /**
             * 登陆信息发送完成后将调用本方法（注意：此处仅是登陆信息发送完成
             * ，真正的登陆结果要在异步回调中处理哦）。
             *
             * @param code 数据发送返回码，0 表示数据成功发出，否则是错误码
             */
            @Override
            protected void fireAfterSendLogin(int code) {
                if (code == 0) {
                    Log.e(TAG, "登陆信息已成功发出！");
                    if (result != null) {
                        result.onSuccess();
                    }
                } else {
                    Log.e(TAG, "数据发送失败。错误码是：" + code + "！");
                    // * 登陆信息没有成功发出时当然无条件取消显示登陆进度条
                    if (result != null) {
                        result.onFailed();
                    }
                }
            }
        }.execute();
    }

    private boolean CheckNetworkState(Context con) {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            Log.e(TAG, "网络未连接，请检查您的网络。。。");
        }
        return flag;
    }
}
