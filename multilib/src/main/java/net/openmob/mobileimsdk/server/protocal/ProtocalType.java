/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ProtocalType.java at 2016-2-20 11:26:02, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.server.protocal;

public interface ProtocalType {
    /**
     * from client
     */
    interface C {
        int FROM_CLIENT_TYPE_OF_LOGIN = 0;
        int FROM_CLIENT_TYPE_OF_KEEP$ALIVE = 1;
        int FROM_CLIENT_TYPE_OF_COMMON$DATA = 2;
        int FROM_CLIENT_TYPE_OF_LOGOUT = 3;
        int FROM_CLIENT_TYPE_OF_RECIVED = 4;
        int FROM_CLIENT_TYPE_OF_ECHO = 5;
        int FROM_CLIENT_TYPE_OF_GROUP = 6;//群信息
        int FROM_CLIENT_TYPE_OF_GROUP$SERVER$RECIVED = 7;//群信息的某个接收用户接收到信息后的应答，应答给服务器
        int FROM_CLIENT_TYPE_OF_LOGIN$RESPONSE$SUCCESS$BACK = 8;//登陆请求验证通过后发送的应答包
        int FROM_CLIENT_TYPE_OF_FRIEND$REQUEST = 9;//添加好友请求
        int FROM_CLIENT_TYPE_OF_FRIEND$RESPONSE$RESULT = 10;//对于好友请求的回答
        int FROM_CLIENT_TYPE_OF_FRIEND$RESPONSE$REFUSE=11;//拒绝添加好友
        int FROM_CLIEND_TYPE_OF_FRIEND$REQUEST$DELETE = 12;//删除好友
        int FROM_CLIENT_TYPE_OF_GROUP$REQUEST = 13;//申请进群
        int FROM_CLIENT_TYPE_OF_GROUP$RESPONSE = 14;//申请进群群主应答
        int FROM_CLIENT_TYPE_OF_GROUP$INIT = 15;//建群，或者被一个自己没有的群主动拉进群，通知被拉的用户，让其初始化该群信息
    }

    /**
     * from server
     */
    interface S {
        int FROM_SERVER_TYPE_OF_RESPONSE$LOGIN = 50;
        int FROM_SERVER_TYPE_OF_RESPONSE$KEEP$ALIVE = 51;
        int FROM_SERVER_TYPE_OF_RESPONSE$FOR$ERROR = 52;
        int FROM_SERVER_TYPE_OF_RESPONSE$ECHO = 53;
        int FROM_SERVER_TYPE_OF_FRIEND$AUTION$DELETED = 101;//被删除好友通知
        int FROM_SERVER_TYPE_OF_AUTION$LOGOUT = 20;//通知手机端退出通知
        int FROM_SERVER_TYPE_OF_AUTION$GROUP$NEWMEMBER = 21;//群中有新成员加入通知
        int FROM_SERVER_TYPE_OF_AUTION$GROUP$SIGNOUT = 22;//某人退群通知群众所有人
        int FROM_SERVER_TYPE_OF_AUTION$GROUP$DISSLUTION = 23;//群解散，通知所有人
    }

    interface SNS {
        int SNS_AUTION_FEED_AUTION = 1001;
    }
}