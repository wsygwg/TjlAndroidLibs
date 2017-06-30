/**  
* 北京智普信科技股份有限公司
* Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd. 
* http://www.zeepson.com/
* All right reserved. 
*/ 

package net.openmob.mobileimsdk.server.protocal.c;

/** 
 * @Title: PGroupRequest 
 * @Description: 进群申请请求内容体
 * @author wayne
 * @date 2016年11月30日 下午3:37:56 
 * @version V1.0   
 */
public class PGroupRequest {

	private String content;
	private String groupId;
	
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
	 * @return createDate 
	 */
	

	/** 
	 *  
	 *  
	 * @param type
	 * @param groupId
	 * @param content
	 * @param createDate 
	 */ 
	
	public PGroupRequest(String content,
			String groupId) {
		super();
		this.content = content;
		this.groupId = groupId;
	}
	/**
	 * @return groupId 
	 */
	
	public String getGroupId() {
		return groupId;
	}
	/** 
	 * @param groupId 要设置的 groupId 
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public PGroupRequest() {
		super();
	}
	
}
