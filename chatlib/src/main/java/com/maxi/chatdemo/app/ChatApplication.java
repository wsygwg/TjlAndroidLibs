package com.maxi.chatdemo.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mao Jiqing on 2016/9/28.
 */
public class ChatApplication extends Application {

    public static Context con;

    @Override
    public void onCreate() {
        super.onCreate();
        //        BaseManager.initOpenHelper(this);
        con = this;
    }

    public static void setupAppContext(Context con) {
        ChatApplication.con = con;
    }

}
