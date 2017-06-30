package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wuyanzhe on 2017/3/10.
 * type:PERSON_REQUEST,GROUP_REQUEST 别人请求加我为好友，请求加我的群。PERSON_REQUEST数据库查找参数：loginUserId,id(对方的ID);GROUP_REQUEST参数：loginUserId,GroupId，groupApplyPersonId
 * PERSON_RESPONSE，GROUP_RESPONSE我请求加别人为好友，请求加入别人的群。PERSON_RESPONSE数据库查找参数：loginUserId,id（对方的群ID）；GROUP_RESPONSE参数：loginUserId,GroupId
 * 在我发出请求的同时，将PERSON_RESPONSE，GROUP_RESPONSE的记录存入数据库，状态为UNSELECTED，直到我接收到他人的回答
 * 在我接到别人发出请求的同时，将PERSON_REQUEST,GROUP_REQUEST的记录存入数据库，状态为UNSELECTED，直到我接受或拒绝该请求
 */
@Entity
public class DbFriendRequestModel implements Parcelable {
    public static final String PERSON_REQUEST = "1";
    public static final String PERSON_RESPONSE = "2";
    public static final String GROUP_REQUEST = "3";
    public static final String GROUP_RESPONSE = "4";

    public static final String UNSELECTED = "10";
    public static final String ACCEPTED = "11";
    public static final String REFUSED = "12";

    @Id
    private Long dbId;
    private String loginUserId; //TODO;登录用户ID，自己添加
    private String id;  //好友ID
    private String approveMessage;  //好友认证信息
    private String type;    //个人，群组的请求及应答
    private String accepted;   //0,未选择；1，同意；2，拒绝
    private String GroupId; //群ID，群功能用
    private String groupApplyPersonId; //请求加入群的人的ID，用于此人的信息搜索
    private long requestTime; //请求时间
    private String dbFriendStr;
    private String dbGroupStr;


    protected DbFriendRequestModel(Parcel in) {
        loginUserId = in.readString();
        id = in.readString();
        approveMessage = in.readString();
        type = in.readString();
        accepted = in.readString();
        GroupId = in.readString();
        groupApplyPersonId = in.readString();
        requestTime = in.readLong();
        dbFriendStr = in.readString();
        dbGroupStr = in.readString();
    }

    @Generated(hash = 489962522)
    public DbFriendRequestModel(Long dbId, String loginUserId, String id, String approveMessage,
            String type, String accepted, String GroupId, String groupApplyPersonId, long requestTime,
            String dbFriendStr, String dbGroupStr) {
        this.dbId = dbId;
        this.loginUserId = loginUserId;
        this.id = id;
        this.approveMessage = approveMessage;
        this.type = type;
        this.accepted = accepted;
        this.GroupId = GroupId;
        this.groupApplyPersonId = groupApplyPersonId;
        this.requestTime = requestTime;
        this.dbFriendStr = dbFriendStr;
        this.dbGroupStr = dbGroupStr;
    }

    @Generated(hash = 539964614)
    public DbFriendRequestModel() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUserId);
        dest.writeString(id);
        dest.writeString(approveMessage);
        dest.writeString(type);
        dest.writeString(accepted);
        dest.writeString(GroupId);
        dest.writeString(groupApplyPersonId);
        dest.writeLong(requestTime);
        dest.writeString(dbFriendStr);
        dest.writeString(dbGroupStr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbFriendRequestModel> CREATOR = new Creator<DbFriendRequestModel>() {
        @Override
        public DbFriendRequestModel createFromParcel(Parcel in) {
            return new DbFriendRequestModel(in);
        }

        @Override
        public DbFriendRequestModel[] newArray(int size) {
            return new DbFriendRequestModel[size];
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

    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupApplyPersonId() {
        return groupApplyPersonId;
    }

    public void setGroupApplyPersonId(String groupApplyPersonId) {
        this.groupApplyPersonId = groupApplyPersonId;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getDbFriendStr() {
        return dbFriendStr;
    }

    public void setDbFriendStr(String dbFriendStr) {
        this.dbFriendStr = dbFriendStr;
    }

    public String getDbGroupStr() {
        return dbGroupStr;
    }

    public void setDbGroupStr(String dbGroupStr) {
        this.dbGroupStr = dbGroupStr;
    }
}
