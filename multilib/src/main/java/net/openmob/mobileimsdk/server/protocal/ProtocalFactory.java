/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ProtocalFactory.java at 2016-2-20 11:26:02, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.server.protocal;

import com.google.gson.Gson;

import net.openmob.mobileimsdk.server.protocal.c.PKeepAlive;
import net.openmob.mobileimsdk.server.protocal.c.PLoginInfo;
import net.openmob.mobileimsdk.server.protocal.s.PErrorResponse;
import net.openmob.mobileimsdk.server.protocal.s.PKeepAliveResponse;
import net.openmob.mobileimsdk.server.protocal.s.PLoginInfoResponse;

public class ProtocalFactory {
    private static String create(Object c) {
        return new Gson().toJson(c);
    }

    public static String createDataContentStr(Object c) {
        return create(c);
    }

    public static <T> T parse(byte[] fullProtocalJASOnBytes, int len, Class<T> clazz) {
        return parse(CharsetHelper.getString(fullProtocalJASOnBytes, len), clazz);
    }

    public static <T> T parse(String dataContentOfProtocal, Class<T> clazz) {
        return new Gson().fromJson(dataContentOfProtocal, clazz);
    }

    public static Protocal parse(byte[] fullProtocalJASOnBytes, int len) {
        return (Protocal) parse(fullProtocalJASOnBytes, len, Protocal.class);
    }

    /**
     * 服务器发给客户端的心跳响应包，from为0，是服务器
     *
     * @param to_user_id
     * @return Protocal
     */
    public static Protocal createPKeepAliveResponse(String to_user_id) {
        return new Protocal(ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$KEEP$ALIVE, create(new PKeepAliveResponse()), "0", to_user_id);
    }

    /**
     * 此方法的使用场景：
     * client发送登陆请求，服务器接收到请求后判断登陆信息正确，回复client，client接收到是登陆正确信息后，对服务器的再次应答
     *
     * @param from_user_id 发送人id，调用的地方叫接收人id
     * @return
     */
    public static Protocal createLoginSuccessResponse4Back(String from_user_id, String fingerPrint) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_LOGIN$RESPONSE$SUCCESS$BACK, "", from_user_id, "0", false, fingerPrint);
    }

    public static PKeepAliveResponse parsePKeepAliveResponse(String dataContentOfProtocal) {
        return (PKeepAliveResponse) parse(dataContentOfProtocal, PKeepAliveResponse.class);
    }

    /**
     * 发给服务器的心跳包，to为0
     *
     * @param from_user_id
     * @return Protocal
     */
    public static Protocal createPKeepAlive(String from_user_id) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_KEEP$ALIVE, create(new PKeepAlive()), from_user_id, "0");
    }

    public static PKeepAlive parsePKeepAlive(String dataContentOfProtocal) {
        return (PKeepAlive) parse(dataContentOfProtocal, PKeepAlive.class);
    }

    public static Protocal createPErrorResponse(int errorCode, String errorMsg, String user_id) {
        /**
         * qos=false;fingerPrint=null的Protocal对象
         */
        return new Protocal(ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$FOR$ERROR, create(new PErrorResponse(errorCode, errorMsg)), "0", user_id);
    }

    public static PErrorResponse parsePErrorResponse(String dataContentOfProtocal) {
        return (PErrorResponse) parse(dataContentOfProtocal, PErrorResponse.class);
    }

    /**
     * 发给服务器的退出包，to是服务器值为0
     *
     * @param user_id
     * @param loginName
     * @return Protocal
     */
    public static Protocal createPLoginoutInfo(String user_id, String loginName) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_LOGOUT
                //				, create(new PLogoutInfo(user_id, loginName))
                , null, user_id, "0");
    }

    /**
     * 未登录情况下发送给服务器的登陆信息，from为-1，to为0
     *
     * @param loginName
     * @param loginPsw
     * @param extra
     * @return Protocal
     */
    public static Protocal createPLoginInfo(String loginName, String loginPsw, String extra) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_LOGIN, create(new PLoginInfo(loginName, loginPsw, extra)), "-1", "0");
    }

    public static PLoginInfo parsePLoginInfo(String dataContentOfProtocal) {
        return (PLoginInfo) parse(dataContentOfProtocal, PLoginInfo.class);
    }

    /**
     * 服务器返回的应答包，服务器id为0
     *
     * @param code
     * @param user_id
     * @return Protocal
     */
    public static Protocal createPLoginInfoResponse(int code, String user_id) {
        return new Protocal(ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$LOGIN, create(new PLoginInfoResponse(code, user_id)), "0", user_id, true, Protocal.genFingerPrint());
    }

    public static PLoginInfoResponse parsePLoginInfoResponse(String dataContentOfProtocal) {
        return (PLoginInfoResponse) parse(dataContentOfProtocal, PLoginInfoResponse.class);
    }

    /**
     * 正常数据包,带QoS, fingerPrint机制
     *
     * @param dataContent
     * @param from_user_id
     * @param to_user_id
     * @param QoS
     * @param fingerPrint
     * @return Protocal
     */
    public static Protocal createCommonData(String dataContent, String from_user_id, String to_user_id, boolean QoS, String fingerPrint) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_COMMON$DATA, dataContent, from_user_id, to_user_id, QoS, fingerPrint);
    }

    /**
     * 正常数据包，不带QoS, fingerPrint机制
     *
     * @param dataContent
     * @param from_user_id
     * @param to_user_id
     * @return Protocal
     */
    public static Protocal createCommonData(String dataContent, String from_user_id, String to_user_id) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_COMMON$DATA, dataContent, from_user_id, to_user_id);
    }

    /**
     * 心跳包应答包，无content，不需要qos机制
     *
     * @param from_user_id
     * @param to_user_id
     * @param recievedMessageFingerPrint
     * @return Protocal
     */
    public static Protocal createRecivedBack(String from_user_id, String to_user_id, String recievedMessageFingerPrint, int type) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_RECIVED, type + "", from_user_id, to_user_id, false, recievedMessageFingerPrint);
    }

    /**
     * 回复服务器群信息收到应答包，不需要QoS支持
     *
     * @param from_user_id
     * @param to_user_id
     * @param recievedMessageFingerPrint
     * @return
     */
    public static Protocal createGroupRecivedBack(String from_user_id, String to_user_id, String recievedMessageFingerPrint) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_GROUP, null, from_user_id, to_user_id, false, recievedMessageFingerPrint);
    }

    /**
     * 创建群组信息协议
     * @param dataContent
     * @param from_user_id
     * @param group_id
     * @param QoS
     * @return
     */
    public static Protocal createCommonGroupData(String dataContent, String from_user_id, String group_id, boolean QoS) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_GROUP, dataContent, from_user_id, group_id, QoS, null);
    }

    //添加好友请求协议
    public static Protocal createFriendAsk(String fromId, String dataContent, String toId) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_FRIEND$REQUEST, dataContent, fromId, toId, true, null);
    }

    //回应好友请求协议
    public static Protocal createFriendResponse(String fromId, String dataContent, String toId) {
        return new Protocal(ProtocalType.C.FROM_CLIENT_TYPE_OF_FRIEND$RESPONSE$RESULT, dataContent, fromId, toId, true, null);
    }
}