/**  
* 北京智普信科技股份有限公司
* Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd. 
* http://www.zeepson.com/
* All right reserved. 
*/ 

package net.openmob.mobileimsdk.server.protocal.c;

/** 
 * @Title: PGroupSignoutAution 
 * @Description: 退群通知的datacontent消息体
 * @author wayne
 * @date 2017年2月11日 下午3:32:04 
 * @version V1.0   
 */
public class PGroupSignoutAution {
	private String content;
	private String memberId;
	/**
	 * @return content 
	 */
	
	public String getContent() {
		return content;
	}
	/** 
	 * @param content 要设置的 content 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return memberId 
	 */
	
	public String getMemberId() {
		return memberId;
	}
	/** 
	 * @param memberId 要设置的 memberId 
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/** 
	 *  
	 *   
	 */ 
	
	public PGroupSignoutAution() {
		super();
		// TODO Auto-generated constructor stub
	}
	/** 
	 *  
	 *  
	 * @param content
	 * @param memberId 
	 */ 
	
	public PGroupSignoutAution(String content, String memberId) {
		super();
		this.content = content;
		this.memberId = memberId;
	}
	
}
