package cn.com.hiss.www.multilib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuyanzhe on 2017/3/30.
 */

public class IosPosition implements Parcelable{
    private double latitude;
    private double longitude;

    public IosPosition(){

    }

    protected IosPosition(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IosPosition> CREATOR = new Creator<IosPosition>() {
        @Override
        public IosPosition createFromParcel(Parcel in) {
            return new IosPosition(in);
        }

        @Override
        public IosPosition[] newArray(int size) {
            return new IosPosition[size];
        }
    };

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
