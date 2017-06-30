/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ChatBaseEventImpl.java at 2016-2-20 11:20:18, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package cn.com.hiss.www.multilib.event;

import android.util.Log;

import net.openmob.mobileimsdk.android.event.ChatBaseEvent;

import java.util.Observer;

/**
 * 登录信息反馈 && 链接丢失信息反馈
 */
public class ChatBaseEventImpl implements ChatBaseEvent {
    private final static String TAG = "EventImpl$$" + ChatBaseEventImpl.class.getSimpleName() + "$$";
    // 本Observer目前仅用于登陆时（因为登陆与收到服务端的登陆验证结果是异步的，所以有此观察者来完成收到验证后的处理）
    private Observer loginOkForLaunchObserver = null;

    @Override
    public void onLoginMessage(String dwUserId, int dwErrorCode) {
        if (dwErrorCode == 0) {
            Log.e(TAG,"登录成功,id=" + dwUserId);
        } else {
            Log.e(TAG,"登录失败,code=" + dwErrorCode);
        }
        // 此观察者只有开启程序首次使用登陆界面时有用
        if (loginOkForLaunchObserver != null) {
            loginOkForLaunchObserver.update(null, dwErrorCode);
            loginOkForLaunchObserver = null;
        }
    }

    @Override
    public void onLinkCloseMessage(int dwErrorCode) {
        Log.e(TAG, "服务器连接已断开，error：" + dwErrorCode);
    }

    public void setLoginOkForLaunchObserver(Observer loginOkForLaunchObserver) {
        this.loginOkForLaunchObserver = loginOkForLaunchObserver;
    }
}
