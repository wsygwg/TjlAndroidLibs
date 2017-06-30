package cn.com.hiss.www.multilib.utils;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;

/**
 * Created by junliang on 2017/2/10.
 */

public class HissAmapUtil {
    private static final String TAG = HissAmapUtil.class.getSimpleName();

    /**
     * 响应逆地理编码
     */
    public static void getAddress(Context con, final LatLonPoint latLonPoint, GeocodeSearch.OnGeocodeSearchListener listener) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        GeocodeSearch geocoderSearch = new GeocodeSearch(con);
        geocoderSearch.setOnGeocodeSearchListener(listener);
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }
}
