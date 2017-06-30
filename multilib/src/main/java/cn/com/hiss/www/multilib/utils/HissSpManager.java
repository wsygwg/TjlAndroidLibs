package cn.com.hiss.www.multilib.utils;

import android.content.Context;

public class HissSpManager {

    public static final String HISS_SPORTS_TEST_SETTINGS = "HISS_SPORTS_TEST_SETTINGS"; // XML

    public static final String KEY_PHONE_UUID = "KEY_PHONE_UUID";
    public static final String KEY_MEMBER_ID = "KEY_MEMBER_ID";
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_LOGIN_NAME = "KEY_LOGIN_NAME";
    public static final String KEY_LOGIN_PW = "KEY_LOGIN_PW";
    public static final String UNIVERSITY_ID = "UNIVERSITY_ID";
    public static final String KEY_AUTO_LOGIN = "KEY_AUTO_LOGIN";
    public static final String KEY_FACULTY_CLASS_ID = "KEY_FACULTY_CLASS_ID";
//    public static final String KEY_FIRST_VIEWED_ALBUM_ID = "KEY_FIRST_VIEWED_ALBUM_ID";
    public static final String KEY_LAST_VIEWED_ALBUM_ID = "KEY_LAST_VIEWED_ALBUM_ID";
    public static final String KEY_LAST_PHOTO_ALBUM_ID = "KEY_LAST_PHOTO_ALBUM_ID";
    public static final String KEY_LAST_PHOTO_ALBUM_PAGE = "KEY_LAST_PHOTO_ALBUM_PAGE";
    public static final String KEY_NEW_FRIEND_SIGN = "KEY_NEW_FRIEND_SIGN";
    public static final String KEY_MOMEMNTS_OFFLINE_DATA = "KEY_MOMEMNTS_OFFLINE_DATA";
    public static final String KEY_PHOTO_ALBUM_OFFLINE_DATA = "KEY_PHOTO_ALBUM_OFFLINE_DATA";

    public static final String KEY_UNIVERSITY_ID = "KEY_UNIVERSITY_ID";
    public static final String KEY_TEACHER_NO = "KEY_TEACHER_NO";

    //                        cexam	添加好友是否需要审批，是：1，否：0	默认1
    //                        calbum	是否公开朋友圈，是：1，否：0	该字段可以不传，如果传，默认1
    //                        csearch	是否允许被搜索，是：1，否：0	添加好友，搜索屏蔽作用
    //                        cinfo	是否对陌生人公开资料，1：允许，0：不允许
    public static final String KEY_GLOBLE_CONFIG_CEXAM = "cexam";
    public static final String KEY_GLOBLE_CONFIG_CALBUM = "calbum";
    public static final String KEY_GLOBLE_CONFIG_CSEARCH = "csearch";
    public static final String KEY_GLOBLE_CONFIG_CINFO = "cinfo";


    //	public static final String KEY_STUDENT = "KEY_STUDENT";
    //	public static final String KEY_FRIEND = "KEY_FRIEND";
    //	public static final String KEY_GROUP = "KEY_GROUP";
    //	public static final String KEY_GROUP_MEMBERS = "KEY_GROUP_MEMBERS";

    public static final String LANGUAGE_VALUE_CHINESE = "CN";
    public static final String LANGUAGE_VALUE_ENGLISH = "EN";

    public static String getStringValue(Context con, String xmlName, String keyName) {
        return con.getSharedPreferences(xmlName, 0).getString(keyName, null);
    }

    public static boolean setStringValue(Context con, String xmlName, String keyName, String stringValue) {
        return con.getSharedPreferences(xmlName, 0).edit().putString(keyName, stringValue).commit();
    }

    public static boolean getBooleanValue(Context con, String xmlName, String keyName) {
        return con.getSharedPreferences(xmlName, 0).getBoolean(keyName, false);
    }

    public static boolean setBooleanValue(Context con, String xmlName, String keyName, boolean b) {
        return con.getSharedPreferences(xmlName, 0).edit().putBoolean(keyName, b).commit();
    }

    public static int getIntValue(Context con, String xmlName, String keyName) {
        return con.getSharedPreferences(xmlName, 0).getInt(keyName, 0);
    }

    public static boolean setIntValue(Context con, String xmlName, String keyName, int intValue) {
        return con.getSharedPreferences(xmlName, 0).edit().putInt(keyName, intValue).commit();
    }

    public static boolean contains(Context con, String xmlName, String keyName) {
        return con.getSharedPreferences(xmlName, 0).contains(keyName);
    }

    public static void initProjectXmlPara(Context mContext) {
        try {
            //			if (HissSpManager.contains(mContext, HissSpManager.HISS_BOX_SETTINGS, HissSpManager.KEY_ALERT_MUSIC_NUM)) {
            //				HissSpManager.alertMusicNum = HissSpManager.getIntValue(mContext, HissSpManager.HISS_BOX_SETTINGS, HissSpManager.KEY_ALERT_MUSIC_NUM);
            //			} else {
            //				HissSpManager.setIntValue(mContext, HissSpManager.HISS_BOX_SETTINGS, HissSpManager.KEY_ALERT_MUSIC_NUM, HissSpManager.DEFAULT_ALERT_MUSIC_NUM);
            //				HissSpManager.alertMusicNum = HissSpManager.DEFAULT_ALERT_MUSIC_NUM;
            //			}
        } catch (Exception e) {
            e.printStackTrace();
            new HissFileService(mContext, e);
        }
    }
}
