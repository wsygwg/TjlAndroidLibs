package cn.com.hiss.www.multilib.db;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by junliang on 2017/2/24.
 */

public class StuInterestConverter implements PropertyConverter<StuInterest,String>{

    @Override
    public StuInterest convertToEntityProperty(String databaseValue) {
        Gson gson = new Gson();
        StuInterest stuInterest = gson.fromJson(databaseValue,StuInterest.class);
        return stuInterest;
    }

    @Override
    public String convertToDatabaseValue(StuInterest entityProperty) {
        Gson gson = new Gson();
        String str = gson.toJson(entityProperty);
        return str;
    }
}
