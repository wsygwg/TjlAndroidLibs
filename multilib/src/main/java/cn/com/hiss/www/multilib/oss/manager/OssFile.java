package cn.com.hiss.www.multilib.oss.manager;

/**
 * Created by wuyanzhe on 2017/3/16.
 */

public class OssFile {
    private String filePath;
    private String ossPath;
    private OssSetting.HissResType type;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public OssSetting.HissResType getType() {
        return type;
    }

    public void setType(OssSetting.HissResType type) {
        this.type = type;
    }
}
