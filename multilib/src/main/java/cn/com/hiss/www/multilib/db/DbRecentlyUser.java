package cn.com.hiss.www.multilib.db;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Created by wuyanzhe on 2017/3/10.
 */
@Entity
public class DbRecentlyUser implements Parcelable {
    @Id
    private Long dbId;
    private String loginUserId; //TODO:登录用户ID，需要自己添加
    private String id;  //群组或个人ID
    private String type;    //"0" 个人  "1"群组
    private String unreadCount; //消息未读数量
    private String isFriend;    //是否好友:是则为1，否则为0
    private long recentTime;
    private String contentType;
    private String contentData;
    private String dbGetStudentStr; //学生数据字符串
    private String dbGetChatGroupsStr; //群组数据字符串
    private boolean topFlag;

    public DbRecentlyUser clone(){
        Gson gson = new Gson();
        String str = gson.toJson(this);
        DbRecentlyUser newOne = gson.fromJson(str,DbRecentlyUser.class);
        return newOne;
    }

    public static void deleteRecentLog(Context con, String loginId, String id){
        DbRecentlyUserDao dao = HissDbManager.getDaoSession(con).getDbRecentlyUserDao();
        List<DbRecentlyUser> list = dao.queryBuilder().where(DbRecentlyUserDao.Properties.LoginUserId.eq(loginId),DbRecentlyUserDao.Properties.Id.eq(id)).list();
        if(list != null && list.size() > 0){
            dao.deleteInTx(list);
        }
    }

    public static void topLog(Context con, String loginId, DbRecentlyUser log){
        if(log.getDbId() != null){
            topLogUpdate(con,loginId,log);
        }else{
            DbRecentlyUserDao dao = HissDbManager.getDaoSession(con).getDbRecentlyUserDao();
            List<DbRecentlyUser> list = dao.queryBuilder().where(DbRecentlyUserDao.Properties.LoginUserId.eq(loginId)).list();
            if(list != null){
                for(DbRecentlyUser oneLog : list){
                        oneLog.setTopFlag(false);
                }
                dao.updateInTx(list);
            }
            log.setTopFlag(true);
            dao.insert(log);
        }
    }

    private static void topLogUpdate(Context con, String loginId,DbRecentlyUser log){
        DbRecentlyUserDao dao = HissDbManager.getDaoSession(con).getDbRecentlyUserDao();
        List<DbRecentlyUser> list = dao.queryBuilder().where(DbRecentlyUserDao.Properties.LoginUserId.eq(loginId)).list();
        if(list != null){
            for(DbRecentlyUser oneLog : list){
                if(oneLog.getDbId().equals(log.getDbId())){
                    oneLog.setTopFlag(true);
                }else{
                    oneLog.setTopFlag(false);
                }
            }
            dao.updateInTx(list);
        }
    }

    protected DbRecentlyUser(Parcel in) {
        loginUserId = in.readString();
        id = in.readString();
        type = in.readString();
        unreadCount = in.readString();
        isFriend = in.readString();
        recentTime = in.readLong();
        contentType = in.readString();
        contentData = in.readString();
        dbGetStudentStr = in.readString();
        dbGetChatGroupsStr = in.readString();
        topFlag = in.readByte() != 0;
    }

    @Generated(hash = 1323292370)
    public DbRecentlyUser(Long dbId, String loginUserId, String id, String type, String unreadCount, String isFriend, long recentTime, String contentType,
            String contentData, String dbGetStudentStr, String dbGetChatGroupsStr, boolean topFlag) {
        this.dbId = dbId;
        this.loginUserId = loginUserId;
        this.id = id;
        this.type = type;
        this.unreadCount = unreadCount;
        this.isFriend = isFriend;
        this.recentTime = recentTime;
        this.contentType = contentType;
        this.contentData = contentData;
        this.dbGetStudentStr = dbGetStudentStr;
        this.dbGetChatGroupsStr = dbGetChatGroupsStr;
        this.topFlag = topFlag;
    }

    @Generated(hash = 1027296977)
    public DbRecentlyUser() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUserId);
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(unreadCount);
        dest.writeString(isFriend);
        dest.writeLong(recentTime);
        dest.writeString(contentType);
        dest.writeString(contentData);
        dest.writeString(dbGetStudentStr);
        dest.writeString(dbGetChatGroupsStr);
        dest.writeByte((byte) (topFlag ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbRecentlyUser> CREATOR = new Creator<DbRecentlyUser>() {
        @Override
        public DbRecentlyUser createFromParcel(Parcel in) {
            return new DbRecentlyUser(in);
        }

        @Override
        public DbRecentlyUser[] newArray(int size) {
            return new DbRecentlyUser[size];
        }
    };

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public long getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(long recentTime) {
        this.recentTime = recentTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public String getDbGetStudentStr() {
        return dbGetStudentStr;
    }

    public void setDbGetStudentStr(String dbGetStudentStr) {
        this.dbGetStudentStr = dbGetStudentStr;
    }

    public String getDbGetChatGroupsStr() {
        return dbGetChatGroupsStr;
    }

    public void setDbGetChatGroupsStr(String dbGetChatGroupsStr) {
        this.dbGetChatGroupsStr = dbGetChatGroupsStr;
    }

    public boolean isTopFlag() {
        return topFlag;
    }

    public void setTopFlag(boolean topFlag) {
        this.topFlag = topFlag;
    }

    public boolean getTopFlag() {
        return this.topFlag;
    }
}
