/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ChatTransDataEventImpl.java at 2016-2-20 11:20:18, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package cn.com.hiss.www.multilib.event;

import android.util.Log;

import net.openmob.mobileimsdk.android.event.ChatTransDataEvent;

//import com.im.hiss.ui.MainActivity;

/**
 * 聊天消息接收 && 聊天消息接收错误反馈
 */
public class ChatTransDataEventImpl implements ChatTransDataEvent {
    private final static String TAG = "EventImpl$$" + ChatTransDataEventImpl.class.getSimpleName() + "$$";

    @Override
    public void onTransBuffer(String fingerPrintOfProtocal, String dwUserid, String dataContent) {
        Log.e(TAG, "收到来自用户" + dwUserid + "的消息:" + dataContent);
    }

    @Override
    public void onErrorResponse(int errorCode, String errorMsg) {
        Log.e(TAG, "收到服务端错误消息，errorCode=" + errorCode + ", errorMsg=" + errorMsg);
    }
}
