package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.hello.app.R;

import butterknife.ButterKnife;

public class BaiduActivity extends Activity {
//
//    public static Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int command = msg.what;
//            switch (command) {
//
//                case 1:
//
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.zoomIn());
//                    break;
//                case 2:
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.zoomOut());
//                    break;
//                case 3:
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.scrollBy(-400, 0));
//                    break;
//                case 4:
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.scrollBy(400, 0));
//                    break;
//                case 5:
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.scrollBy(0, -400));
//                    break;
//                case 6:
//                    baidumap.animateMapStatus(MapStatusUpdateFactory.scrollBy(0, 400));
//                    break;
//
//                case 0:
//                    Toast.makeText(mContext,"语音模式开启",Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//
//    };
//
//    private static BaiduMap baidumap;
//
//    public  static Context mContext;
//
//    private LatLng mLatLng_ = new LatLng(26.123443, 106.000000);//重新定位
//
//    private static int scaleZoom = 12;
//
//    /** 百度地图的 */
//    @InjectView(R.id.bmapView)
//    MapView mapView;
//
//    @InjectView(R.id.btn_voice)
//    Button buttonVoice;
//
//    LatLng sLatLng;//初始化定位
//
//    LatLng mLatLng;//中间点
//
//    LatLng rLatLng;//重新定位
//
//    Button button;
//
//    MyRouteOverLay overlay;
//
//    MapStatusUpdate update;
//
//    private MyOnCommandListener mListener;
//
//    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu);
        ButterKnife.inject(this);
       /* mContext = this;
        init();
        baidumap.setOnMapStatusChangeListener(this);
        baidumap.setOnMarkerClickListener(new MarkerClickListener(this, baidumap));
        View view = LayoutInflater.from(this).inflate(R.layout.item_pop, null);

        button = (Button) view.findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText(System.currentTimeMillis() + "");
            }
        });

        MapViewLayoutParams layoutParams = new MapViewLayoutParams.Builder().layoutMode(
                MapViewLayoutParams.ELayoutMode.mapMode).width(view.getWidth())
                .height(view.getHeight()).position(mLatLng_)
                .build();
*/
        //添加OpenGL的绘制

    }

