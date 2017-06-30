package cn.com.hiss.www.multilib.db;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by junliang on 2017/2/24.
 */

public class StuImageConverter implements PropertyConverter<StuImage,String>{

    @Override
    public StuImage convertToEntityProperty(String databaseValue) {
        Gson gson = new Gson();
        StuImage stuImage = gson.fromJson(databaseValue,StuImage.class);
        return stuImage;
    }

    @Override
    public String convertToDatabaseValue(StuImage entityProperty) {
        Gson gson = new Gson();
        String str = gson.toJson(entityProperty);
        return str;
    }
}
