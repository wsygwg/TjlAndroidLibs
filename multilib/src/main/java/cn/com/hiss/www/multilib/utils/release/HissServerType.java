package cn.com.hiss.www.multilib.utils.release;

/**
 * Created by tao on 2017/5/17.
 */

public enum HissServerType {
    TEST("http://youdianwang.iask.in", "18080", "youdianwang.iask.in", 7901, 0), RELEASE("http://ws.dianm.com.cn", "8080", "ws.dianm.com.cn", 7901, 0);
    private String siteUrl;
    private String sitePort;
    private String imServerIp;
    private int imServerUdpPort;
    private int imLocalUdpPort;

    HissServerType(String siteUrl, String sitePort, String imServerIp, int imServerUdpPort, int imLocalUdpPort) {
        this.siteUrl = siteUrl;
        this.sitePort = sitePort;
        this.imServerIp = imServerIp;
        this.imServerUdpPort = imServerUdpPort;
        this.imLocalUdpPort = imLocalUdpPort;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getSitePort() {
        return sitePort;
    }

    public String getImServerIp() {
        return imServerIp;
    }

    public int getImServerUdpPort() {
        return imServerUdpPort;
    }

    public int getImLocalUdpPort() {
        return imLocalUdpPort;
    }
}
