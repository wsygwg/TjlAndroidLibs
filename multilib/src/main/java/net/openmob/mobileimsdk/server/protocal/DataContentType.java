/**  
* 北京智普信科技股份有限公司
* Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd. 
* http://www.zeepson.com/
* All right reserved. 
*/ 

package net.openmob.mobileimsdk.server.protocal;

/** 
 * @Title: DataContentType 
 * @Description: TODO
 * @author wayne
 * @date 2016年10月11日 下午4:01:36 
 * @version V1.0   
 */
public interface DataContentType {
	/**
	 * 
	 * @Title: C 
	 * @Description: dataContent json字符串结构：{type:1,content:"xxxx"},标准表情根据表情符自动转换
	 * @author wayne
	 * @date 2016年10月11日 下午4:11:46 
	 * @version V1.0
	 */
	interface C{
		int MESSAGE_TYPE$TXT = 1;
		int MESSAGE_TYPE$MEDIA = 2;
		int MESSAGE_TYPE$VOICE = 3;
		int MESSAGE_TYPE$PIC = 4;
		int MESSAGE_TYPE$LOCATION = 5;
	}
}
