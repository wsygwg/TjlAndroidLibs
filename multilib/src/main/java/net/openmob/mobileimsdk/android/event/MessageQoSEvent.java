/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * MessageQoSEvent.java at 2016-2-20 11:25:50, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.android.event;

import net.openmob.mobileimsdk.server.protocal.Protocal;

import java.util.ArrayList;

public abstract interface MessageQoSEvent {

    /**
     * 消息丢失反馈
     * @param paramArrayList
     */
    public abstract void messagesLost(ArrayList<Protocal> paramArrayList);

    /**
     * 消息已接收反馈
     * @param paramString
     */
    public abstract void messagesBeReceived(String paramString);
}