package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by junliang on 2017/2/24.
 */

public class StuImage implements Parcelable{
    String backgroundUrl;
    String id;
    String studentId;
    String headUrl;

    public StuImage(){

    }

    protected StuImage(Parcel in) {
        backgroundUrl = in.readString();
        id = in.readString();
        studentId = in.readString();
        headUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backgroundUrl);
        dest.writeString(id);
        dest.writeString(studentId);
        dest.writeString(headUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StuImage> CREATOR = new Creator<StuImage>() {
        @Override
        public StuImage createFromParcel(Parcel in) {
            return new StuImage(in);
        }

        @Override
        public StuImage[] newArray(int size) {
            return new StuImage[size];
        }
    };

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
