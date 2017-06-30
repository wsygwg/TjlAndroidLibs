/**  
* 北京智普信科技股份有限公司
* Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd. 
* http://www.zeepson.com/
* All right reserved. 
*/ 

package net.openmob.mobileimsdk.server.protocal.c;

/** 
 * @Title: PGroupResponse 
 * @Description: TODO
 * @author wayne
 * @date 2016年11月30日 下午5:48:53 
 * @version V1.0   
 */
public class PGroupResponse {
	private String groupId;
	private boolean result;
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
	/**
	 * @return result 
	 */
	
	public boolean getResult() {
		return result;
	}
	/** 
	 * @param result 要设置的 result 
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	/** 
	 *  
	 *  
	 * @param groupId
	 * @param result 
	 */ 
	
	public PGroupResponse(String groupId, boolean result) {
		super();
		this.groupId = groupId;
		this.result = result;
	}
	/** 
	 *  
	 *   
	 */ 
	
	public PGroupResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
