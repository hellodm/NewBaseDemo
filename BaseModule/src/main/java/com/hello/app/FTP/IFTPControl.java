package com.hello.app.FTP;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * Created by mozzie on 16-11-13.
 */

public interface IFTPControl<Param> {

    void init(Param param);

    void getFilesFrom(String directory, ResultCallBack<List<FileBean>> callBack);

    void copyFileToMobile(String filePath, String fileName, String downLoadPath,
            ResultCallBack<Boolean> callBack);

    void getBase64(String filePath, @NonNull final ResultCallBack<String> callBack);

}
