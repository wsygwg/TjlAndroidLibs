/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * MessageQoSEventImpl.java at 2016-2-20 11:20:18, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package cn.com.hiss.www.multilib.event;

import android.util.Log;

import net.openmob.mobileimsdk.android.event.MessageQoSEvent;
import net.openmob.mobileimsdk.server.protocal.Protocal;

import java.util.ArrayList;

public class MessageQoSEventImpl implements MessageQoSEvent {
    private final static String TAG = "EventImpl$$" + MessageQoSEventImpl.class.getSimpleName() + "$$";

    @Override
    public void messagesLost(ArrayList<Protocal> lostMessages) {
        Log.e(TAG, "收到系统的未实时送达事件通知，当前共有" + lostMessages.size() + "个包QoS保证机制结束，判定为【无法实时送达】！");
    }

    @Override
    public void messagesBeReceived(String theFingerPrint) {
        if (theFingerPrint != null) {
            Log.e(TAG, "收到对方已收到消息事件的通知，fp=" + theFingerPrint);
        }
    }
}
