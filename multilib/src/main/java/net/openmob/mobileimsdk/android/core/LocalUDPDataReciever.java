/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * LocalUDPDataReciever.java at 2016-2-20 11:25:50, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.android.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import net.openmob.mobileimsdk.android.ClientCoreSDK;
import net.openmob.mobileimsdk.android.conf.ConfigEntity;
import net.openmob.mobileimsdk.server.protocal.Protocal;
import net.openmob.mobileimsdk.server.protocal.ProtocalFactory;
import net.openmob.mobileimsdk.server.protocal.ProtocalType;
import net.openmob.mobileimsdk.server.protocal.s.PErrorResponse;
import net.openmob.mobileimsdk.server.protocal.s.PLoginInfoResponse;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;
import java.util.Observer;

import cn.com.hiss.www.multilib.utils.TBroadcast;

public class LocalUDPDataReciever {
    private static final String TAG = LocalUDPDataReciever.class.getSimpleName();
    private Thread thread = null;
    private static LocalUDPDataReciever instance = null;
    private static MessageHandler messageHandler = null;
    private Context context = null;

    public static LocalUDPDataReciever getInstance(Context context) {
        if (instance == null) {
            instance = new LocalUDPDataReciever(context);
            messageHandler = new MessageHandler(context);
        }
        return instance;
    }

    private LocalUDPDataReciever(Context context) {
        this.context = context;
    }

    public void stop() {
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
    }

