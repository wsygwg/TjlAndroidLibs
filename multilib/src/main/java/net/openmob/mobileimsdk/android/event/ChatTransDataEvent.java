/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ChatTransDataEvent.java at 2016-2-20 11:25:50, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.android.event;

public abstract interface ChatTransDataEvent
{
  /**
   * 聊天消息接收
   * @param paramString1
   * @param paramInt
   * @param paramString2
   */
  public abstract void onTransBuffer(String paramString1, String paramInt, String paramString2);

  /**
   * 聊天消息接收错误反馈
   * @param paramInt
   * @param paramString
   */
  public abstract void onErrorResponse(int paramInt, String paramString);
}