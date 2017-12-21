package com.hello.app.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.ThumbnailUtils;

import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.hello.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2014/10/9.
 */
public class MapUtil {


    private static List<LatLng> mLatLngList;

    /** 绘制弧线 */

    public static void drawArc(BaiduMap baiduMap, LatLng start, LatLng middle, LatLng end) {

        ArcOptions arc = new ArcOptions();
        arc.color(Color.argb(200, 255, 0, 255));
        arc.points(start, middle, end);
        arc.width(5);
        baiduMap.addOverlay(arc);
    }

    /** 添加折线的坐标 */
    public static List<LatLng> addLatLng(LatLng latLng) {

        if (mLatLngList == null) {

            mLatLngList = new ArrayList<LatLng>();

        }

        mLatLngList.add(latLng);

        return mLatLngList;

    }

    /** 绘制折线 */

    public static void drawPolyline(BaiduMap baiduMap, List<LatLng> list) {

        PolylineOptions polyLine = new PolylineOptions();
        polyLine.color(Color.argb(200, 255, 0, 255));
        polyLine.width(10);
        polyLine.points(list);
        polyLine.zIndex(0);

        baiduMap.addOverlay(polyLine);
    }

    /** 绘制虚线 */

    public static void drawDashedLine(BaiduMap baiduMap, List<LatLng> list) {

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 1 && i + 1 <= list.size() - 1) {
                drawPolyLineBetween(baiduMap, list.get(i), list.get(i + 1));
            }


        }

    }

    /** 绘制虚线 */

    public static void drawDashedLine(BaiduMap baiduMap, LatLng start, LatLng end) {

        drawDashedLine(baiduMap, parseBetween(start, end));

    }


    /** 绘制两点折线 */

    public static void drawPolyLineBetween(BaiduMap baiduMap, LatLng start, LatLng end) {
        List<LatLng> beList = new ArrayList<LatLng>();
        beList.add(start);
        beList.add(end);

        PolylineOptions polyLine = new PolylineOptions();
        polyLine.color(Color.argb(200, 255, 0, 255));
        polyLine.width(7);
        polyLine.points(beList);
        polyLine.zIndex(0);

        baiduMap.addOverlay(polyLine);
    }

    /**
     * 计算2点之间的虚线坐标点list
     */


    private static List<LatLng> parseBetween(LatLng start, LatLng end) {

        List<LatLng> beList = new ArrayList<LatLng>();

        double distance = DistanceUtil.getDistance(start, end);
        int size;

        if (distance < 300) {
            beList.add(start);
            beList.add(end);
        } else {

            size = (int) (distance / 300);

            double offLat = end.latitude - start.latitude;
            double offLng = end.longitude - start.longitude;

            double singLat = offLat / size;
            double singLng = offLng / size;

            for (int i = 0; i < size; i++) {
                LatLng latng = new LatLng(start.latitude + singLat * i,
                        start.longitude + singLng * i);

                beList.add(latng);

            }


        }

        return beList;

    }


    /**
     * 添加折线的坐标
     */
    public static List<LatLng> getListLatLng() {

        if (mLatLngList == null) {

            mLatLngList = new ArrayList<LatLng>();

        }

        return mLatLngList;

    }


    /** 绘制bitMapOverLay */
    public static void canvasMarker(BaiduMap baidumap, LatLng latLng, int bitmapId, float rotate) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(bitmapId);
        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions();
        option.position(latLng);
        option.icon(bitmap);
        option.draggable(true);
        option.anchor(0.5f, 1.0f);
        option.rotate(rotate);
        option.zIndex(1);

        //在地图上添加Marker，并显示
        baidumap.addOverlay(option);


    }

    /** 绘制一个ground */
    public static void drawGround(BaiduMap baidumap, LatLng southwest, LatLng northeast) {

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(northeast)
                .include(southwest)
                .build();
        //定义Ground显示的图片
        BitmapDescriptor bdGround = BitmapDescriptorFactory
                .fromResource(R.drawable.activity_top_bg);
        //定义Ground覆盖物选项
        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds)
                .image(bdGround)
                .transparency(0.8f);
        //在地图中添加Ground覆盖物
        baidumap.addOverlay(ooGround);


    }

    /** 获取圈子头像的组合 */
    public static Bitmap getGroupBitmap(Context context, int defaultId, int size, Bitmap... heads) {
        Bitmap bitmapDefault = BitmapFactory.decodeResource(context.getResources(), defaultId);
        try {

            Bitmap bitmapResult = null;
            int d_s = size / 2 - 1;//单个圆圈头像的直径

            Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
            Bitmap bacBitmap = Bitmap.createBitmap(size, size, srcConfig);
            Canvas canvas = new Canvas(bacBitmap);
            Paint paintSrc = new Paint();
            paintSrc.setFilterBitmap(false);
            paintSrc.setAntiAlias(true);

            //遍历数组置null为默认

            for (int i = 0; i < heads.length; i++) {

                if (heads[i] == null) {
                    heads[i] = bitmapDefault;
                }
                heads[i] = getSrcBitMap(heads[i], d_s, d_s);

            }

            switch (heads.length) {

                case 0:
                    break;
                case 1:
                    canvas.drawBitmap(heads[0], size / 2 - d_s / 2, size / 2 - d_s / 2, paintSrc);
                    break;
                case 2:
                    canvas.drawBitmap(heads[0], 0, size - d_s, paintSrc);
                    canvas.drawBitmap(heads[1], size - d_s, 0, paintSrc);
                    break;
                case 3:
                    canvas.drawBitmap(heads[0], size / 2 - d_s / 2, 0, paintSrc);
                    canvas.drawBitmap(heads[1], 0, size - d_s, paintSrc);
                    canvas.drawBitmap(heads[2], size - d_s, size - d_s, paintSrc);
                    break;
                case 4:
                    canvas.drawBitmap(heads[0], 0, 0, paintSrc);
                    canvas.drawBitmap(heads[1], size - d_s, 0, paintSrc);
                    canvas.drawBitmap(heads[2], 0, size - d_s, paintSrc);
                    canvas.drawBitmap(heads[3], size - d_s, size - d_s, paintSrc);
                    break;


            }

            bitmapResult = bacBitmap;

            return bitmapResult;
        } catch (OutOfMemoryError e) {
            return null;
        } finally {
            bitmapDefault.recycle();
        }

    }
