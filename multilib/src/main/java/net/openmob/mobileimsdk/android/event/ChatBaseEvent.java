/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * ChatBaseEvent.java at 2016-2-20 11:25:50, code by Jack Jiang.
 * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 */
package net.openmob.mobileimsdk.android.event;

public abstract interface ChatBaseEvent
{
  /**
   * 登录信息反馈
   * @param paramInt1
   * @param paramInt2
     */
  public abstract void onLoginMessage(String paramInt1, int paramInt2);

  /**
   * 链接丢失信息反馈
   * @param paramInt
     */
  public abstract void onLinkCloseMessage(int paramInt);
}