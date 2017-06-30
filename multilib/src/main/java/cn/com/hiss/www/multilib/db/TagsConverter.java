package cn.com.hiss.www.multilib.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junliang on 2017/2/24.
 */

public class TagsConverter implements PropertyConverter<ArrayList<BeanGroupTags>, String> {
    @Override
    public ArrayList<BeanGroupTags> convertToEntityProperty(String databaseValue) {
        Gson gson = new Gson();
        ArrayList<BeanGroupTags> tags = gson.fromJson(databaseValue, new TypeToken<ArrayList<BeanGroupTags>>() {
        }.getType());
        return tags;
    }

    @Override
    public String convertToDatabaseValue(ArrayList<BeanGroupTags> entityProperty) {
        Gson gson = new Gson();
        String str = gson.toJson(entityProperty);
        return str;
    }
}
