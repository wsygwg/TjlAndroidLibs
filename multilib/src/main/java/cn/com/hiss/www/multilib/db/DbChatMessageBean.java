package cn.com.hiss.www.multilib.db;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import cn.com.hiss.www.multilib.common.ChatConst;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DbChatMessageBean implements Parcelable {
    @Id
    private Long dbId;
    private String loginUserId;  //TODO:登录用户ID,自己添加
    private String groupId;     //群组ID  单聊则为“”
    private String userId;      //对方 ID
    private String content;     //消息内容
    private String type;           //信息类型
    private String trans;          //"in": 收到的他人信息  "out": 发出信息
    private String createDate;  //创建时间
    private String latitude;    //经度
    private String longitude;   //维度
    private String myId;        //
    @ChatConst.SendState
    private int sendState;      //发送状态

    public DbChatMessageBean() {
    }

    protected DbChatMessageBean(Parcel in) {
        loginUserId = in.readString();
        groupId = in.readString();
        userId = in.readString();
        content = in.readString();
        type = in.readString();
        trans = in.readString();
        createDate = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        myId = in.readString();
        sendState = in.readInt();
    }

    @Generated(hash = 352662371)
    public DbChatMessageBean(Long dbId, String loginUserId, String groupId, String userId,
            String content, String type, String trans, String createDate, String latitude,
            String longitude, String myId, int sendState) {
        this.dbId = dbId;
        this.loginUserId = loginUserId;
        this.groupId = groupId;
        this.userId = userId;
        this.content = content;
        this.type = type;
        this.trans = trans;
        this.createDate = createDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.myId = myId;
        this.sendState = sendState;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUserId);
        dest.writeString(groupId);
        dest.writeString(userId);
        dest.writeString(content);
        dest.writeString(type);
        dest.writeString(trans);
        dest.writeString(createDate);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(myId);
        dest.writeInt(sendState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbChatMessageBean> CREATOR = new Creator<DbChatMessageBean>() {
        @Override
        public DbChatMessageBean createFromParcel(Parcel in) {
            return new DbChatMessageBean(in);
        }

        @Override
        public DbChatMessageBean[] newArray(int size) {
            return new DbChatMessageBean[size];
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }
}
