package com.hello.app.FTP;

import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by mozzie on 16-11-13.
 */

public class FTPConnect implements IFTPControl<FTPParam> {

    private final String TAG = "FTPConnect";


    private FTPParam mParam;

    private FTPService mService;

    private FTPClient mClient;

    public FTPConnect(FTPParam ftpParam) {
        init(ftpParam);
    }

    @Override
    public void init(FTPParam ftpParam) {
        mParam = ftpParam;
        mService = new FTPService();

    }

    //连接
    private void connect(FTPClient client) throws Exception {
        if (!mParam.isValid()) {
            Log.i(TAG, "FTPClient mParam =" + false);
            throw new IllegalArgumentException(FTPCode.error_param);
        }
        //链接服务器
        client.connect(mParam.getHostName());
        if (!isConnected(client)) {
            Log.i(TAG, "FTPClient connect =" + false);
            throw new IllegalArgumentException(FTPCode.error_connect);
        }
    }

    //登录
    private void login(FTPClient client) throws Exception {
        boolean isLogin = false;
        //登录
        if (mParam.isNeedLogin()) {
            //登录服务器
            isLogin = client.login(mParam.getUserName(), mParam.getPassword());
        }
        if (!isLogin) {
            Log.i(TAG, "FTPClient login =" + false);
            throw new IllegalArgumentException(FTPCode.error_login);
        }
    }

    //设置config
    private void setConfig(FTPClient client) throws Exception {
        FTPClientConfig config = new FTPClientConfig(client.getSystemType().split(" ")[0]);
        config.setServerLanguageCode("zh");
        client.enterLocalPassiveMode();  // 使用被动模式设为默认
        client.setFileType(FTP.BINARY_FILE_TYPE);   // 二进制文件支持
//      mClient.setFileType(ASCII_FILE_TYPE);
        client.configure(config);
        client.setControlKeepAliveTimeout(60);
    }

    /**
     * 从目录下获取图片urls
     *
     * @param directory /SD/THUMB
     */
    @Override
    public void getFilesFrom(final String directory, @NonNull final ResultCallBack callBack) {
//        if (mClient == null) {
//            callBack.callBack(FTPCode.error_connect, null);
//            return;
//        }
        mService.getImagesFrom(directory, new FTPService.Action2<FTPFile[], List<FileBean>>() {
            @Override
            public FTPFile[] onAction() throws Exception {
                FTPClient mClient = new FTPClient();
                mClient.setControlEncoding("UTF-8");  // 中文转码
                try {
                    connect(mClient); //连接
                    login(mClient);//登录
                    setConfig(mClient);//设置config
                    FTPFile[] files = mClient.listFiles("/SD/THUMB/N20161118112304.jpg");
                    Log.i(TAG, "FTPClient openDirectory =" + files.length);
                    if (files.length > 0) {
//                        return files;
                    } else {
                        throw new IllegalArgumentException(FTPCode.error_directory_empty);
                    }

                    InputStream input = mClient.retrieveFileStream("/SD/THUMB/N20161118112304.jpg");

                    byte[] buffer = new byte[(int) files[0].getSize()];
                    input.read(buffer);
                    input.close();
                    files[0].setName(Base64.encodeToString(buffer, Base64.NO_WRAP));
                    return files;

                } finally {
                    disConnect(mClient);
                }
            }

            @Override
            public void onSuccess(String code, List<FileBean> fileBeans) {
                callBack.callBack(code, fileBeans);

            }

        });
    }

