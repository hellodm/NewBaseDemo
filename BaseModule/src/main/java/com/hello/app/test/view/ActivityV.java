package com.hello.app.test.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hello.app.R;
import com.hello.app.test.presenter.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ActivityV extends Activity implements ILoginView {


    @InjectView(R.id.login_button)
    Button mLoginButton;

    @InjectView(R.id.login_response)
    TextView mLoginResponse;


    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_v);
        ButterKnife.inject(this);

        mLoginPresenter = new LoginPresenter(this);
    }


    @OnClick(R.id.login_button)
    public void goLogin() {
        mLoginPresenter.login("12345");
    }


    @Override
    public void loginSuccess(String userId, String userName) {

        mLoginResponse.setText(userId + ":" + userName);


    }


}
