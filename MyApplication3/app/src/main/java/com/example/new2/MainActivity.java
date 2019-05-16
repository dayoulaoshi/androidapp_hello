package com.example.new2;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.OverlayOptions;
import java.util.List;
public class MainActivity extends Activity {
    public MapView mMapView = null;
    public Button Button_getposition;              //设置监听器
    public BaiduMap baiduMap;
    public TextView textView_longitude;             //显示的经度
    public TextView textView_latitude;              //显示的纬度
    public ImageView imageView_bubble;              //气泡图片
    public ImageView suspendbutton_location;
    public TextView textView_now_longitude;             //显示当前经度
    public TextView textView_now_latitude;              //显示当前纬度


    //定位相关声明
    public LocationClient locationClient = null;
    //自定义图标
    BitmapDescriptor mCurrentMarket = null;
    //是否首次定位
    boolean isFirstLoc = true;
    //得到经纬度
    private double longitude;
    private double latitude;
    private MyLocationListener myLitenner = new MyLocationListener();


    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            boolean isLocateFailed = false;//定位是否成功
            //MAP VIEW 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    //此处设置开发者获取到的方向信息，顺时针0-360
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            //设置定位数据
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
                //设置地图中心点以及缩放级别
                baiduMap.animateMapStatus(mapStatusUpdate);

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //获取地图控件引用

        Button_getposition=findViewById(R.id.button1);
        textView_longitude=findViewById(R.id.longitude);
        textView_latitude=findViewById(R.id.latitude);
        imageView_bubble=findViewById(R.id.bubble);
        suspendbutton_location=findViewById(R.id.suspendbutton_location);
        textView_now_longitude=findViewById(R.id.now_longitude);
        textView_now_latitude=findViewById(R.id.now_latitude);


        mMapView = findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();





        //按钮添加点击事件
        Button_getposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button1_Getplace button1_getplace=new Button1_Getplace(baiduMap,textView_longitude,textView_latitude);
                button1_getplace.initListener();
            }
        });

        //添加气泡拖动
        Image_Setontouch image_setontouch=new Image_Setontouch(baiduMap,imageView_bubble,textView_longitude,textView_latitude);
        image_setontouch.setontouchlistener();

        //添加定位按钮点击事件          点击后定位当前位置
        suspendbutton_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SDKInitializer.initialize(getApplicationContext());
                //setContentView(R.layout.activity_main);
                initView();
                initData();
            }
        });
    }

    private void initData() {
        locationClient.start();//开始定位
        //baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为一般地图
        //baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
        //baiduMap.setTrafficEnabled(true);//开启交通图
    }

    private void initView() {
        mMapView = findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getApplicationContext());//实例化LocationClient类
        locationClient.registerLocationListener(myLitenner);//注册监听函数
        this.setLocationOption();//设置定位参数
    }






    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

        super.onDestroy();
        mMapView.onDestroy();
    }

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值是gcj02
        option.setScanSpan(5000);//设置发起定位请求的时间间隔为5000ms
        option.setIsNeedAddress(true);//返回的定位结果饱饭地址信息
        option.setNeedDeviceDirect(true);// 返回的定位信息包含手机的机头方向
        locationClient.setLocOption(option);
    }



}



