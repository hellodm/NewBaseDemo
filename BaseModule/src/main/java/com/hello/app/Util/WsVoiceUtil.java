package com.hello.app.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hello.app.Activity.BaiduActivity;
import com.qq.wx.voice.recognizer.VoiceRecognizerGrammar;
import com.qq.wx.voice.recognizer.VoiceRecognizerListener;
import com.qq.wx.voice.recognizer.VoiceRecognizerResult;
import com.qq.wx.voice.recognizer.VoiceRecordState;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2015/3/25
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class WsVoiceUtil implements VoiceRecognizerListener {

    int mRecoInitSucc = 0;

    private final String APP_KEY = "248b63f1ceca9158ca88516bcb338e82a482ecd802cbca12";

    private Context mContext;

    private static WsVoiceUtil mInstance;

    private int volume;

    private VoiceState mState = VoiceState.close;


    private enum VoiceState {

        init, start, ing, close
    }

    private WsVoiceUtil(Context context) {
        mContext = context;
    }

    public static WsVoiceUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WsVoiceUtil(context);
        }
        return mInstance;
    }

    /** 初始化speech */
    private void initUtility() {
        //setSilentTime参数单位为微秒：1秒=1000毫秒
        VoiceRecognizerGrammar.shareInstance().setSilentTime(60);
        VoiceRecognizerGrammar.shareInstance().setListener(this);

        if (VoiceRecognizerGrammar.shareInstance().init(mContext, APP_KEY) == 0) {
            mState = VoiceState.init;
        } else {
            mState = VoiceState.close;
            Toast.makeText(mContext, "语音识别引擎初始化失败", Toast.LENGTH_SHORT).show();
        }


    }

    private int startRecognizer() {
        String text
                = "放大 | 缩小 | 往左  | 往右 | 往上 | 往下 | 向左 | 向右 | 向上 | 向下 | 冬天 | 春天 | 寿司  | 香蕉 | 橘子 | 烧酒 | 红茶 | 绿茶 | 果汁 | 班级 | 哥哥;";
//        String text = "冬天 | 春天 | 寿司  | 香蕉 | 橘子 | 烧酒 | 红茶 | 绿茶 | 果汁 | 班级 | 哥哥;";
        if (0 == VoiceRecognizerGrammar.shareInstance().start(text, 1)) {
            return 0;
        }
        return -1;

    }


    /** 开启后台语音模式 */
    public void startVoice() {

        initUtility();

        if (mState == VoiceState.init) {
            if (0 == startRecognizer()) {

            } else {
                Toast.makeText(mContext, "语音识别开始词表识别失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "语音识别引擎初始化失败", Toast.LENGTH_SHORT).show();
        }

    }

    /** 关闭后台语音模式 */
    public void closeVoice() {

        VoiceRecognizerGrammar.shareInstance().destroy();

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
        if (command != -1) {
//            BaiduActivity.handler.sendEmptyMessage(command);
        }

        startRecognizer();
    }


    /** 微信语音的回调 */

    @Override
    public void onGetResult(VoiceRecognizerResult result) {
        Log.i("onGetVoiceRecordState结果", result.words.toString() + "");

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
