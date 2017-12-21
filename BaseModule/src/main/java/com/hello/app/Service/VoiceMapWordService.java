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
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.UserWords;

import java.util.ArrayList;

/**
 * 讯飞语音的词列表指令识别
 */

public class VoiceMapWordService extends Service {


    private final String APP_ID = "=54fd4610";

    private final String SPEECH_LANGUAGE = "zh_cn";

    private final String SPEECH_MANDARIN = "mandarin";

    private final String mEngineType = SpeechConstant.TYPE_CLOUD;

    private SpeechRecognizer mRecognizer;

    private OnVoiceCommandListener mCommandListener;

    private String TAG = "VoiceMapWordService";


    private Toast mToast;

    private int volume;

    private static UserWords mWords;

    static {
        mWords = new UserWords();
        mWords.putWord("放大");
        mWords.putWord("缩小");
        mWords.putWord("往左");
        mWords.putWord("往右");
        mWords.putWord("往上");
        mWords.putWord("往上");
    }


    public VoiceMapWordService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");

//        if (intent.getSerializableExtra("listener") instanceof OnVoiceComomandListener) {
//            mCommandListener = (OnVoiceCommandListener) intent.getParcelableExtra("listener");
//        }
        initUtility();

        upLoadWords();

        startVoice();

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
        super.onDestroy();
        // 退出时释放连接
        if (mRecognizer != null) {
            mRecognizer.cancel();
            mRecognizer.destroy();
        }
    }


    /** 初始化speech */
    private void initUtility() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + APP_ID);
        //创建SpeechRecognizer对象
        mRecognizer = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int code) {
                Log.i("VoiceMapActivity", "code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    showTip("初始化失败,错误码：" + code);
                }
            }
        });
        initParameter();

    }

    /** 初始话Parameter */
    private void initParameter() {
        if (mRecognizer == null) {
            return;
        }
        // 清空参数
        mRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        mRecognizer.setParameter(SpeechConstant.DOMAIN, mRecognizer.getClass().getSimpleName());
        mRecognizer.setParameter(SpeechConstant.LANGUAGE, SPEECH_LANGUAGE);//zh_cn，zh_tw，en_us
        mRecognizer.setParameter(SpeechConstant.ACCENT, SPEECH_MANDARIN);//普通话和粤语
        mRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        // 设置语音前端点
        mRecognizer.setParameter(SpeechConstant.VAD_BOS, "1000");
        // 设置语音后端点
        mRecognizer.setParameter(SpeechConstant.VAD_EOS, "3000");
        // 设置标点符号
        mRecognizer.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径
        mRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
                Environment.getExternalStorageDirectory() + "/iflytek/wavaudio.pcm");

    }

    /** 上传语义词表 */
    private void upLoadWords() {
        int ret = mRecognizer.updateLexicon("userword", mWords.toString(), new LexiconListener() {
            @Override
            public void onLexiconUpdated(String s, SpeechError speechError) {
                if (speechError != null) {
                    Log.d(TAG, speechError.toString());
                } else {
                    Log.d(TAG, "上传成功！");
                }
            }
        });
        if (ret != ErrorCode.SUCCESS) {
            Log.d(TAG, "上传用户词表失败：" + ret);
        }
    }

    /**
     * 开始进行语音识别操作
     */
    private void startVoice() {

        mRecognizer.startListening(new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i) {
                volume = i;
//                Log.i("音量=", volume + "");
            }

            @Override
            public void onBeginOfSpeech() {
                //录音机开启录制
                Log.i("语音开始：", "开启录制");
            }

            @Override
            public void onEndOfSpeech() {
                //录音自动停止回调--
                // 内部集成了端点检测功能，当用户一定时间内不说话，默认为用户已经不需要再录入语音，会自动调用此回调函数， 并停止当前录音。
                Log.i("语音结束：", "停止录制");
                mRecognizer.startListening(this);
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                //返回识别结果,结果可能为空，请增加判空处理 说明：
                //SpeechRecognizer采用边录音边发送的方式，可能会多次返回结果。
                Log.i("结果返回：", recognizerResult.getResultString());

                parseResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {
                //错误回调
                Log.i("错误回调：", speechError.toString());
            }

            @Override
            public void onEvent(int i, int i2, int i3, Bundle bundle) {
                //识别会话事件 扩展用接口，由具体业务进行约定 例如eventType为0显示网络状态，agr1为网络连接值
                Log.i("会话事件：", "");
            }
        });
    }


    /** 解析result */
    private void parseResult(RecognizerResult recognizerResult) {
        String text = JsonParser.parseIatResult(recognizerResult.getResultString());
//
//        if (BaiduActivity.handler == null) {
//            return;
//        }
        int command = -1;
        if (text.contains("放大")) {
            command = 1;
        } else if (text.contains("缩小")) {
            command = 2;
        } else if (text.contains("往左")) {
            command = 3;
        } else if (text.contains("往右")) {
            command = 4;
        }
        if (command != -1) {
//            BaiduActivity.handler.sendEmptyMessage(command);
        }


    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /** 监听回调 */
    public interface OnVoiceCommandListener {

        public void receiveCommand(int command);

        public void receiveError(int error);


    }

}


