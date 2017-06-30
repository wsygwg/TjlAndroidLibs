/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * Protocal.java at 2016-2-20 11:26:03, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.server.protocal;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author wayne
 * @version V1.0
 * @Title: Protocal
 * @Description: 协议报文对象. from to userId默认为0，是服务器id
 * @date 2016年9月30日 下午3:25:17
 */
public class Protocal implements Serializable {
    /**
     * @Fields serialVersionUID : TODO
     */

    private static final long serialVersionUID = 1L;
    private int type = 0;
    private String dataContent = null;
    private String from = "0";
    private String to = "0";
    private String fp = null;
    private boolean QoS = false;
    private transient int retryCount = 0;

    /**
     * 构造方法（QoS标记默认为false）。
     *
     * @param type        协议类型
     * @param dataContent 协议数据内容
     * @param from        消息发出方的id（当用户登陆时，此值可不设置）
     * @param to          消息接收方的id（当用户退出时，此值可不设置）
     */
    public Protocal(int type, String dataContent, String from, String to) {
        this(type, dataContent, from, to, false, null);
    }

    public Protocal() {

    }

    /**
     * @param type
     * @param dataContent
     * @param from
     * @param to
     * @param QoS         是否需要QoS支持，true表示是，否则不需要
     * @param fingerPrint
     */
    public Protocal(int type, String dataContent, String from, String to, boolean QoS, String fingerPrint) {
        this.type = type;
        this.dataContent = dataContent;
        this.from = from;
        this.to = to;
        this.QoS = QoS;

        // 只有在需要QoS支持时才生成指纹，否则浪费数据传输流量
        // 目前一个包的指纹只在对象建立时创建哦
        if ((QoS) && (fingerPrint == null))
            this.fp = genFingerPrint();
        else
            this.fp = fingerPrint;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataContent() {
        return this.dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFp() {
        return this.fp;
    }

    /**
     * 本字段仅用于QoS时：表示丢包重试次数。
     * 本值是transient类型，对象序列化时将会忽略此字段。
     *
     * @return int
     */
    public int getRetryCount() {
        return this.retryCount;
    }

    /**
     * 本方法仅用于QoS时：选出包重试次数+1。
     * 本方法理论上由MobileIMSDK内部调用，应用层无需额外调用。
     * void
     */
    public void increaseRetryCount() {
        this.retryCount += 1;
    }

    /**
     * 是否需QoS支持。
     *
     * @return boolean  true表示是，否则不是
     */
    public boolean isQoS() {
        return this.QoS;
    }

    public String toGsonString() {
        return new Gson().toJson(this);
    }

    public byte[] toBytes() {
        return CharsetHelper.getBytes(toGsonString());
    }

    public Object clone() {
        // 克隆一个Protocal对象（该对象已重置retryCount数值为0）
        Protocal cloneP = new Protocal(getType(), getDataContent(), getFrom(), getTo(), isQoS(), getFp());
        return cloneP;
    }

    /**
     * 返回QoS需要的消息包的指纹特征码.
     * 重要说明：使用系统时间戳作为指纹码，则意味着只在Protocal生成的环境中可能唯一. 它存在重复的可能性有2种：
     * 1) 比如在客户端生成时如果生成过快的话（时间戳最小单位是1毫秒，如1毫秒内生成 多个指纹码），理论上是有重复可能性；
     * 2) 不同的客户端因为系统时间并不完全一致，理论上也是可能存在重复的，所以唯一性应是：好友+指纹码才对.
     * 目前使用的UUID基本能保证全局唯一，但它有36位长（加上分隔符32+4），目前为了保持框架的算法可读性 暂时不进行优化，以后可考虑使用2进制方式或者Protobuffer实现。
     *
     * @return 指纹特征码实际上就是系统的当时时间戳
     * 另请参见：
     * System.currentTimeMillis()
     */
    public static String genFingerPrint() {
        return UUID.randomUUID().toString();
    }


    public Protocal protocalClone(){
        Gson gson = new Gson();
        String str = gson.toJson(this);
        Protocal p = gson.fromJson(str,Protocal.class);
        return p;
    }
}