    public void startup() {
        stop();
        try {
            this.thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        if (ClientCoreSDK.DEBUG) {
                            Log.e(LocalUDPDataReciever.TAG, "【IMCORE】启动本地UDP端口侦听，端口=" + ConfigEntity.localUDPPort + "...");
                        }
                        //开始侦听
                        LocalUDPDataReciever.this.p2pListeningImpl();
                    } catch (Exception eee) {
                        Log.e(LocalUDPDataReciever.TAG, "【IMCORE】本地UDP监听停止了(socket被关闭了?)," + eee.getMessage(), eee);
                    }
                }
            });
            this.thread.start();
        } catch (Exception e) {
            Log.e(TAG, "【IMCORE】本地UDPSocket监听开启时发生异常," + e.getMessage(), e);
        }
    }

    private void p2pListeningImpl() throws Exception {
        while (true) {
            // 缓冲区
            byte[] data = new byte[1024];
            // 接收数据报的包
            DatagramPacket packet = new DatagramPacket(data, data.length);
            DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
            if ((localUDPSocket == null) || (localUDPSocket.isClosed())) {
                continue;
            }
            localUDPSocket.receive(packet);
            Message m = Message.obtain();
            m.obj = packet;
            messageHandler.sendMessage(m);
        }
    }

    /**
     * TODO:DATA RECIEVER
     */
    private static class MessageHandler extends Handler {
        private Context context = null;

        public MessageHandler(Context context) {
            this.context = context;
        }

        public void handleMessage(Message msg) {
            DatagramPacket packet = (DatagramPacket) msg.obj;
            if (packet == null) {
                return;
            }

            try {
                Protocal pFromServer = ProtocalFactory.parse(packet.getData(), packet.getLength());
                Gson gson = new Gson();
                String pString = gson.toJson(pFromServer);
                Log.e(TAG, "#############$$$$$$$$$$$$$$$$$%%%%%%%%%%%%%服务器端信息接收：pString = " + pString);
                if (pFromServer.isQoS()) {
                    //QOS应答包处理
                    if (QoS4ReciveDaemon.getInstance(this.context).hasRecieved(pFromServer.getFp())) {
                        if (ClientCoreSDK.DEBUG) {
                            Log.e(LocalUDPDataReciever.TAG, "【IMCORE】【QoS机制】" + pFromServer.getFp() + "已经存在于发送列表中，这是重复包，通知应用层已收到该包！");
                        }
                        //更新收到时间戳
                        QoS4ReciveDaemon.getInstance(this.context).addRecieved(pFromServer);
                        sendRecievedBack(pFromServer);
                        return;
                    }
                    /**发送应答包，加try.catch以防发送应答包报错从而无法进行消息的后续处理*/
                    try{
                        QoS4ReciveDaemon.getInstance(this.context).addRecieved(pFromServer);
                        if (pFromServer.getType() == ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$LOGIN) {
                            if (pFromServer.getTo().equals("-1")) {
                                //登陆失败
                                sendRecievedBack(pFromServer);
                            } else {
                                //登陆成功
                                sendLoginSuccessRecievedBack(pFromServer);
                            }
                        } else {
                            sendRecievedBack(pFromServer);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                TBroadcast.sendLocalStringBc(context, TBroadcast.BROADCAST_IM_MESSAGE_RECEIVED, pString);

                switch (pFromServer.getType()) {
                    //群组信息
                    case ProtocalType.C.FROM_CLIENT_TYPE_OF_GROUP: {
                        if (ClientCoreSDK.getInstance().getChatTransDataEvent() == null)
                            break;
                        //                        ClientCoreSDK.getInstance().getChatTransDataEvent().onTransBuffer(pFromServer.getFp(), pFromServer.getFrom(), pFromServer.getDataContent());
                        break;
                    }
                    //普通信息
                    case ProtocalType.C.FROM_CLIENT_TYPE_OF_COMMON$DATA: {
                        if (ClientCoreSDK.getInstance().getChatTransDataEvent() == null)
                            break;
                        //                        ClientCoreSDK.getInstance().getChatTransDataEvent().onTransBuffer(pFromServer.getFp(), pFromServer.getFrom(), pFromServer.getDataContent());
                        break;
                    }
                    //普通信息应答包
                    case ProtocalType.C.FROM_CLIENT_TYPE_OF_RECIVED: {
                        String theFingerPrint = pFromServer.getFp();
                        if (ClientCoreSDK.DEBUG) {
                            Log.e(LocalUDPDataReciever.TAG, "【IMCORE】【QoS】收到" + pFromServer.getFrom() + "发过来的指纹为" + theFingerPrint + "的应答包.");
                        }
                        if (ClientCoreSDK.getInstance().getMessageQoSEvent() != null) {
                            ClientCoreSDK.getInstance().getMessageQoSEvent().messagesBeReceived(theFingerPrint);
                        }
                        QoS4SendDaemon.getInstance(this.context).remove(theFingerPrint);
                        break;
                    }
                    //服务器心跳包
                    case ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$KEEP$ALIVE: {
                        if (ClientCoreSDK.DEBUG) {
                            Log.e(LocalUDPDataReciever.TAG, "【IMCORE】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4收到服务端回过来的Keep Alive心跳响应包.");
                        }
                        KeepAliveDaemon.getInstance(this.context).updateGetKeepAliveResponseFromServerTimstamp();
                        break;
                    }
                    case ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$LOGIN: {
                        PLoginInfoResponse loginInfoRes = ProtocalFactory.parsePLoginInfoResponse(pFromServer.getDataContent());
                        if (loginInfoRes.getCode() == 0) {
                            ClientCoreSDK.getInstance().setLoginHasInit(true).setCurrentUserId(loginInfoRes.getUser_id());
                            AutoReLoginDaemon.getInstance(this.context).stop();
                            KeepAliveDaemon.getInstance(this.context).setNetworkConnectionLostObserver(new Observer() {
                                public void update(Observable observable, Object data) {
                                    QoS4SendDaemon.getInstance(MessageHandler.this.context).stop();
                                    QoS4ReciveDaemon.getInstance(MessageHandler.this.context).stop();
                                    ClientCoreSDK.getInstance().setConnectedToServer(false);
                                    ClientCoreSDK.getInstance().setCurrentUserId("-1");
                                    ClientCoreSDK.getInstance().getChatBaseEvent().onLinkCloseMessage(-1);
                                    AutoReLoginDaemon.getInstance(MessageHandler.this.context).start(true);
                                }
                            });
                            KeepAliveDaemon.getInstance(this.context).start(false);
                            QoS4SendDaemon.getInstance(this.context).startup(true);
                            QoS4ReciveDaemon.getInstance(this.context).startup(true);
                            ClientCoreSDK.getInstance().setConnectedToServer(true);
                        } else {
                            ClientCoreSDK.getInstance().setConnectedToServer(false);
                            ClientCoreSDK.getInstance().setCurrentUserId("-1");
                        }
                        if (ClientCoreSDK.getInstance().getChatBaseEvent() == null)
                            break;
                        ClientCoreSDK.getInstance().getChatBaseEvent().onLoginMessage(loginInfoRes.getUser_id(), loginInfoRes.getCode());
                        break;
                    }
                    case ProtocalType.S.FROM_SERVER_TYPE_OF_RESPONSE$FOR$ERROR: {
                        PErrorResponse errorRes = ProtocalFactory.parsePErrorResponse(pFromServer.getDataContent());
                        if (errorRes.getErrorCode() == 301) {
                            ClientCoreSDK.getInstance().setLoginHasInit(false);
                            Log.e(LocalUDPDataReciever.TAG, "【IMCORE】&&&&&&&&&&&&&&&&&&收到服务端的“尚未登陆”的错误消息，心跳线程将停止，请应用层重新登陆.&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");
                            KeepAliveDaemon.getInstance(this.context).stop();
                            AutoReLoginDaemon.getInstance(this.context).start(false);
                        }
                        if (ClientCoreSDK.getInstance().getChatTransDataEvent() == null)
                            break;
                        ClientCoreSDK.getInstance().getChatTransDataEvent().onErrorResponse(errorRes.getErrorCode(), errorRes.getErrorMsg());
                        break;
                    }
                    default:
                        Log.e(LocalUDPDataReciever.TAG, "【IMCORE】收到的服务端消息类型：" + pFromServer.getType() + "，但目前该类型客户端不支持解析和处理！");
                }
            } catch (Exception e) {
                Log.e(LocalUDPDataReciever.TAG, "【IMCORE】处理消息的过程中发生了错误.", e);
            }
        }

        /**
         * 发送应答包，任何接收的信息都需要发送应答包，应答包只负责发送给服务器，所以to是0，应答包中FP就是接收包中的FP，一条来，一条回，一一对应，目的是让发送者收到这个应答包然后发送者在QOS机制中移除他的发送包
         *
         * @param pFromServer
         */
        private void sendRecievedBack(final Protocal pFromServer) {
            if (pFromServer.getFp() != null) {
                new LocalUDPDataSender.SendCommonDataAsync(ProtocalFactory.createRecivedBack(pFromServer.getTo(), "0", pFromServer.getFp(), pFromServer.getType())) {
                    @Override
                    protected void onPostExecute(Integer code) {
                        if (ClientCoreSDK.DEBUG)
                            Log.e(TAG, "【IMCORE】【QoS】向" + pFromServer.getFrom() + "发送" + pFromServer.getFp() + "包的应答包成功,from=" + pFromServer.getTo() + "！");
                    }
                }.execute();
            } else {
                Log.e(TAG, "【IMCORE】【QoS】收到" + pFromServer.getFrom() + "发过来需要QoS的包，但它的指纹码却为null！无法发应答包！");
            }
        }

        /**
         * 发送应答包，任何接收的信息都需要发送应答包，应答包中FP就是接收包中的FP，一条来，一条回，一一对应，目的是让发送者收到这个应答包然后发送者在QOS机制中移除他的发送包
         *
         * @param pFromServer
         */
        private void sendLoginSuccessRecievedBack(final Protocal pFromServer) {
            if (pFromServer.getFp() != null) {
                new LocalUDPDataSender.SendCommonDataAsync(ProtocalFactory.createLoginSuccessResponse4Back(pFromServer.getTo(), pFromServer.getFp())) {
                    @Override
                    protected void onPostExecute(Integer code) {
                        if (ClientCoreSDK.DEBUG)
                            Log.e(TAG, "【IMCORE】【QoS】向" + pFromServer.getFrom() + "发送" + pFromServer.getFp() + "包的应答包成功,from=" + pFromServer.getTo() + "！");
                    }
                }.execute();
            } else {
                Log.e(TAG, "【IMCORE】【QoS】收到" + pFromServer.getFrom() + "发过来需要QoS的包，但它的指纹码却为null！无法发应答包！");
            }
        }
    }
}