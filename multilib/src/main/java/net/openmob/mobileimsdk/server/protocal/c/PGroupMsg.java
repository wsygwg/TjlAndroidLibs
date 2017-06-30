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
 * @Title: PGroupMsg
 * @Description: TODO
 * @date 2016年10月12日 下午3:16:22
 */
public class PGroupMsg {
    private String type;
    private String groupId;
    private Object content;
    private String createDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
