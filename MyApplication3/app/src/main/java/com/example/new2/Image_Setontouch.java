package com.example.new2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


//给气泡添加监视器

public class Image_Setontouch  {
    private ImageView image;
    private TextView textView_longitude;
    private TextView textView_latitude;
    public BaiduMap mBaiduMap;
    Image_Setontouch(BaiduMap mBaiduMap,ImageView image,TextView textView_longitude,TextView textView_latitude){
        this.image=image;
        this.mBaiduMap=mBaiduMap;
        this.textView_longitude=textView_longitude;
        this.textView_latitude=textView_latitude;
    }

    public void setontouchlistener(){
        image.setOnTouchListener(new View.OnTouchListener() {

            private float startX;
            private float startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    //按下记录原始地址
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getRawX();
                        startY=event.getRawY();
                        //Toast.makeText(MainActivity.this, "按下了", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //移动后的坐标
                        float moveX= event.getRawX();
                        float moveY= event.getRawY();
                        //手指移动距离的大小
                        float move_bigX=moveX-startX;
                        float move_bigY=moveY-startY;
                        //获得图片左边的上边的坐标
                        float left=image.getLeft();
                        float top=image.getTop();
                        //得到移动后的位置
                        left=left+move_bigX;
                        top=top+move_bigY;
                        //宽度和高度
                        float right=left+image.getWidth();
                        float bottom=top+image.getTop();
                        image.layout((int)left,(int)top,(int)right,(int)bottom);
                        startX = moveX;
                        startY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        //获取屏幕坐标
                        float x=event.getX();
                        float y=event.getY();

                        //将屏幕坐标转化为经纬度
                        Point current_screenpt = new Point((int)x,(int)y);
                        LatLng current_maplocation = mBaiduMap.getProjection()
                                .fromScreenLocation(current_screenpt);

                        //显示在textview
                        textView_longitude.setText(String.valueOf(current_maplocation.longitude));
                        textView_latitude.setText(String.valueOf(current_maplocation.latitude));
                        //showdialog();
                        //Toast.makeText(MainActivity.this, "抬起", Toast.LENGTH_SHORT).show();

                        String jing=textView_longitude.getText().toString();
                        String wei=textView_latitude.getText().toString();
                        double value_jin = Double.valueOf(jing.toString());
                        double value_wei = Double.valueOf(wei.toString());




                        break;

                }
                return true;

            }
            });

        }


    }









