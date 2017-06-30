package cn.com.hiss.www.multilib.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.com.hiss.www.multilib.db.DaoMaster;
import cn.com.hiss.www.multilib.db.DaoSession;
import cn.com.hiss.www.multilib.db.DbChatMessageBean;
import cn.com.hiss.www.multilib.db.DbChatMessageBeanDao;
import cn.com.hiss.www.multilib.db.HissDbManager;
import cn.com.hiss.www.multilib.db.interfaces.IDatabase;
import cn.com.hiss.www.multilib.utils.CacheData;

import static cn.com.hiss.www.multilib.db.HissDbManager.DEFAULT_DATABASE_NAME;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */

public abstract class BaseManager<M, K> implements IDatabase<M, K> {
    private static DaoMaster.DevOpenHelper mHelper;
    protected static DaoSession daoSession;
    private static Context context;
    private static final String TAG = BaseManager.class.getSimpleName();

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化OpenHelper(必须要在主模块中运行此方法，以初始化基本参数)
     *
     * @param context
     */
    public static void initOpenHelper(@NonNull Context context) {
        BaseManager.context = context;
        mHelper = getOpenHelper(context, DEFAULT_DATABASE_NAME);
        openWritableDb();
        Log.e("ChatDb", "initOpenHelper~~~~~~~~~~~~~~~~~~~~~");
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

    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean dropDatabase() {
        try {
            openWritableDb();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(@NonNull M m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().insert(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull M m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplace(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NonNull M m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().delete(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        try {
            if (key.toString().isEmpty())
                return false;
            openWritableDb();
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(K... key) {
        try {
            openWritableDb();
            getAbstractDao().deleteByKeyInTx(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<M> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().deleteInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            openWritableDb();
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NonNull M m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().update(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(M... m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<M> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public M selectByPrimaryKey(@NonNull K key) {
        try {
            openReadableDb();
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        openReadableDb();
        return getAbstractDao().loadAll();
    }

    @Override
    public List<M> loadPages(int page, int number) {
        openReadableDb();
        List<M> list = getAbstractDao().queryBuilder().offset(page * number).limit(number).list();
        return list;
    }

    @Override
    public List<DbChatMessageBean> loadPages(int page, int number, String myId, String otherSideId) {
        if (context == null) {
            Log.e(TAG, "context 为空！！！！！！！！！！！！");
            return new ArrayList<DbChatMessageBean>();
        }
        DbChatMessageBeanDao dao = HissDbManager.getDaoSession(BaseManager.context).getDbChatMessageBeanDao();
        if (CacheData.getToGroup() != null) {
            List<DbChatMessageBean> list = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.GroupId.eq(otherSideId)).offset(page * number).limit(number).list();
            return list;
        }else{
            List<DbChatMessageBean> list = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.UserId.eq(otherSideId),DbChatMessageBeanDao.Properties.GroupId.eq("")).offset(page * number).limit(number).list();
            return list;
        }
    }

    @Override
    public long getPages(int number, String myId, String otherSideId) {
        if (context == null) {
            Log.e(TAG, "context 为空！！！！！！！！！！！！");
            return 0;
        }
        DbChatMessageBeanDao dao = HissDbManager.getDaoSession(BaseManager.context).getDbChatMessageBeanDao();
        if (CacheData.getToGroup() != null) {
            //群信息
            long count = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.GroupId.eq(otherSideId)).count();
            long page = count / number;
            if (page > 0 && count % number == 0) {
                return page - 1;
            }
            return page;
        } else {
            //个人信息
            long count = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.UserId.eq(otherSideId),DbChatMessageBeanDao.Properties.GroupId.eq("")).count();
            long page = count / number;
            if (page > 0 && count % number == 0) {
                return page - 1;
            }
            return page;
        }
    }

    /**
     * 删除聊天记录
     * @param myId
     * @param otherSideId
     */
    public void clearLogData(String myId,String otherSideId){
        DbChatMessageBeanDao dao = HissDbManager.getDaoSession(BaseManager.context).getDbChatMessageBeanDao();
        if (CacheData.getToGroup() != null) {
            //群信息
            List<DbChatMessageBean> groupList = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.GroupId.eq(otherSideId)).list();
            dao.deleteInTx(groupList);
        } else {
            //个人信息
            List<DbChatMessageBean> personalList = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.UserId.eq(otherSideId),DbChatMessageBeanDao.Properties.GroupId.eq("")).list();
            dao.deleteInTx(personalList);
        }
    }

    @Override
    public long getPages(int number) {
        long count = getAbstractDao().queryBuilder().count();
        long page = count / number;
        if (page > 0 && count % number == 0) {
            return page - 1;
        }
        return page;
    }

    public long getChatBeansCount(int number, String myId, String otherSideId) {
        if (context == null) {
            Log.e(TAG, "context 为空！！！！！！！！！！！！");
            return 0;
        }
        DbChatMessageBeanDao dao = HissDbManager.getDaoSession(BaseManager.context).getDbChatMessageBeanDao();
        long count = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.MyId.eq(myId), DbChatMessageBeanDao.Properties.UserId.eq(otherSideId)).count();
        return count;
    }

    @Override
    public boolean refresh(@NonNull M m) {
        try {
            if (m == null)
                return false;
            openWritableDb();
            getAbstractDao().refresh(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            openWritableDb();
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {
        }
    }

    @Override
    public boolean insertList(@NonNull List<M> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public boolean insertOrReplaceList(@NonNull List<M> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplaceInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<M> getQueryBuilder() {
        openReadableDb();
        return getAbstractDao().queryBuilder();
    }

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    @Override
    public List<M> queryRaw(String where, String... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    public Query<M> queryRawCreate(String where, Object... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    public Query<M> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }

    /**
     * 获取Dao
     *
     * @return
     */
    public abstract AbstractDao<M, K> getAbstractDao();

}
