package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.hello.app.FTP.FTPCode;
import com.hello.app.FTP.FTPConnect;
import com.hello.app.FTP.FTPParam;
import com.hello.app.FTP.FileBean;
import com.hello.app.FTP.IFTPControl;
import com.hello.app.FTP.ResultCallBack;
import com.hello.app.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class FTPActivity extends Activity {

    public final String TAG = "FTPActivity";

    public String mServer = "192.168.15.1";

    public String userName = "root";

    public String passWord = "1234";

    @InjectView(R.id.text_ftp)
    public TextView mTextView;

    public IFTPControl mControl;

    //保存聊天图片的本地路径
    public static String getChatImageSavePath() {
        try {
            return Environment.getExternalStorageDirectory().getPath() + "/cstonline/kartor/images";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp);
        ButterKnife.inject(this);
        mControl = new FTPConnect(new FTPParam().buildHostName(mServer).buildUserName(userName)
                .buildPassword(passWord));
    }

    private void goGoGO() {
//            mControl.openDirectory("/SD/THUMB/");
//            mControl.openDirectory("/SD/THUMB");
        //ftp://192.168.15.1/SD/THUMB/N20160905131218.jpg
//        mControl.getFilesFrom("/SD/THUMB", new ResultCallBack<List<FileBean>>() {
//            @Override
//            public void callBack(String code,List<FileBean> result) {
//                if(code.equals(FTPCode.code_success)){
//
//                }
//                if (result == null) {
//                    return;
//                }
//                for (FileBean b : result) {
//                    mTextView
//                            .setText(mTextView.getText() + "\n" + b.getName() + "==" + b.getType());
//                }
//            }
//        });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });
        mControl.copyFileToMobile("/SD/THUMB/N20161118162154.jpg", "N20161118162154.jpg",
                getChatImageSavePath(),
                new ResultCallBack<Boolean>() {
                    @Override
                    public void callBack(String code, Boolean result) {
                        mTextView.setText(result + "");
                    }
                });

//
//        mControl.getBase64("", new ResultCallBack<String>() {
//            @Override
//            public void callBack(String code, String result) {
//                mTextView
//                        .setText(mTextView.getText() + "\n" + result );
//            }
//        });

    }

    @OnClick(R.id.button_connect)
    public void goConnect() {
        goGoGO();
    }


}
