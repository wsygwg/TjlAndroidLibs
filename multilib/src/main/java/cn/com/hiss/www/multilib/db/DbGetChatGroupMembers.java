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
public class DbGetChatGroupMembers implements Parcelable {
    @Id
    private Long dbId;//数据表主键
    private String groupId;     //TODO:服务器不返回，自己添加
    private String friendId;    //	成员id
    private String realName;    //	成员名字
    private String friendRemark;    //	群内昵称	不用
    private String universityName; //	大学名称
    private String sex; //	性别：1男，2女
    private String age; //	年龄
    private String className; //	班级名称
    private String imgUrl;  //	头像
    private String firstLetter; //	首字母（暂时不需要）	不用

    public DbGetChatGroupMembers() {

    }

    public static DbGetFriends getFriendByMember(DbGetChatGroupMembers member) {
        DbGetFriends friend = new DbGetFriends();
        friend.setFriendId(member.getFriendId());
        friend.setRealName(member.getRealName());
        friend.setFriendRemark(member.getFriendRemark());
        friend.setUniversityName(member.getUniversityName());
        friend.setSex(member.getSex());
        friend.setAge(member.getAge());
        friend.setClassName(member.getClassName());
        friend.setImgUrl(member.getImgUrl());
        friend.setFirstLetter(member.getFirstLetter());
        return friend;
    }

    protected DbGetChatGroupMembers(Parcel in) {
        groupId = in.readString();
        friendId = in.readString();
        realName = in.readString();
        universityName = in.readString();
        friendRemark = in.readString();
        age = in.readString();
        sex = in.readString();
        className = in.readString();
        imgUrl = in.readString();
        firstLetter = in.readString();
    }

    @Generated(hash = 175377786)
    public DbGetChatGroupMembers(Long dbId, String groupId, String friendId, String realName, String friendRemark, String universityName, String sex, String age, String className, String imgUrl, String firstLetter) {
        this.dbId = dbId;
        this.groupId = groupId;
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
        dest.writeString(groupId);
        dest.writeString(friendId);
        dest.writeString(realName);
        dest.writeString(universityName);
        dest.writeString(friendRemark);
        dest.writeString(age);
        dest.writeString(sex);
        dest.writeString(className);
        dest.writeString(imgUrl);
        dest.writeString(firstLetter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbGetChatGroupMembers> CREATOR = new Creator<DbGetChatGroupMembers>() {
        @Override
        public DbGetChatGroupMembers createFromParcel(Parcel in) {
            return new DbGetChatGroupMembers(in);
        }

        @Override
        public DbGetChatGroupMembers[] newArray(int size) {
            return new DbGetChatGroupMembers[size];
        }
    };

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
