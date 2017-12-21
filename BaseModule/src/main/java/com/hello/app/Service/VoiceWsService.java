package com.hello.app.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.hello.app.Activity.BaiduActivity;
import com.hello.app.Util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.qq.wx.voice.recognizer.VoiceRecognizerGrammar;
import com.qq.wx.voice.recognizer.VoiceRecognizerListener;
import com.qq.wx.voice.recognizer.VoiceRecognizerResult;
import com.qq.wx.voice.recognizer.VoiceRecordState;

/**
 * 讯飞语音的词列表指令识别
 */
public class VoiceWsService extends Service implements VoiceRecognizerListener {


    private final String APP_KEY = "248b63f1ceca9158ca88516bcb338e82a482ecd802cbca12";


    private int volume;


    public VoiceWsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("VoiceMapService", "onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("VoiceMapService", "onCreate()");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("VoiceMapService", "onStartCommand()");
        initUtility();
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onRebind(Intent intent) {
        Log.i("VoiceMapService", "onRebind()");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("VoiceMapService", "onDestroy()");
        // 退出时释放连接
        VoiceRecognizerGrammar.shareInstance().destroy();
        super.onDestroy();

    }


    /** 初始化speech */
    private void initUtility() {
        //setSilentTime参数单位为微秒：1秒=1000毫秒
        VoiceRecognizerGrammar.shareInstance().setSilentTime(60);
        VoiceRecognizerGrammar.shareInstance().setListener(this);

        if (VoiceRecognizerGrammar.shareInstance().init(this.getApplicationContext(), APP_KEY)
                == 0) {
            startRecognizer();
        } else {
            Toast.makeText(this, "语音识别引擎初始化失败", Toast.LENGTH_SHORT).show();
        }


    }


    private void startRecognizer() {
        String text
                = "放大 | 缩小 | 往左  | 往右 | 往上 | 往下 | 向左 | 向右 | 向上 | 向下 | 冬天 | 春天 | 寿司  | 香蕉 | 橘子 | 烧酒 | 红茶 | 绿茶 | 果汁 | 班级 | 哥哥;";
//        String text = "冬天 | 春天 | 寿司  | 香蕉 | 橘子 | 烧酒 | 红茶 | 绿茶 | 果汁 | 班级 | 哥哥;";
        if (0 != VoiceRecognizerGrammar.shareInstance().start(text, 1)) {
            Toast.makeText(this, "语音识别词表初始化失败", Toast.LENGTH_SHORT).show();
        }

    }


    /** 解析result */
    private void parseResult(VoiceRecognizerResult result) {

        String text = "";
        if (result != null && result.words != null) {
            int wordSize = result.words.size();
            StringBuilder results = new StringBuilder();
            for (int i = 0; i < wordSize; ++i) {
                VoiceRecognizerResult.Word word = (VoiceRecognizerResult.Word) result.words.get(i);
                if (word != null && word.text != null) {
                    results.append(word.text.replace(" ", ""));
                    results.append("\r\n");
                }
            }
            text = results.toString();
        }

//        if (BaiduActivity.handler == null) {
//            return;
//        }
        int command = -1;
        if (text.contains("放大")) {
            command = 1;
        } else if (text.contains("缩小")) {
            command = 2;
        } else if (text.contains("左")) {
            command = 3;
        } else if (text.contains("右")) {
            command = 4;
        } else if (text.contains("上")) {
            command = 5;
        } else if (text.contains("下")) {
            command = 6;
        }
//        if (command != -1) {
//            BaiduActivity.handler.sendEmptyMessage(command);
//        }

        startRecognizer();
    }


    /** 微信语音的回调 */

    @Override
    public void onGetResult(VoiceRecognizerResult result) {
        Log.i("onGetVoiceRecordState结果", result.words.toString() + "");
//        Toast.makeText(BaiduActivity.mContext,result.words.toString(),Toast.LENGTH_SHORT).show();
        parseResult(result);
    }

    @Override
    public void onGetError(int i) {
        //错误回调

    }

    @Override
    public void onVolumeChanged(int i) {
        volume = i;
//   Log.i("音量=", volume + "");

    }

    @Override
    public void onGetVoiceRecordState(VoiceRecordState state) {
        if (state == VoiceRecordState.Start) {
//            Toast.makeText(BaiduActivity.mContext,"语音模式开启",Toast.LENGTH_SHORT).show();
            Log.i("onGetVoiceRecordState=", "=========================Start");
        } else if (state == VoiceRecordState.Complete) {
            Log.i("onGetVoiceRecordState=", "=========================Complete");


        } else if (state == VoiceRecordState.Canceling) {
            Log.i("onGetVoiceRecordState=", "=========================Canceling");

        } else if (state == VoiceRecordState.Canceled) {
            Log.i("onGetVoiceRecordState=", "=========================Canceled");
            startRecognizer();
        }
    }

    /** 监听回调 */
    public interface OnVoiceCommandListener {

        public void receiveCommand(int command);

        public void receiveError(int error);


    }

}


