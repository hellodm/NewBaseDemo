package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.map.MapFragment;
import com.hello.app.Fragment.MyFragment;
import com.hello.app.Fragment.MyFragmentCopy;
import com.hello.app.R;
import com.hello.app.Util.Constant;
import com.hello.app.Util.TimeUtil;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MyCacheActivity extends Activity implements MyFragment.OnFragmentInteractionListener,
        MyFragmentCopy.OnFragmentInteractionListener {


    @InjectView(R.id.text_info)
    TextView textInfo;

    Bitmap mBitmap;

    /**
     * 创建声音临时文件
     */
    public static File createTemFile() {

        createFile(obtainSDCard() + Constant.voicePath);

        File temporary = new File(obtainSDCard() + Constant.voicePath);
        try {
            temporary = File
                    .createTempFile(TimeUtil.obtainCurrentTime() + "", Constant.VOICE_suffix,
                            temporary);

            Log.i("临时文件的创建", temporary.getAbsolutePath());

            return temporary;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 返回外部Sd卡路径
     */
    public static String obtainSDCard() {

        return Environment.getExternalStorageDirectory().getPath();


    }

    /**
     * 创建文件
     */
    public static void createFile(String path) {

        File file = new File(path);

        if (!file.exists()) {

            file.mkdirs();

        }


    }

//    /** 保存文件 */
//
//    private void saveBmpToSd(Bitmap bm, String url) {
//        if (bm == null) {
//            Log.w("saveBmpToSd", " trying to save null bitmap");
//            return;
//        }
//        String filename =convertUrlToFileName(url);
//        String dir = getDirectory(filename);
//        File file = new File(dir +"/" + filename);
//        try {
//            file.createNewFile();
//            OutputStream outStream = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//            Log.i("saveBmpToSd", "Image saved tosd");
//        } catch (FileNotFoundException e) {
//            Log.w("saveBmpToSd","FileNotFoundException");
//        } catch (IOException e) {
//            Log.w("saveBmpToSd","IOException");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commca);

        ButterKnife.inject(this);

        textInfo.setText("保存吗");
        SharedPreferences sharedPreferences = getSharedPreferences("cache",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 100; i++) {
            editor.putString("i+你好", i + "你好");
        }

        editor.commit();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);

        trans();

    }

    @OnClick(R.id.btn_send)
    public void sendBroadcast() {
        transs();
    }

    void trans() {

        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();

        MyFragment fragment = new MyFragment();
        transaction.add(R.id.lin_frag, fragment, "MyFragment");
        transaction.commit();

    }

    void transs() {

        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();

//        MapFragment fragment= new MapFragment();
        MyFragmentCopy fragment = new MyFragmentCopy();
        transaction.replace(R.id.lin_frag, fragment, "Fragment");
        transaction.commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
