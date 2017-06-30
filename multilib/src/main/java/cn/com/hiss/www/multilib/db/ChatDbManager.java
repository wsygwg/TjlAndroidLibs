package cn.com.hiss.www.multilib.db;

import org.greenrobot.greendao.AbstractDao;

import cn.com.hiss.www.multilib.db.base.BaseManager;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */

public class ChatDbManager extends BaseManager<DbChatMessageBean,Long> {
    @Override
    public AbstractDao<DbChatMessageBean, Long> getAbstractDao() {
        return daoSession.getDbChatMessageBeanDao();
    }

    private static ChatDbManager chatDbManager;
    private ChatDbManager(){

    }

    public static ChatDbManager getInstance(){
        if(chatDbManager == null){
            synchronized (ChatDbManager.class){
                if(chatDbManager == null){
                    chatDbManager = new ChatDbManager();
                }
            }
        }
        return chatDbManager;
    }

}
