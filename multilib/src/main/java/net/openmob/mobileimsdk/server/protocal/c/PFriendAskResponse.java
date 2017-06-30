/**
 * 北京智普信科技股份有限公司
 * Copyright Right (c) 2012-2015 Beijing Zeepson technology Co.,Ltd.
 * http://www.zeepson.com/
 * All right reserved.
 */

package net.openmob.mobileimsdk.server.protocal.c;

/**
 * @author wayne
 * @version V1.0
 * @Title: PFriendAskResponse
 * @Description: 好友请求的答复内容体
 * @date 2016年11月1日 下午1:26:29
 */
public class PFriendAskResponse {

    private boolean result;
    private String reason;

    public PFriendAskResponse() {
        super();
    }

    public PFriendAskResponse(boolean result, String reason) {
        super();
        this.result = result;
        this.reason = reason;
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
     * @return reason
     */

    public String getReason() {
        return reason;
    }

    /**
     * @param reason 要设置的 reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


}
