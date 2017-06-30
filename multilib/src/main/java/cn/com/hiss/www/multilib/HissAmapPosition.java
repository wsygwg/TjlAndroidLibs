package cn.com.hiss.www.multilib;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.LatLonPoint;
import com.google.gson.Gson;


/**
 * Created by junliang on 2017/2/10.
 */

public class HissAmapPosition implements Parcelable {
    String address;
    LatLonPoint latLonPoint;

    public static String toImStr(HissAmapPosition point) {
        IosPosition position = new IosPosition();
        position.setLatitude(point.getLatLonPoint().getLatitude());
        position.setLongitude(point.getLatLonPoint().getLongitude());
        Gson gson = new Gson();
        String iosPosStr = gson.toJson(position);
        return iosPosStr;
    }

    /**
     *
     * @param pointStr 格式：{"longitude" : 120.3729008568097,"latitude" : 31.49033864166615}
     * @return
     */
    public static HissAmapPosition fromImStr(String pointStr) {
        Gson gson = new Gson();
        IosPosition position = gson.fromJson(pointStr,IosPosition.class);
        HissAmapPosition point = new HissAmapPosition();
        point.setLatLonPoint(new LatLonPoint(position.getLatitude(), position.getLongitude()));
        return point;
    }

    public HissAmapPosition() {

    }

    public HissAmapPosition(String address, LatLonPoint latLonPoint) {
        this.address = address;
        this.latLonPoint = latLonPoint;
    }


    protected HissAmapPosition(Parcel in) {
        address = in.readString();
        latLonPoint = in.readParcelable(LatLonPoint.class.getClassLoader());
    }

    public static final Creator<HissAmapPosition> CREATOR = new Creator<HissAmapPosition>() {
        @Override
        public HissAmapPosition createFromParcel(Parcel in) {
            return new HissAmapPosition(in);
        }

        @Override
        public HissAmapPosition[] newArray(int size) {
            return new HissAmapPosition[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeParcelable(latLonPoint, flags);
    }
}
