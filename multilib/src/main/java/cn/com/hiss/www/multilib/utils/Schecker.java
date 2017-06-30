package cn.com.hiss.www.multilib.utils;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junliang on 2017/2/24.
 */

public class Schecker {
    public static boolean isStringNull(String s) {
        if (s == null || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized static String getStringByKey(JSONObject obj, String key) throws Exception {
        if (obj.has(key)) {
            return Schecker.isStringNull(obj.getString(key)) ? "" : obj.getString(key);
        } else {
            return "";
        }
    }

    public synchronized static String getStringByKey(JSONObject obj, String key,String defalutValue) throws Exception {
        if (obj.has(key)) {
            return Schecker.isStringNull(obj.getString(key)) ? defalutValue : obj.getString(key);
        } else {
            return "";
        }
    }

    public synchronized static boolean getBooleanByKey(JSONObject obj, String key) throws Exception {
        if (obj.has(key)) {
            return obj.getBoolean(key);
        } else {
            return false;
        }
    }

    public static String validStr(String s) {
        return Schecker.isStringNull(s) ? "" : s;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）173
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static List<String> spliteString(String in,String sign){
        List<String> list = new ArrayList<>();
        try {
            String[] retArray = in.split(sign);
            for(String one : retArray){
                list.add(one);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return list;
        }
    }
}
