package com.example.new2;

import android.view.MotionEvent;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.model.LatLng;

public class Button1_Getplace {
    private LatLng currentPt;
    public BaiduMap mBaiduMap;
    private TextView textView_longitude;
    private TextView textView_latitude;

    Button1_Getplace(BaiduMap mBaiduMap, TextView textView_longitude,TextView textView_latitude){
        this.mBaiduMap=mBaiduMap;
        this.textView_longitude=textView_longitude;
        this.textView_latitude=textView_latitude;
    }


    public void initListener() {

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            //地图单击事件回调函数
            public void onMapClick(LatLng point) {
                currentPt = point;

                //textView_longitude.setText(String.valueOf(currentPt.longitude));
                //textView_latitude.setText(String.valueOf(currentPt.latitude));
            }
            //地图内 Poi 单击事件回调函数
            //MapPoi:点击地图 Poi 点时，该兴趣点的描述信息
            public boolean onMapPoiClick(MapPoi poi) {
                return false;
            }
        });






    }

}