//
//    /** 进行组合数据bitmap */
//    private static Bitmap goTogether() {
//
//    }


    /**
     * 获取Src圆圈
     */
    private static Bitmap getSrcBitMap(Bitmap bitmap, int w, int h) {

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(w, h, srcConfig);

        Canvas srcCanvas = new Canvas(srcBitmap);
        Paint paintSrc = new Paint();

        RectF srcRectF = new RectF(0, 0, w, h);

        srcCanvas.drawOval(srcRectF, paintSrc);

        paintSrc.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, w, h);
        srcCanvas.drawBitmap(bitmap, 0f, 0f, paintSrc);

        return srcBitmap;
    }

    /**
     * 获取Src圆圈
     */
    private static Bitmap getDstBitMap(Bitmap bitmap, int w, int h) {

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(w, h, srcConfig);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, w, h);
        Canvas srcCanvas = new Canvas(bitmap);
        Paint paintSrc = new Paint();
        RectF srcRectF = new RectF(0, 0, w, h);
        srcCanvas.drawOval(srcRectF, paintSrc);
        paintSrc.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        srcCanvas.drawBitmap(srcBitmap, 0f, 0f, paintSrc);
//        srcBitmap.recycle();
//        srcBitmap = null;
//        paintSrc = null;
//        srcCanvas = null;

        return bitmap;
    }


    //图片灰化处理
    public static Bitmap getGrayBitmap(Bitmap srcBitmap, float scale) {
        Bitmap mGrayBitmap = Bitmap
                .createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mGrayBitmap);
        Paint mPaint = new Paint();

        //创建颜色变换矩阵
        ColorMatrix mColorMatrix = new ColorMatrix();
        //设置灰度影响范围
        mColorMatrix.setSaturation(scale);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
        //设置画笔的颜色过滤矩阵
        mPaint.setColorFilter(mColorFilter);
        //使用处理后的画笔绘制图像
        mCanvas.drawBitmap(srcBitmap, 0, 0, mPaint);

        return mGrayBitmap;
    }


}
