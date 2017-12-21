package com.hello.app.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.hello.app.MyView.VoiceSurfaceView;
import com.hello.app.R;
import com.hello.app.Util.FileUtil;
import com.hello.app.Util.VoicePlayUtil;
import com.hello.app.Util.VoiceRecordUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyVoiceActivity extends Activity {


    @InjectView(R.id.valume)
    TextView text;

    String path = "";

    @InjectView(R.id.voice)
    VoiceSurfaceView voice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voice);

        ButterKnife.inject(this);


    }


    @OnClick(R.id.btn_start)

    public void startVoice(Button button) {
        path = FileUtil.createTemFile().getPath();

        VoiceRecordUtil.setVolumeListener(new VoiceRecordUtil.VolumeListener() {
            @Override
            public void getVolume(int volume) {

                text.setText("音量：" + volume * 20);
                voice.setVolume(volume);

            }
        });

        VoiceRecordUtil.startRecord(path);
    }

    @OnClick(R.id.btn_stop)

    public void stopRecord(Button button) {
        VoiceRecordUtil.stopRecord();
    }

    @OnClick(R.id.btn_play)

    public void playVoice(Button button) {

        File file = new File(path.replace("voice", "mp3"));
        Uri uri = Uri.fromFile(file);
        Log.i("文件路径", file.exists() + "");
        VoicePlayUtil.createPlayer(path);

        VoicePlayUtil.startPlay();

    }

    @OnClick(R.id.btn_stopplay)

    public void stopVoice(Button button) {

        VoicePlayUtil.stopPlay();

    }


    @Override
    protected void onDestroy() {
        super.onPause();

        VoiceRecordUtil.releaseRecord();//释放资源
        VoiceRecordUtil.releasePlay();
    }


}
