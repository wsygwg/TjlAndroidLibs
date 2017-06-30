package cn.com.hiss.www.multilib.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by junliang on 2017/2/24.
 */

public class BeanGroupTags implements Parcelable{
    private String id;
    private String tagName;
    private boolean checked;

    public BeanGroupTags(){

    }
    protected BeanGroupTags(Parcel in) {
        id = in.readString();
        tagName = in.readString();
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tagName);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanGroupTags> CREATOR = new Creator<BeanGroupTags>() {
        @Override
        public BeanGroupTags createFromParcel(Parcel in) {
            return new BeanGroupTags(in);
        }

        @Override
        public BeanGroupTags[] newArray(int size) {
            return new BeanGroupTags[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
