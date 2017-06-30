package cn.com.hiss.www.multilib.utils;

/**
 * Created by wuyanzhe on 2017/4/17.
 */

public class FloatDecorator {

    public static String getFloatStr(String milliInt) {
        try{
            float a = Float.valueOf(milliInt) / 1000;
            float b = (float) (Math.round(a * 10)) / 10;//(这里的10就是1位小数点, 如果要其它位, 如4位, 这里两个100改成10000)
            return b + "\"";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
