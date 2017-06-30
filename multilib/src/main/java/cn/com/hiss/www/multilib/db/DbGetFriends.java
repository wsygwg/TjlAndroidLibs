package cn.com.hiss.www.multilib.db;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wuyanzhe on 2017/3/10.
 */
@Entity
public class DbGetFriends implements Parcelable {
    @Id
    private Long dbId;      //TODO:数据表主键
    private String loginUserId; //TODO:登录用户ID,服务器不返回，需要自己添加
    private boolean checkFlag; //TODO:服务器不返回，用于方便在列表中显示好友是否被选中
    private String friendId;//	好友ID
    private String realName;//	个人姓名
    private String friendRemark;//	好友名备注
    private String universityName;//	大学名
    private String sex;//	性别：1男，2女
    private String age;//	年龄
    private String className;//	班级名
    private String imgUrl;//	个人头像
    private String firstLetter; //首字母

    public DbGetFriends() {

    }

    public static DbGetFriends getFriendByMemberId(Context con, String id) {
        DbGetFriends friend = null;
        DbGetFriendsDao dao = HissDbManager.getDaoSession(con).getDbGetFriendsDao();
        friend = dao.queryBuilder().where(DbGetFriendsDao.Properties.FriendId.eq(id)).unique();
        return friend;
    }

    public static DbGetChatGroupMembers getMemberByFriend(DbGetFriends friend, String grouId) {
        DbGetChatGroupMembers member = new DbGetChatGroupMembers();
        member.setGroupId(grouId);
        member.setFriendId(friend.getFriendId());
        member.setRealName(friend.getRealName());
        member.setFriendRemark(friend.getFriendRemark());
        member.setUniversityName(friend.getUniversityName());
        member.setSex(friend.getSex());
        member.setAge(friend.getAge());
        member.setClassName(friend.getClassName());
        member.setImgUrl(friend.getImgUrl());
        member.setFirstLetter(friend.getFirstLetter());
        return member;
    }

    protected DbGetFriends(Parcel in) {
        loginUserId = in.readString();
        checkFlag = in.readByte() != 0;
        friendId = in.readString();
        realName = in.readString();
        friendRemark = in.readString();
        universityName = in.readString();
        sex = in.readString();
        age = in.readString();
        className = in.readString();
        imgUrl = in.readString();
        firstLetter = in.readString();
    }

    @Generated(hash = 2082148944)
    public DbGetFriends(Long dbId, String loginUserId, boolean checkFlag, String friendId, String realName, String friendRemark, String universityName, String sex, String age, String className, String imgUrl, String firstLetter) {
        this.dbId = dbId;
        this.loginUserId = loginUserId;
        this.checkFlag = checkFlag;
        this.friendId = friendId;
        this.realName = realName;
        this.friendRemark = friendRemark;
        this.universityName = universityName;
        this.sex = sex;
        this.age = age;
        this.className = className;
        this.imgUrl = imgUrl;
        this.firstLetter = firstLetter;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUserId);
        dest.writeByte((byte) (checkFlag ? 1 : 0));
        dest.writeString(friendId);
        dest.writeString(realName);
        dest.writeString(friendRemark);
        dest.writeString(universityName);
        dest.writeString(sex);
        dest.writeString(age);
        dest.writeString(className);
        dest.writeString(imgUrl);
        dest.writeString(firstLetter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbGetFriends> CREATOR = new Creator<DbGetFriends>() {
        @Override
        public DbGetFriends createFromParcel(Parcel in) {
            return new DbGetFriends(in);
        }

        @Override
        public DbGetFriends[] newArray(int size) {
            return new DbGetFriends[size];
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

    public boolean getCheckFlag() {
        return this.checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

}
