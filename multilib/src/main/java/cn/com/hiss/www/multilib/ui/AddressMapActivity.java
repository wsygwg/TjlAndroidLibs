package cn.com.hiss.www.multilib.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

import cn.com.hiss.www.multilib.HissAmapPosition;
import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.hissviews.HissTitleBar;
import cn.com.hiss.www.multilib.utils.HissAmapUtil;

public class AddressMapActivity extends Activity implements LocationSource, AMapLocationListener, AMap.OnMapClickListener {
    private static final String TAG = AddressMapActivity.class.getSimpleName();
    HissTitleBar titleBar;

    private UiSettings mUiSettings;
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    double defaultLat = 31.490294; //locationY
    double defaultLng = 120.372979;  //locationX
    double latitude;
    double longitude;
    String addressName = "";
    HissAmapPosition hissAmapPosition = new HissAmapPosition();
    Marker marker;
    MarkerOptions otMarkerOptions;
    public static final String POSITION_OBJECT_KEY = "POSITION_OBJECT_KEY";
    public static final String POSITION_PURPOSE_KEY = "POSITION_PURPOSE_KEY";
    public static final int POSITIONING = 0x000100;
    public static final int SHOW_POSITION = 0x000101;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private int purpose = POSITIONING;
    private HissAmapPosition positionToBeShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_map);
        purpose = getIntent().getExtras().getInt(POSITION_PURPOSE_KEY);
        if (purpose == SHOW_POSITION) {
            positionToBeShow = getIntent().getExtras().getParcelable(POSITION_OBJECT_KEY);
        }
        titleBar = (HissTitleBar) findViewById(R.id.id_hiss_title_bar);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        initTitleBar();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void initTitleBar() {
        try {
            titleBar.title.setText("选择位置");
            titleBar.setBackAction(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            if (purpose == POSITIONING) {
                titleBar.optionText.setText("确定");
                titleBar.optionText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            positionResult();
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                titleBar.hideOption();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void positionResult() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(POSITION_OBJECT_KEY, hissAmapPosition);
        intent.putExtras(bundle);
        setResult(POSITIONING, intent);
    }

    @Override
    protected void onDestroy() {
        try {
            Log.e(TAG, "onDestroy " + this.getClass().getSimpleName());
            mapView.onDestroy();
            if (null != mlocationClient) {
                mlocationClient.onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            aMap.setOnMapClickListener(this);
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);            //设置地图默认的指南针是否显示
        mUiSettings.setMyLocationButtonEnabled(true);   // 设置默认定位按钮是否显示
        mUiSettings.setScaleControlsEnabled(true);      //比例尺
        mUiSettings.setZoomControlsEnabled(true);       //设置地图默认的缩放按钮是否显示
        mUiSettings.setMyLocationButtonEnabled(true); // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        if (purpose == POSITIONING) {
            aMap.setLocationSource(this);// 设置定位监听
            aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            // 自定义系统定位小蓝点
            setupLocationStyle();
            go2myPosition();
        } else if (purpose == SHOW_POSITION) {
            showMarkerOnMap(positionToBeShow);
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    private void showMarkerOnMap(LatLng latLng) {
        try {
            aMap.clear();
            initMarkerOptions(latLng);
            aMap.addMarker(otMarkerOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMarkerOnMap(HissAmapPosition position) {
        try {
            //            aMap.clear();
            //            initMarkerOptions(new LatLng(position.getLatLonPoint().getLatitude(), position.getLatLonPoint().getLongitude()));
            //            aMap.addMarker(otMarkerOptions);
            if (position != null) {
                LatLng latLng = new LatLng(position.getLatLonPoint().getLatitude(), position.getLatLonPoint().getLongitude());
                changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 16, 0, 0)));//CameraPosition(LatLng target, float zoom, float tilt, float bearing)
                aMap.clear();
                aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initMarkerOptions(LatLng latLng) {
        otMarkerOptions = new MarkerOptions();
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding));
        otMarkerOptions.position(latLng);
        otMarkerOptions.draggable(false);
    }

    //地图点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        if (purpose == POSITIONING) {
            //点击地图后清理图层插上图标，在将其移动到中心位置
            aMap.clear();
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            initMarkerOptions(latLng);
            aMap.addMarker(otMarkerOptions);
            Log.e("ONCLICK@@@@@@@@@@", "longitude = " + longitude + " ; latitude = " + latitude);
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            hissAmapPosition.setLatLonPoint(latLonPoint);
            HissAmapUtil.getAddress(getApplication(), latLonPoint, listener);
        }
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            LatLng latLng = new LatLng(Double.valueOf(defaultLat), Double.valueOf(defaultLng));
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                latLng = new LatLng(latitude, longitude);
                mlocationClient.stopLocation();
            } else {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mlocationClient.stopLocation();
            }
            showMarkerOnMap(latLng);
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            hissAmapPosition.setLatLonPoint(latLonPoint);
            HissAmapUtil.getAddress(getApplication(), latLonPoint, listener);
        }
    }

    private void go2myPosition() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        go2myPosition();
        //        if (mlocationClient == null) {
        //            mlocationClient = new AMapLocationClient(this);
        //            mLocationOption = new AMapLocationClientOption();
        //            //设置定位监听
        //            mlocationClient.setLocationListener(this);
        //            //设置为高精度定位模式
        //            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //            //设置定位参数
        //            mlocationClient.setLocationOption(mLocationOption);
        //            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        //            // 在定位结束后，在合适的生命周期调用onDestroy()方法
        //            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //            mlocationClient.startLocation();
        //        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 获取当前位置的名称，并做相应操作
     */
    GeocodeSearch.OnGeocodeSearchListener listener = new GeocodeSearch.OnGeocodeSearchListener() {
        /**
         * 逆地理编码回调
         */
        @Override
        public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) {
                    addressName = result.getRegeocodeAddress().getFormatAddress();
                } else {
                    Log.e(TAG, "no address result");
                    addressName = "未知";
                }
            } else {
                Log.e(TAG, "error: code = " + rCode);
                addressName = "未知";
            }
            //在地点上显示具体地点的具体名称
            otMarkerOptions.title("位置").snippet(addressName);
            marker = aMap.addMarker(otMarkerOptions);
            marker.showInfoWindow();
            //设置位置名称
            hissAmapPosition.setAddress(addressName);
            Log.e(TAG, "addressName = " + addressName);
        }

        /**
         * 地理编码查询回调
         */
        @Override
        public void onGeocodeSearched(GeocodeResult result, int rCode) {
        }
    };
}
