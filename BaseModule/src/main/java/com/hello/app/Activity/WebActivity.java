package com.hello.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hello.app.FTP.FTPConnect;
import com.hello.app.FTP.FTPParam;
import com.hello.app.R;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebActivity extends Activity {


    @InjectView(R.id.webView)
    public WebView mWebView;

    @InjectView(R.id.progressBar)
    public ProgressBar mProgressBar;


    private String nowUrl;  //当前的url

    private String backUrl; //上一个url

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        initWebView();
        startUrl();
    }

    private void initWebView() {
        mWebView.setInitialScale(200);
//        mWebView.setOverScrollMode(WebView.SCROLLBAR_POSITION_RIGHT);
//        WebSettings settings = mWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);

        mWebView.setVerticalScrollBarEnabled(true);

        final WebSettings setting = mWebView.getSettings();
        setting.setTextZoom(100);
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(false);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        setting.setDomStorageEnabled(true);

        mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "script");
        mWebView.setWebChromeClient(new MyChromeClient());
        mWebView.setWebViewClient(new MyClient());

        System.out.println("currentThread :" + Thread.currentThread().getId());
    }

    private void startUrl() {
//        mWebView.loadUrl("http://www.hejgjs.com/index/kaihu");
        mWebView.loadUrl("file:///android_asset/demo.html");
//        mWebView.loadUrl("file:///android_asset/html5.html");
    }

    /** 重写返回键 */


    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /** 设置url */
    private void setUrl(String nowUrl, String backUrl) {
        this.backUrl = backUrl;
        this.nowUrl = nowUrl;

    }

    /** 执行二次点击Url */
    void processUrl(WebView view, String url) {
        Log.d("processUrl", url);
        Uri requestUri = Uri.parse(url);
        String scheme = requestUri.getScheme();
        if ("appcall".equals(scheme)) {
            //初始appcall
            String functionPath = requestUri.getHost();
        } else {
            view.loadUrl(url);
        }
    }

    /** 自定义的javaScript */
    public class MyJavaScriptInterface {

        MyJavaScriptInterface() {

        }

        @JavascriptInterface
        public void sendOrder(final String order) {

            Toast.makeText(WebActivity.this, "点击指令", Toast.LENGTH_SHORT).show();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String jsonText;
                    jsonText = "{" + "\"name\"" + ":" + "\"" + order + "\"" + "}";
                    mWebView.loadUrl("javascript:wave(" + jsonText + ")");
                }
            });


        }
    }

    class MyChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(ProgressBar.GONE);
            } else {
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }
            mProgressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


    }

    public class MyClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            setUrl(url, nowUrl);
            processUrl(view, url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
//
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            WebResourceResponse res = null;
//
//            if (url.toLowerCase(Locale.getDefault()).endsWith(".jpg")) { // 替换图片
//                System.out.println("load jpg:" + url + Thread.currentThread().getId());
//
////                BitmapDrawable in = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                in.getBitmap().compress(Bitmap.CompressFormat.PNG, 100,
////                        stream);
//                FTPParam param = new FTPParam();
//                param.buildHostName("192.168.15.1");
//                param.buildUserName("root");
//                param.buildPassword("1234");
//                FTPConnect connect = new FTPConnect(param);
//                res = new WebResourceResponse("image/png", "UTF-8", connect.getFileInputStream(""));
//            }
//            return res;
//        }
    }

}
