package cn.com.hiss.www.multilib.utils.release;

/**
 * Created by tao on 2017/5/17.
 */

public enum HissHttpUtilType {
    TAO("/health-webservice/rest/"),XIN("/health-webservice/");
    private String directory;
    HissHttpUtilType(String directory){
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
