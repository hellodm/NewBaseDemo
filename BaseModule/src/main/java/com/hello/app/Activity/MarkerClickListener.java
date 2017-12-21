package com.hello.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dong on 2014/9/15.
 */
public class MarkerClickListener
        implements BaiduMap.OnMarkerClickListener, InfoWindow.OnInfoWindowClickListener {

    Button textTitle;

    private Context mContext;

    private BaiduMap mMap;

    private MyInfoWindow adapter;

    private MyApplication mApplication;

    public MarkerClickListener(Context context, BaiduMap googleMap) {
        mContext = context;
        mMap = googleMap;
    }

    /**
     * 添加infoWindow
     */
    private void addWindow(Context context, final BaiduMap map, LatLng latLng) {

        mMap = map;
        View view = LayoutInflater.from(context).inflate(R.layout.item_pop, null);

        textTitle = (Button) view.findViewById(R.id.image);
        view.setTag(textTitle);
        if (adapter == null) {

            adapter = new MyInfoWindow(view, latLng, this);

            adapter.view = view;


        }

        map.showInfoWindow(adapter);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        addWindow(mContext, mMap, marker.getPosition());
        return true;
    }

    @Override
    public void onInfoWindowClick() {
        Button button = (Button) adapter.view.getTag();
        button.setText(System.currentTimeMillis() + "");
        mMap.showInfoWindow(adapter);

//        textTitle.setText(System.currentTimeMillis() + "");
    }


    /** 自定义的pop窗口 */
    class MyInfoWindow extends InfoWindow {


        String text;

        View view;


        public MyInfoWindow(View view, LatLng latLng,
                OnInfoWindowClickListener onInfoWindowClickListener) {

            super(view, latLng, 1);
        }

        //        public MyInfoWindow(BitmapDescriptor bitmapDescriptor,
//                LatLng latLng, int i,
//                OnInfoWindowClickListener onInfoWindowClickListener) {
//            super(bitmapDescriptor, latLng, i, onInfoWindowClickListener);
//        }
//
//        public MyInfoWindow(View view, LatLng latLng) {
//            super(view, latLng, 1);
//        }


    }

}
