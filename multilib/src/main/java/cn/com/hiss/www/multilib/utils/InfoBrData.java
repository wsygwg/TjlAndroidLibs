package cn.com.hiss.www.multilib.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuyanzhe on 2017/4/13.
 */

public class InfoBrData implements Parcelable{
    private String studentId;
    private String groupId;

    public InfoBrData(){

    }

    public InfoBrData(String studentId, String groupId) {
        this.studentId = studentId;
        this.groupId = groupId;
    }

    protected InfoBrData(Parcel in) {
        studentId = in.readString();
        groupId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentId);
        dest.writeString(groupId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InfoBrData> CREATOR = new Creator<InfoBrData>() {
        @Override
        public InfoBrData createFromParcel(Parcel in) {
            return new InfoBrData(in);
        }

        @Override
        public InfoBrData[] newArray(int size) {
            return new InfoBrData[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
