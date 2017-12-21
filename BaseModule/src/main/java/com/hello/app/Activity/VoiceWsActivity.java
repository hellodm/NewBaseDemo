package com.hello.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.app.R;
import com.hello.app.Service.VoiceWsService;
import com.hello.app.Util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.qq.wx.voice.recognizer.VoiceRecognizer;
import com.qq.wx.voice.recognizer.VoiceRecognizerGrammar;
import com.qq.wx.voice.recognizer.VoiceRecognizerListener;
import com.qq.wx.voice.recognizer.VoiceRecognizerResult;
import com.qq.wx.voice.recognizer.VoiceRecordState;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/** 该类是声音的实时识别 */
public class VoiceWsActivity extends Activity implements InitListener, VoiceRecognizerListener {

    private final String APP_KEY = "248b63f1ceca9158ca88516bcb338e82a482ecd802cbca12";

    private final String SPEECH_LANGUAGE = "zh_cn";

    private final String SPEECH_MANDARIN = "mandarin";

    private final String mEngineType = SpeechConstant.TYPE_CLOUD;

    @InjectView(R.id.progressBar)
    public ProgressBar mProgressVolume;

    @InjectView(R.id.resultText)
    public TextView mResult;

    private Toast mToast;

    private int volume;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        VoiceRecognizerGrammar.shareInstance().destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_map);
        ButterKnife.inject(this);
        mProgressVolume.setMax(90);
        initUtility();

    }

    int mRecoInitSucc = 0;

    /** 初始化speech */
    private void initUtility() {
        //setSilentTime参数单位为微秒：1秒=1000毫秒
        VoiceRecognizerGrammar.shareInstance().setSilentTime(200);
        VoiceRecognizerGrammar.shareInstance().setListener(this);

        mRecoInitSucc = VoiceRecognizerGrammar.shareInstance().init(this, APP_KEY);
        if (mRecoInitSucc != 0) {
            Toast.makeText(this, "语音识别引擎初始化失败", Toast.LENGTH_SHORT).show();
        }


    }

    /** 初始话Parameter */
    private void initParameter() {

    }


    /** 实现的本地听写的监听 */
    @Override
    public void onInit(int code) {
        Log.i("VoiceMapActivity", "code = " + code);
        if (code != ErrorCode.SUCCESS) {
            showTip("初始化失败,错误码：" + code);
        }
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }


    /** 开始听写监听 */
    @OnClick(R.id.button_start_voice)
    public void goStartListening() {
        startRecognizer();
    }

    /** 开始听写监听 */
    @OnClick(R.id.button_stop_voice)
    public void goStopListening() {
        VoiceRecognizerGrammar.shareInstance().stop();
    }


    private int startRecognizer() {
//        String text = "放大 | 缩小 | 往左  | 往右 | 往上 | 往下 | 放大 | 缩小;";
        String text = "冬天 | 春天 | 寿司  | 香蕉 | 橘子 | 烧酒 | 红茶 | 绿茶 | 果汁 | 班级 | 哥哥;";
        if (0 == VoiceRecognizerGrammar.shareInstance().start(text, 1)) {
            return 0;
        }
        return -1;

    }


    /** 微信语音的回调 */

    @Override
    public void onGetResult(VoiceRecognizerResult result) {
        Log.i("onGetVoiceRecordState结果", result.words + "");
        String mRecognizerResult = "";
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
            mRecognizerResult = results.toString();
        }
        mResult.append(mRecognizerResult);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onGetError(int i) {
        //错误回调

    }

    @Override
    public void onVolumeChanged(int i) {
        volume = i;
//                Log.i("音量=", volume + "");
        mProgressVolume.setProgress(i * 3);

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
            handler.sendEmptyMessage(0);
        }
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startRecognizer();
                    break;
                default:
                    break;
            }
            return false;
        }
    });


}