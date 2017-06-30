package cn.com.hiss.www.multilib.utils.release;

/**
 * Created by tao on 2017/5/17.
 */

public class HissServerPara {
    public static final HissServerType serverType = HissServerType.TEST;
    public static String getOrderUrl(HissHttpUtilType hissHttpUtilType) {
        return serverType.getSiteUrl() + ":" + serverType.getSitePort() + hissHttpUtilType.getDirectory();
    }
}
