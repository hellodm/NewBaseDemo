package com.hello.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.app.R;
import com.hello.app.Service.VoiceWsService;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/** 该类是声音的实时识别 */
public class VoiceMapActivity extends Activity implements InitListener {

    private final String APP_ID = "=54fd4610";

    private final String SPEECH_LANGUAGE = "zh_cn";

    private final String SPEECH_MANDARIN = "mandarin";

    private final String mEngineType = SpeechConstant.TYPE_CLOUD;

    @InjectView(R.id.progressBar)
    public ProgressBar mProgressVolume;

    @InjectView(R.id.resultText)
    public TextView mResult;

    private SpeechRecognizer mRecognizer;

    private String TAG = "VoiceMapActivity";

    private Toast mToast;

    private int volume;

    private static UserWords mWords;

    static {
        mWords = new UserWords();
//        mWords.putWord("放大");
//        mWords.putWord("缩小");
//        mWords.putWord("往左");
//        mWords.putWord("往右");
//        mWords.putWord("往上");
//        mWords.putWord("往上");
        mWords.putWord("橘子");
        mWords.putWord("香蕉");
        mWords.putWord("班级");
        mWords.putWord("哥哥");
        mWords.putWord("寿司");
        mWords.putWord("绿茶");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        if (mRecognizer != null) {
            mRecognizer.cancel();
            mRecognizer.destroy();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_map);
        ButterKnife.inject(this);
        mProgressVolume.setMax(90);
        initUtility();
        upLoadWords();
    }


    /** 初始化speech */
    private void initUtility() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + APP_ID);
        //创建SpeechRecognizer对象
        mRecognizer = SpeechRecognizer.createRecognizer(this, this);
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
        // 设置语音前端点
        mRecognizer.setParameter(SpeechConstant.VAD_BOS, "500");
        // 设置语音后端点
        mRecognizer.setParameter(SpeechConstant.VAD_EOS, "500");
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
                    Log.i(TAG, speechError.toString());
                } else {
                    Log.i(TAG, "上传成功！");
                }
            }
        });
        if (ret != ErrorCode.SUCCESS) {
            Log.i(TAG, "上传用户词表失败：" + ret);
        }
    }


    /** 实现的本地听写的监听 */
    @Override
    public void onInit(int code) {
        Log.i(TAG, "code = " + code);
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
        mResult.setText("");

        mRecognizer.startListening(new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i) {
                volume = i;
                mProgressVolume.setProgress(i * 3);
            }

            @Override
            public void onBeginOfSpeech() {
                //录音机开启录制
                Log.i(TAG, "开启录制");
            }

            @Override
            public void onEndOfSpeech() {
                //录音自动停止回调--
                // 内部集成了端点检测功能，当用户一定时间内不说话，默认为用户已经不需要再录入语音，会自动调用此回调函数， 并停止当前录音。
                Log.i(TAG, "停止录制");

            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                //返回识别结果,结果可能为空，请增加判空处理 说明：
                //SpeechRecognizer采用边录音边发送的方式，可能会多次返回结果。
                Log.i(TAG, recognizerResult.getResultString());

                String text = JsonParser.parseIatResult(recognizerResult.getResultString());
                mResult.append(text);
                mRecognizer.startListening(this);
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

    /** 开始听写监听 */
    @OnClick(R.id.button_stop_voice)
    public void goStopListening() {

        mProgressVolume.setProgress(0);
        mRecognizer.stopListening();

    }

}