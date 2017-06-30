package cn.com.hiss.www.multilib.db.upgrade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import cn.com.hiss.www.multilib.db.DaoMaster;
import cn.com.hiss.www.multilib.db.DbChatMessageBeanDao;
import cn.com.hiss.www.multilib.db.DbFriendRequestModelDao;
import cn.com.hiss.www.multilib.db.DbGetChatGroupMembersDao;
import cn.com.hiss.www.multilib.db.DbGetChatGroupsDao;
import cn.com.hiss.www.multilib.db.DbGetFriendsDao;
import cn.com.hiss.www.multilib.db.DbGetStudentDao;
import cn.com.hiss.www.multilib.db.DbRecentlyUserDao;

import static cn.com.hiss.www.multilib.db.HissDbManager.DEFAULT_DATABASE_NAME;

/**
 * Created by wuyanzhe on 2017/3/10.
 */

public class HissSQLiteOpenHelper extends DaoMaster.OpenHelper {
    public HissSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, DbGetStudentDao.class, DbGetChatGroupsDao.class, DbGetChatGroupMembersDao.class, DbGetFriendsDao.class, DbChatMessageBeanDao.class, DbFriendRequestModelDao.class, DbRecentlyUserDao.class);
    }

    public static void hissDbUpdate(Context con) {
        HissSQLiteOpenHelper helper = new HissSQLiteOpenHelper(con, DEFAULT_DATABASE_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
    }
}
