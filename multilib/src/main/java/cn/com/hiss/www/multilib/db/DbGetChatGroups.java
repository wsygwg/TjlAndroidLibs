package cn.com.hiss.www.multilib.db;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * Created by wuyanzhe on 2017/3/7.
 */
@Entity
public class DbGetChatGroups implements Parcelable {
    @Id
    private Long dbId;
    private String loginUserId;//TODO:登录用户ID,服务器不返回，需要自己添加
    private String groupId;
    private String groupName;
    private String notice;
    private String description;
    private String isDel;
    private String createTime;
    private String picUrl;
    private String adminId;
    @Convert(converter = TagsConverter.class, columnType = String.class)
    private ArrayList<BeanGroupTags> tags;
    @Convert(converter = ChatGroupConfigConverter.class, columnType = String.class)
    private ChatGroupConfig chatGroupConfig;

//    public static DbGetChatGroups getGroupsById(Context con,String loginId,String groupId){
//        DbGetChatGroupsDao dao = HissDbManager.getDaoSession(con).getDbGetChatGroupsDao();
//        DbGetChatGroups dbGetChatGroup = dao.queryBuilder().where(DbGetChatGroupsDao.Properties.LoginUserId.eq(loginId),DbGetChatGroupsDao.Properties.GroupId.eq(groupId)).unique();
//        return dbGetChatGroup;
//    }

    public static List<DbGetChatGroups> getAllGroupsByUserId(Context con,String loginId){
        DbGetChatGroupsDao dao = HissDbManager.getDaoSession(con).getDbGetChatGroupsDao();
        List<DbGetChatGroups> dbGetChatGroupses = dao.queryBuilder().where(DbGetChatGroupsDao.Properties.LoginUserId.eq(loginId)).list();
        return dbGetChatGroupses;
    }

    /**
     * 删除群信息
     *
     * @param groupId
     */
    private static void deleteGroup(Context con,String loginId,String groupId) {
        try {
            DbGetChatGroupsDao dao = HissDbManager.getDaoSession(con).getDbGetChatGroupsDao();
            DbGetChatGroups group = dao.queryBuilder().where(DbGetChatGroupsDao.Properties.LoginUserId.eq(loginId), DbGetChatGroupsDao.Properties.GroupId.eq(groupId)).unique();
            if (group != null) {
                dao.delete(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deletGroupAllMembers(Context con,String groupId) {
        DbGetChatGroupMembersDao dao = HissDbManager.getDaoSession(con).getDbGetChatGroupMembersDao();
        List<DbGetChatGroupMembers> members = dao.queryBuilder().where(DbGetChatGroupMembersDao.Properties.GroupId.eq(groupId)).list();
        if(members != null && members.size() > 0){
            dao.deleteInTx(members);
        }
    }

    public static void deleteOnMember(Context con,String groupId, String memberId) {
        DbGetChatGroupMembersDao dao = HissDbManager.getDaoSession(con).getDbGetChatGroupMembersDao();
        DbGetChatGroupMembers member = dao.queryBuilder().where(DbGetChatGroupMembersDao.Properties.GroupId.eq(groupId), DbGetChatGroupMembersDao.Properties.FriendId.eq(memberId)).unique();
        if (member != null) {
            dao.delete(member);
        }
    }

    /**
     * 删除群信息，并删除DbGetChatGroupMembers中该群所包含的成员信息
     * @param groupId
     */
    public static void deleteGroupInfo(Context con,String loginUserId,String groupId){
        deleteGroup(con,loginUserId,groupId);
        deletGroupAllMembers(con,groupId);
        DbRecentlyUser.deleteRecentLog(con,loginUserId,groupId);
    }

    protected DbGetChatGroups(Parcel in) {
        loginUserId = in.readString();
        groupId = in.readString();
        groupName = in.readString();
        notice = in.readString();
        description = in.readString();
        isDel = in.readString();
        createTime = in.readString();
        picUrl = in.readString();
        adminId = in.readString();
        tags = in.createTypedArrayList(BeanGroupTags.CREATOR);
        chatGroupConfig = in.readParcelable(ChatGroupConfig.class.getClassLoader());
    }

    @Generated(hash = 156416832)
    public DbGetChatGroups(Long dbId, String loginUserId, String groupId, String groupName,
            String notice, String description, String isDel, String createTime, String picUrl,
            String adminId, ArrayList<BeanGroupTags> tags, ChatGroupConfig chatGroupConfig) {
        this.dbId = dbId;
        this.loginUserId = loginUserId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.notice = notice;
        this.description = description;
        this.isDel = isDel;
        this.createTime = createTime;
        this.picUrl = picUrl;
        this.adminId = adminId;
        this.tags = tags;
        this.chatGroupConfig = chatGroupConfig;
    }

    @Generated(hash = 1495014275)
    public DbGetChatGroups() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUserId);
        dest.writeString(groupId);
        dest.writeString(groupName);
        dest.writeString(notice);
        dest.writeString(description);
        dest.writeString(isDel);
        dest.writeString(createTime);
        dest.writeString(picUrl);
        dest.writeString(adminId);
        dest.writeTypedList(tags);
        dest.writeParcelable(chatGroupConfig, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbGetChatGroups> CREATOR = new Creator<DbGetChatGroups>() {
        @Override
        public DbGetChatGroups createFromParcel(Parcel in) {
            return new DbGetChatGroups(in);
        }

        @Override
        public DbGetChatGroups[] newArray(int size) {
            return new DbGetChatGroups[size];
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public ArrayList<BeanGroupTags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<BeanGroupTags> tags) {
        this.tags = tags;
    }

    public ChatGroupConfig getChatGroupConfig() {
        return chatGroupConfig;
    }

    public void setChatGroupConfig(ChatGroupConfig chatGroupConfig) {
        this.chatGroupConfig = chatGroupConfig;
    }
}