    synchronized public boolean saveFileSync(final String fileNamePath, final String fileName,
            final String downLoadPath) throws Exception {
        boolean isSuccess = false;
        OutputStream out = null;
        FTPClient mClient = new FTPClient();
        mClient.setControlEncoding("UTF-8");  // 中文转码
        try {
            connect(mClient); //连接
            login(mClient);//登录
            setConfig(mClient);//设置config
            // 先判断服务器文件是否存在
            FTPFile[] files = mClient.listFiles(fileNamePath);
            if (files.length == 0) {
                //文件不存在
                throw new IllegalArgumentException(FTPCode.error_file_not_exist);
            }
            //创建本地文件夹
            File mkFile = new File(downLoadPath);
            if (!mkFile.exists()) {
                mkFile.mkdirs();
            }
            String localPath = downLoadPath + System.currentTimeMillis() + ".jpg";
            //创建缓存文件
            File localFile = new File(localPath);
            // 开始准备下载文件
            out = new FileOutputStream(localFile, true);
//                mClient.setRestartOffset(localSize);
            isSuccess = mClient.retrieveFile(fileNamePath, out);
            out.close();
        } finally {
            disConnect(mClient);
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccess;

    }

    /**
     * copy文件
     *
     * @param fileNamePath /SD/THUMB 下载文件的path
     * @param fileName     N20160905131218.jpg 下载后的文件名
     * @param downLoadPath /SD/THUMB/ 下载到手机的地址
     */
    @Override
    public void copyFileToMobile(final String fileNamePath, final String fileName,
            final String downLoadPath,
            @NonNull final ResultCallBack<Boolean> callBack) {
        mService.copyFileToMobile(new FTPService.Action<String>() {
            @Override
            public String onAction() throws Exception {
                boolean isSuccess = false;
                isSuccess = saveFileSync(fileNamePath, fileName, downLoadPath);
                if (isSuccess) {
                    return FTPCode.code_success;
                } else {
                    throw new IllegalArgumentException(FTPCode.error_file_copy);
                }


            }

            @Override
            public void onSuccess(String code) {
                Log.i("onSuccess", "保存=" + code);
                callBack.callBack(code, code.equals(FTPCode.code_success));
            }
        });

    }

    /**
     * 获取ftp inputStream
     *
     * @param filePath /SD/THUMB/N20161027013023.jpg
     */
    public InputStream getFileInputStream(String filePath) {
        InputStream input = null;
        mClient = new FTPClient();
        mClient.setControlEncoding("UTF-8");  // 中文转码
        try {
            long time1 = System.currentTimeMillis();
            System.out.println("\n\n");
            connect(mClient); //连接
            long time2 = System.currentTimeMillis();
            System.out.println("connect=" + (time2 - time1));
            login(mClient);//登录
            long time3 = System.currentTimeMillis();
            System.out.println("login=" + (time3 - time2));
            setConfig(mClient);//设置config
            long time4 = System.currentTimeMillis();
            System.out.println("setConfig=" + (time4 - time3));
//            FTPFile[] files = mClient.listFiles("/SD/THUMB/N20161116101021.jpg");
//            long time5 =System.currentTimeMillis();
//            System.out.println("FTPFile="+(time5-time4));
//            if (files.length == 0) {
//                //文件不存在
//                throw new IllegalArgumentException(FTPCode.error_file_not_exist);
//            }
            input = mClient.retrieveFileStream("/SD/THUMB/N20161116101021.jpg");
            long time6 = System.currentTimeMillis();
            System.out.println("FileStream=" + (time6 - time4) + "\n\n");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
//            disConnect(mClient);
            return input;
        }


    }


    public void getBase64(String filePath, @NonNull final ResultCallBack<String> callBack) {
        mService.get64(new FTPService.Action<String>() {
            @Override
            public String onAction() throws Exception {

                mClient = new FTPClient();
                mClient.setControlEncoding("UTF-8");  // 中文转码
                connect(mClient); //连接
                login(mClient);//登录
                setConfig(mClient);//设置config
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
////                mClient.setRestartOffset(localSize);
//                 mClient.retrieveFile("/SD/THUMB/N20161116101021.jpg",  out);
//                String b = Base64.encodeBase64String(out.toByteArray());
//

                InputStream input = mClient.retrieveFileStream("/SD/THUMB/N20161116185759.jpg");

                byte[] buffer = new byte[1024 * 5];
                input.read(buffer);
                input.close();
                return Base64.encodeToString(buffer, Base64.NO_WRAP);
            }

            @Override
            public void onSuccess(String s) {
                callBack.callBack("0", s);
            }
        });

    }


    /**
     * 关闭FTP服务.
     */
    public void disConnect(FTPClient client) {
        if (client != null && client.isConnected()) {
            try {
                // 退出FTP
                client.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 断开连接
                client.disconnect();
                client = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 获取响应码
    public int getReplay(FTPClient client) {
        return client.getReplyCode();
    }

    //ftp 是否连接
    public boolean isConnected(FTPClient client) {
        return FTPReply.isPositiveCompletion(getReplay(client)) && null != client.getReplyString();
    }

//    //ftp 是否的登录
//    public boolean isLogin() {
//        return getReplay() == 230;
//    }


}
