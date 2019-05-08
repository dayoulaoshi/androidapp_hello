package com.example.new2;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;


public class MainActivity extends Activity {
    private MapView mMapView = null;
    //MapView mapView = new MapView(this);

    private Button Button_getposition;              //设置监听器
    private BaiduMap baiduMap;
    public TextView textView_longitude;             //显示的经度
    public TextView textView_latitude;              //显示的纬度
    public ImageView imageView_bubble;              //气泡图片

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



        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap=mMapView.getMap();
        //setContentView(mapView);


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
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}
