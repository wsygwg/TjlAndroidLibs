/**  
* 北京智普信科技股份有限公司
* Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd. 
* http://www.zeepson.com/
* All right reserved. 
*/ 

package net.openmob.mobileimsdk.server.protocal.c;

/** 
 * @Title: PP2PMsg 
 * @Description: TODO
 * @author wayne
 * @date 2016年10月12日 下午3:17:52 
 * @version V1.0   
 */
public class PP2PMsg {

	public static final String TXT = "1";
	public static final String VIDEO = "2";
	public static final String VOICE = "3";
	public static final String IMAGE = "4";
	public static final String POSITION = "5";

    public static final String TRANS_IN = "in";
    public static final String TRANS_OUT = "out";

	private String type;
	private Object content;
	private String createDate;

	/**
	 * @return createDate 
	 */
	
	public String getCreateDate() {
		return createDate;
	}
	/** 
	 * @param createDate 要设置的 createDate 
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 *  
	 *  
	 * @param type
	 * @param content 
	 */ 
	
	public PP2PMsg(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}

	public PP2PMsg() {
		super();
	}
	
}
