package com.hello.app.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by dong on 2014/7/23.
 */
public class FileUtil {


    /**
     * 返回外部Sd卡路径
     */
    public static String obtainSDCard() {

        return Environment.getExternalStorageDirectory().getPath();


    }


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
     * 创建文件
     */
    public static void createFile(String path) {

        File file = new File(path);

        if (!file.exists()) {

            file.mkdirs();

        }


    }


}
