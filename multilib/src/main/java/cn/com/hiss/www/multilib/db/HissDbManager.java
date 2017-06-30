package cn.com.hiss.www.multilib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;

import cn.com.hiss.www.multilib.db.base.BaseManager;
import cn.com.hiss.www.multilib.utils.CacheData;

/**
 * Created by wuyanzhe on 2017/3/7.
 */

public class HissDbManager {
    public static final String DEFAULT_DATABASE_NAME = "hiss_sport_test.db";
    private static DaoMaster.DevOpenHelper mHelper;
    protected static DaoSession daoSession;

    /**
     * 初始化OpenHelper
     *
     * @param context
     */
    public static void initOpenHelper(@NonNull Context context) {
        mHelper = getOpenHelper(context, DEFAULT_DATABASE_NAME);
        openWritableDb();
        Log.e("HissDb", "initOpenHelper~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * 初始化OpenHelper
     *
     * @param context
     * @param dataBaseName
     */
    public static void initOpenHelper(@NonNull Context context, @NonNull String dataBaseName) {
        mHelper = getOpenHelper(context, dataBaseName);
        openWritableDb();
    }

    /**
     * Query for readable DB
     */
    protected static void openReadableDb() throws SQLiteException {
        daoSession = new DaoMaster(getReadableDatabase()).newSession();
    }

    /**
     * Query for writable DB
     */
    protected static void openWritableDb() throws SQLiteException {
        daoSession = new DaoMaster(getWritableDatabase()).newSession();
    }

    private static SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    private static SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    /**
     * 在applicaiton中初始化DatabaseHelper
     */
    private static DaoMaster.DevOpenHelper getOpenHelper(@NonNull Context context, @Nullable String dataBaseName) {
        closeDbConnections();
        return new DaoMaster.DevOpenHelper(context, dataBaseName, null);
    }

    /**
     * 只关闭helper就好,看源码就知道helper关闭的时候会关闭数据库
     */
    public static void closeDbConnections() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public static DaoSession getDaoSession(Context con){
        if(daoSession == null){
            initOpenHelper(con);
        }
        return daoSession;
    }

//    public boolean dropDatabase() {
//        try {
//            openWritableDb();
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }

    public void insert(Entity entity) {
        long id = -1;
        if (entity instanceof DbGetStudent) {
            DbGetStudentDao beanGetStudentDao = daoSession.getDbGetStudentDao();
            id = beanGetStudentDao.insert((DbGetStudent) entity);
        }
        Log.d("HissDao", "Inserted Entity, ID: " + id);
    }

    public void deleteByKey(Entity entity, long key) {
        if (entity instanceof DbGetStudent) {
            DbGetStudentDao beanGetStudentDao = daoSession.getDbGetStudentDao();
            beanGetStudentDao.deleteByKey(key);
        }
        Log.d("HissDao", "deleteByKey Entity, key: " + key);
    }

    /**
     * 根据对话内容获得群成员信息
     * @param bean  若不存在，返回null;若是单人聊天，返回DbGetFriends；若是群组，返回DbGetChatGroupMembers
     * @return
     */
    public static DbGetChatGroupMembers getMemberByMessage(DbChatMessageBean bean){
        if(TextUtils.isEmpty(bean.getGroupId())){
            DbGetFriendsDao dao =  HissDbManager.getDaoSession(BaseManager.getContext()).getDbGetFriendsDao();
            DbGetFriends friends = dao.queryBuilder().where(DbGetFriendsDao.Properties.LoginUserId.eq(CacheData.getMyData().getMemberId()),DbGetFriendsDao.Properties.FriendId.eq(bean.getUserId())).unique();
            if(friends == null){
                return null;
            }else{
                return getMemberByFriends(friends);
            }
        }else{
            DbGetChatGroupMembersDao dao = HissDbManager.getDaoSession(BaseManager.getContext()).getDbGetChatGroupMembersDao();
            DbGetChatGroupMembers member = dao.queryBuilder().where(DbGetChatGroupMembersDao.Properties.GroupId.eq(bean.getGroupId()),DbGetChatGroupMembersDao.Properties.FriendId.eq(bean.getUserId())).unique();
            return member;
        }
    }

    private static final DbGetChatGroupMembers getMemberByFriends(DbGetFriends friends){
        DbGetChatGroupMembers members = new DbGetChatGroupMembers();
        members.setGroupId("");
        members.setFriendId(friends.getFriendId());
        members.setRealName(friends.getRealName());
        members.setFriendRemark(friends.getFriendRemark());
        members.setUniversityName(friends.getUniversityName());
        members.setSex(friends.getSex());
        members.setAge(friends.getAge());
        members.setClassName(friends.getClassName());
        members.setImgUrl(friends.getImgUrl());
        members.setFirstLetter(friends.getFirstLetter());
        return members;
    }

    public static void deleteAllData(Context con){
        DaoSession daoSession = HissDbManager.getDaoSession(con);
        DbGetStudentDao studentDao = daoSession.getDbGetStudentDao();
        DbGetFriendsDao friendsDao = daoSession.getDbGetFriendsDao();
        DbGetChatGroupsDao groupsDao = daoSession.getDbGetChatGroupsDao();
        DbGetChatGroupMembersDao membersDao = daoSession.getDbGetChatGroupMembersDao();
        studentDao.deleteAll();
        friendsDao.deleteAll();
        groupsDao.deleteAll();
        membersDao.deleteAll();
    }

}
