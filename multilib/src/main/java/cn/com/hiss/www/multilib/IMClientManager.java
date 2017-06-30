/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * IMClientManager.java at 2016-2-20 11:20:18, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package cn.com.hiss.www.multilib;

import android.content.Context;
import android.util.Log;

import net.openmob.mobileimsdk.android.ClientCoreSDK;
import net.openmob.mobileimsdk.android.conf.ConfigEntity;

import cn.com.hiss.www.multilib.event.ChatBaseEventImpl;
import cn.com.hiss.www.multilib.event.ChatTransDataEventImpl;
import cn.com.hiss.www.multilib.event.MessageQoSEventImpl;
import cn.com.hiss.www.multilib.utils.release.HissServerPara;

/**
 * IM设置总控制类
 */
public class IMClientManager {
    private static String TAG = IMClientManager.class.getSimpleName();

    private static IMClientManager instance = null;
////    public static final String APP_KEY = "5418023dfd98c579b6001741";
////    public static final String serverIP = "youdianwang.iask.in";
//    public static final String serverIP = "ws.dianm.com.cn";
//    public static int serverUDPPort = 7901;                         //测试IM端口与正式的相同
//    public static int localUDPPort = 0;
    /** MobileIMSDK是否已被初始化. true表示已初化完成，否则未初始化. */
    private boolean init = false;
    private ChatBaseEventImpl baseEventListener = null;
    private ChatTransDataEventImpl transDataListener = null;
    private MessageQoSEventImpl messageQoSListener = null;
    private Context context = null;

    public static IMClientManager getInstance(Context context) {
        if (instance == null)
            instance = new IMClientManager(context);
        return instance;
    }

    public boolean isInit() {
        return init;
    }

    private IMClientManager(Context context) {
        this.context = context;
        initMobileIMSDK();
    }

    public void initMobileIMSDK() {
        if (!init) {
            Log.e(TAG,"HissStickyService 执行initMobileIMSDK");
            // MobileIMSDK核心IM框架的敏感度模式设置
            ConfigEntity.serverIP = HissServerPara.serverType.getImServerIp();
            ConfigEntity.serverUDPPort = HissServerPara.serverType.getImServerUdpPort();
            ConfigEntity.localUDPPort = HissServerPara.serverType.getImLocalUdpPort();
			ConfigEntity.setSenseMode(ConfigEntity.SenseMode.MODE_10S);
            // 开启/关闭DEBUG信息输出
//	    	ClientCoreSDK.DEBUG = false;

            // 【特别注意】请确保首先进行核心库的初始化（这是不同于iOS和Java端的地方)
            ClientCoreSDK.getInstance().init(this.context);

            // 设置事件回调
            baseEventListener = new ChatBaseEventImpl();
            transDataListener = new ChatTransDataEventImpl();
            messageQoSListener = new MessageQoSEventImpl();
            ClientCoreSDK.getInstance().setChatBaseEvent(baseEventListener);
            ClientCoreSDK.getInstance().setChatTransDataEvent(transDataListener);
            ClientCoreSDK.getInstance().setMessageQoSEvent(messageQoSListener);
            init = true;
        }else{
            Log.e(TAG,"HissStickyService initMobileIMSDK无需执行，IM健在");
        }
    }

    public void release() {
        ClientCoreSDK.getInstance().releaseResources();
        init = false;
    }

    public ChatTransDataEventImpl getTransDataListener() {
        return transDataListener;
    }

    public ChatBaseEventImpl getBaseEventListener() {
        return baseEventListener;
    }

    public MessageQoSEventImpl getMessageQoSListener() {
        return messageQoSListener;
    }
}
