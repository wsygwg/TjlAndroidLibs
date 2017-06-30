package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuyanzhe on 2017/3/7.
 */

public class ChatGroupConfig implements Parcelable{
    String id;
    String groupId;
    String cmemberAdd;
    String capply;
    String cnickname;
    String cexam;

    public ChatGroupConfig(){

    }

    protected ChatGroupConfig(Parcel in) {
        id = in.readString();
        groupId = in.readString();
        cmemberAdd = in.readString();
        capply = in.readString();
        cnickname = in.readString();
        cexam = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(groupId);
        dest.writeString(cmemberAdd);
        dest.writeString(capply);
        dest.writeString(cnickname);
        dest.writeString(cexam);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatGroupConfig> CREATOR = new Creator<ChatGroupConfig>() {
        @Override
        public ChatGroupConfig createFromParcel(Parcel in) {
            return new ChatGroupConfig(in);
        }

        @Override
        public ChatGroupConfig[] newArray(int size) {
            return new ChatGroupConfig[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCmemberAdd() {
        return cmemberAdd;
    }

    public void setCmemberAdd(String cmemberAdd) {
        this.cmemberAdd = cmemberAdd;
    }

    public String getCapply() {
        return capply;
    }

    public void setCapply(String capply) {
        this.capply = capply;
    }

    public String getCnickname() {
        return cnickname;
    }

    public void setCnickname(String cnickname) {
        this.cnickname = cnickname;
    }

    public String getCexam() {
        return cexam;
    }

    public void setCexam(String cexam) {
        this.cexam = cexam;
    }
}