//    private void init() {
//
//        baidumap = mapView.getMap();
//
//        baidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//
//        baidumap.setMaxAndMinZoomLevel(10, 19);
//
//        //定义Maker坐标点
//        sLatLng = new LatLng(39.963175, 116.400244);
//
//        mLatLng = new LatLng(39.973175, 116.400244);
//
//        MapUtil.canvasMarker(baidumap, sLatLng, R.drawable.icon_openmap_mark, 0);
//
//        setCircle(sLatLng);
//
//        baidumap.setOnMarkerDragListener(this);
//
//        baidumap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(sLatLng, scaleZoom));
//
//    }
//
//    /** 设置圆圈overLay */
//    private void setCircle(LatLng point) {
//
//        CircleOptions option = new CircleOptions();
//
//        option.center(point);
//        option.visible(true);
//        option.radius(200);
//        option.visible(true);
//        option.stroke(new Stroke(2, Color.argb(100, 65, 105, 255)));
//        option.fillColor(Color.argb(50, 65, 105, 255));
//
//        baidumap.addOverlay(option);
//
//
//    }
//
//    /** 绘制驾驶路径 */
//    private void canvasRoute(DrivingRouteLine line) {
//
//        if (overlay == null) {
//            overlay = new MyRouteOverLay(baidumap);
//        }
//        overlay.removeFromMap();
//        overlay.setData(line);
//        overlay.addToMap();
//        overlay.zoomToSpan();
//
////        canvasMarker(rLatLng, R.drawable.map_my_car_icon, -60.0f);
//
//    }
//
//    /** 获取路线信息 */
//    private void startSearch() {
//
//        //*配置路线规划option*/
//        DrivingRoutePlanOption option = new DrivingRoutePlanOption();
//        option.from(PlanNode.withLocation(sLatLng));
//        option.to(PlanNode.withLocation(rLatLng));
//        option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);
//
//        //**开始进行路线search*/
//        RoutePlanSearch search = RoutePlanSearch.newInstance();
//        search.setOnGetRoutePlanResultListener(this);
//        search.drivingSearch(option);
//    }
//
//    @OnClick(R.id.btn_voice)
//    public void goVoice() {
////        if (buttonVoice.getText().toString().contains("关闭")) {
////            buttonVoice.setText("语音");
////            WsVoiceUtil.getInstance(this).closeVoice();
////        } else {
////            /**开启语音服务*/
////            buttonVoice.setText("关闭");
////            WsVoiceUtil.getInstance(this).startVoice();
////        }
//
//        intentService = new Intent(this, VoiceWsService.class);
//        this.startService(intentService);
//
//
//    }
//
//    @OnClick(R.id.btn_location)
//    public void goLocation() {
//
//        baidumap.clear();
//
//        MapUtil.getListLatLng().clear();
//        MapUtil.getListLatLng().add(sLatLng);
//        MapUtil.getListLatLng().add(mLatLng);
//        MapUtil.getListLatLng().add(rLatLng);
//
////        MapUtil.canvasMarker(baidumap, rLatLng, R.drawable.map_my_car_icon, -60f);
////        MapUtil.canvasMarker(baidumap, sLatLng, R.drawable.map_my_car_icon, 0);
//
////        MapUtil.drawPolyline(baidumap, MapUtil.getListLatLng());
////        MapUtil.drawGround(baidumap, rLatLng, sLatLng);
//
////        MapUtil.drawDashedLine(baidumap, getListLatng());
//        MapUtil.drawDashedLine(baidumap, rLatLng, sLatLng);
//
////        MapUtil.drawArc(baidumap, sLatLng, mLatLng, rLatLng);
//
////        setPosition(sLatLng);
//
////        startSearch();
//
//    }
//
//    /** 创建latng */
//    private List<LatLng> getListLatng() {
//        MapUtil.getListLatLng().clear();
//
//        for (int i = 0; i < 100; i++) {
//
//            //定义Maker坐标点
//            LatLng sLatLng = new LatLng(39.963175 + 0.003 * i, 116.400244 + 0.003 * i);
//
//            MapUtil.getListLatLng().add(sLatLng);
//        }
//
//        return MapUtil.getListLatLng();
//
//    }
//
//    /**
//     * 定位移动
//     */
//    public void setPosition(LatLng latLng) {
//
//        update = MapStatusUpdateFactory.newLatLngZoom(latLng, 14);
//
//        baidumap.animateMapStatus(update, 200);
//
//    }
//
//    /** 获取屏幕在中心坐标 */
//    private LatLng getCenterLatLng() {
//        Projection projection = baidumap.getProjection();
//        Point p = new Point();
//        p.set(mapView.getWidth() / 2, mapView.getHeight() / 2);
//        rLatLng = projection.fromScreenLocation(p);
//
//        return rLatLng;
//    }
//
//    /** 开始反向地理查询 */
//    private void startReverseGeoCode(LatLng latLng) {
//        GeoCoder geoCoder = GeoCoder.newInstance();
//        ReverseGeoCodeOption option = new ReverseGeoCodeOption();
//        option.location(latLng);
//        geoCoder.setOnGetGeoCodeResultListener(this);
//        geoCoder.reverseGeoCode(option);
//
//
//    }
//
//    /** TODO 地图状态改变监听 =========================================================== */
//    @Override
//    public void onMapStatusChangeStart(MapStatus mapStatus) {
//    }
//
//    @Override
//    public void onMapStatusChange(MapStatus mapStatus) {
//    }
//
//    @Override
//    public void onMapStatusChangeFinish(MapStatus mapStatus) {
//
//        startReverseGeoCode(getCenterLatLng());
//    }
//
//    /** TODO 正向地理查询 =============================================================== */
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//    }
//
//    /** 反向地理查询 */
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//
//        if (reverseGeoCodeResult != null) {
//
//            Toast.makeText(this, reverseGeoCodeResult.getAddress() + ":", Toast.LENGTH_SHORT)
//                    .show();
//        }
//
//    }
//
//    /** TODO marker拖动监听=============================================================== */
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//
//        if (marker != null) {
//            startReverseGeoCode(marker.getPosition());
//
//        }
//
//
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//
//    }
//
//    /** TODO 获取路线回调监听 */
//    @Override
//    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
//
//    }
//
//    @Override
//    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
//
//    }
//
//    @Override
//    public void onGetDrivingRouteResult(DrivingRouteResult result) {
//
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            return;
//        }
//        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//
//            return;
//        }
//        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//
//            canvasRoute(result.getRouteLines().get(0));
//        }
//
//
//    }
//
//    @Override
//    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        stopService(intentService);
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    public class MyOnCommandListener implements OnVoiceCommandListener, Serializable {
//
//        @Override
//        public void receiveCommand(int command) {
//            Log.i("receiveCommand", command + "");
//
//            switch (command) {
//
//                case 1:
//
//                    if (scaleZoom < 19) {
//                        MapStatusUpdateFactory.newLatLngZoom(getCenterLatLng(), scaleZoom + 1);
//                    }
//
//                    break;
//                case 2:
//                    if (scaleZoom > 10) {
//                        MapStatusUpdateFactory.newLatLngZoom(getCenterLatLng(), scaleZoom - 1);
//                    }
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//            }
//
//        }
//
//        @Override
//        public void receiveError(int error) {
//            Log.i("receiveError", error + "");
//        }
//
//
//    }
//
//    /** TODO 自定义的驾驶路线overlay */
//
//    class MyRouteOverLay extends DrivingRouteOverlay {
//
//        public MyRouteOverLay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
//                    .fromResource(R.drawable.icon_openmap_mark);
//            return bitmapDescriptor;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
//                    .fromResource(R.drawable.map_my_car_icon);
//            return bitmapDescriptor;
//        }
//
//        @Override
//        public boolean onRouteNodeClick(int i) {
//            return super.onRouteNodeClick(i);
//        }
//
//
//    }


}
