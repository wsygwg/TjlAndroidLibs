package cn.com.hiss.www.multilib.db;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by wuyanzhe on 2017/3/7.
 */

public class ChatGroupConfigConverter implements PropertyConverter<ChatGroupConfig,String> {

    @Override
    public ChatGroupConfig convertToEntityProperty(String databaseValue) {
        Gson gson = new Gson();
        ChatGroupConfig dbMemberChatGroupConfig = gson.fromJson(databaseValue,ChatGroupConfig.class);
        return dbMemberChatGroupConfig;
    }

    @Override
    public String convertToDatabaseValue(ChatGroupConfig entityProperty) {
        Gson gson = new Gson();
        String str = gson.toJson(entityProperty);
        return str;
    }
}
