package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuyanzhe on 2017/3/22.
 */

public class StuInterest implements Parcelable{
    private String id;
    private String studentId;
    private String interest;

    public StuInterest(){

    }

    public StuInterest(String id,String studentId,String interest){
        this.id = id;
        this.studentId = studentId;
        this.interest = interest;
    }

    protected StuInterest(Parcel in) {
        id = in.readString();
        studentId = in.readString();
        interest = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(studentId);
        dest.writeString(interest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StuInterest> CREATOR = new Creator<StuInterest>() {
        @Override
        public StuInterest createFromParcel(Parcel in) {
            return new StuInterest(in);
        }

        @Override
        public StuInterest[] newArray(int size) {
            return new StuInterest[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
