package cn.com.hiss.www.multilib.utils;

import android.util.Log;

import cn.com.hiss.www.multilib.common.HissNotificationType;
import cn.com.hiss.www.multilib.db.DbGetChatGroups;
import cn.com.hiss.www.multilib.db.DbGetStudent;
import cn.com.hiss.www.multilib.db.base.BaseManager;

/**
 * Created by wuyanzhe on 2017/3/13.
 */

public class CacheData {
    //toUserID
    private static DbGetStudent myData = null;
    private static DbGetStudent toUser = null;
    private static DbGetChatGroups toGroup = null;
    private static String chatType = "0";  //0为个人，1为群组

    public static void setAllData(String chatType, DbGetStudent myData, DbGetStudent toUser, DbGetChatGroups toGroup){
        CacheData.chatType = chatType;
        CacheData.myData = myData;
        CacheData.toUser = toUser;
        CacheData.toGroup = toGroup;
        if(toUser != null){
            cancelNotification(toUser.getMemberId(),HissNotificationType.MessageP2P);
        }else if(toGroup != null){
            cancelNotification(toGroup.getGroupId(),HissNotificationType.MessageGroup);
        }
    }

    private static void cancelNotification(String chatId,HissNotificationType type){
        String id = HissNotification.getCurrentMessageId();
        Log.e("CacheData","chatId = " + chatId + " ; id = " + id);
        if(!Schecker.isStringNull(id) && id.equals(chatId)){
            HissNotification.cancel(BaseManager.getContext(), type);
        }else{

        }
    }

    public static DbGetStudent getMyData() {
        return myData;
    }

//    public static void setMyData(DbGetStudent myData) {
//        CacheData.myData = myData;
//    }

    public static DbGetStudent getToUser() {
        return toUser;
    }

//    public static void setToUser(DbGetStudent toUser) {
//        chatType = "0";
//        CacheData.toUser = toUser;
//    }

    public static DbGetChatGroups getToGroup() {
        return toGroup;
    }

//    public static void setToGroup(DbGetChatGroups toGroup) {
//        chatType = "1";
//        CacheData.toGroup = toGroup;
//    }

    public static String getChatType() {
        return chatType;
    }

//    public static void setChatType(String chatType) {
//        CacheData.chatType = chatType;
//    }

    public static String getOtherSideId(){
        if(toGroup != null){
            return toGroup.getGroupId();
        }else{
            return toUser.getMemberId();
        }
    }

    public static boolean isGroupChat(){
        if(getToGroup() != null){
            return true;
        }else{
            return false;
        }
    }

}
