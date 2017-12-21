package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.hello.app.Fragment.YearFragment;
import com.hello.app.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EditActivity extends Activity implements YearFragment.OnFragmentListener {


    @InjectView(R.id.edit)
    EditText edit;

    @InjectView(R.id.button_send)
    Button btn_send;

    @InjectView(R.id.button_add)
    Button btn_add;

    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.search)
    SearchView search;


    SearchManager mManager = null;

    List<String> list_send = new ArrayList<String>();


    YearFragment mFragment;

    FragmentManager mFragmentManager;

    FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);

        mManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        getComponentName();
//
//        mFragment = YearFragment.newInstance("1989", "12");
//        mFragmentManager = this.getFragmentManager();
//        mTransaction = mFragmentManager.beginTransaction();
//        mTransaction.add(R.id.lin_frag, mFragment);
//        mTransaction.commit();

    }

    @OnClick(R.id.button_add)
    public void addFace(Button button) {

        setFace("/smiley_001", edit);  //添加表情
        View layout = LayoutInflater.from(this).inflate(R.layout.item_report_tips, null);
        new BaloonView(this).showUnderView(button, layout);

    }

//    /** 通过名字获取到图片资源 */
//
//    int r = R.drawable.smiley_001;
//
//    /** 第一种方法 */
//    private void getFace() {
//        String html = "<html>"
//                + "<font color=\"red\" size=\"5\">红色的5</font>"
//                + "</html>";
//        String img = "<html>"
//                + "<img src=\""
//                + r
//                + "\" />"
//                + "</html>";
//
//        Html.ImageGetter getter = new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//
//                Drawable d = getResources().getDrawable(Integer.parseInt(source));
//                return d;
//            }
//        };
//
//        edit.append(Html.fromHtml(img, getter, null));
//    }

    @OnClick(R.id.button_send)
    public void sendFace(Button button) {  //显示

        text.setText("");

        sendText(edit.getText().toString());

        list_send.clear();
        edit.setText("");


    }

    /** 验证消息 */
    private boolean verifyMsg(String msg) {

        Pattern pattern = Pattern.compile("^smiley_\\d{3}$");  //你可以改变smiley成你自己的
        Matcher matcher = pattern.matcher(msg);

        return matcher.matches();


    }

    /** 发送send */
    private void sendText(String msg) {

        char[] s = msg.toCharArray();

        for (int i = 0; i < s.length; i++) {

            if ("/".equals(s[i] + "")) {        //遍历到“/”

                if (i + 10 > s.length - 1) {      //判断“/‘后面的位数够不够显示表情的
                    text.append(s[i] + "");
                } else {
                    if (verifyMsg(msg.substring(i + 1, i + 11))) { //判断“/‘后面的10位是不是表情
                        setFace(msg.substring(i, i + 11), text);
                        i = i + 10;
                    } else {
                        text.append(s[i] + "");
                    }
                }


            } else {                   //没有遍历到"/"

                text.append(s[i] + "");
            }

        }


    }

    /** 显示表情 */
    private void setFace(String path, TextView view) {
        try {
            String name = path.substring(1, path.length());
            Field filed = R.drawable.class.getDeclaredField(name);
            int resourceId = filed.getInt(R.drawable.class);
            Drawable drawable = getResources().getDrawable(resourceId);

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 2 / 3,  //调节表情大小
                    drawable.getIntrinsicHeight() * 2 / 3);

            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            SpannableString span_string = new SpannableString(path);
            span_string.setSpan(span, 0, 11,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//因为我的表情是"/smiley_001"
            view.append(span_string);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragment(Uri uri) {

    }

    public class BaloonView extends PopupWindow {

        public BaloonView(Context context) {
            super(context);
            setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setTouchable(true);
            setFocusable(true);
            setOutsideTouchable(true);
            setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        BaloonView.this.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }

        public void showUnderView(View view, View content) {
            setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//            FrameLayout container = new FrameLayout(view.getContext());
//            container.addView(content);
            setContentView(content);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int xoffset = view.getWidth() / 2 - content.getMeasuredWidth() / 2;
            int y = -view.getHeight() - content.getMeasuredHeight();
            showAsDropDown(view, xoffset, y);
        }
    }
}
