package com.hello.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.app.R;
import com.hello.app.RxAndroid.OnRepeatClick;
import com.hello.app.RxAndroid.RxReportUtil;
import com.hello.app.RxAndroid.RxView;
import com.hello.app.Util.AppSurvivalManager;
import com.hello.app.Util.OnClickEvent;
import com.hello.app.circularavatar.JoinBitmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RXJavaActivity extends Activity {

    @InjectView(R.id.text_alert)
    public TextView mResponse;

    @InjectView(R.id.imageView0)
    public ImageView imageView0;

    @InjectView(R.id.imageView1)
    public ImageView imageView1;

    @InjectView(R.id.imageView2)
    public ImageView imageView2;

    @InjectView(R.id.imageView3)
    public ImageView imageView3;

    @InjectView(R.id.button_Rx)
    public Button mButton;

    ArrayList<Bitmap> mBmps = new ArrayList<Bitmap>();

    private Bitmap avatar1;

    private Bitmap avatar2;

    private Bitmap avatar3;

    private Bitmap avatar4;

    private Bitmap avatar5;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            while (true) {
            try {

                student.register(new Call() {
                    @Override
                    public void call() {
                        Log.i("mRunnable", "当前时间：" + System.currentTimeMillis() + "Thread=" + Thread
                                .currentThread().getName());
                    }
                });

                mThreads.start();

//                    hello1();
                Log.i("mRunnable",
                        "当前时间：" + System.currentTimeMillis() + "mRunnable=Thread=" + Thread
                                .currentThread().getName());
                Thread.sleep(5000);
                Log.i("app存活时间及时", "当前时间：" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//        }
    };

    Thread mThreads;

    Student student = new Student("", null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        mThreads = new Thread(student);

        ButterKnife.inject(this);
        avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
        avatar4 = BitmapFactory.decodeResource(getResources(), R.drawable.anniu);
        avatar5 = BitmapFactory.decodeResource(getResources(), R.drawable.alipay);
        mBmps.add(avatar1);

//         RxView.clickRepeat(mButton, 500, new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
////                 clickMore();
//             }
//         });

    }

    //
//    @OnRepeatClick(R.id.button_Rx)
//    public void goRepeat(){
//        clickMore();
//    }
    private void goTime() {
        Thread mThread = new Thread(mRunnable);
        mThread.setDaemon(false);
        mThread.start();

        AppSurvivalManager.scheduleTask(1000);

    }

    @OnClick(R.id.button_resume)
    public void goResume() {
        AppSurvivalManager.resumeTask();
    }

    @OnClick(R.id.button_Rx)
    public void goRx() {
        goTime();
        imageView0.setImageBitmap(JoinBitmaps.createBitmap(imageView0.getWidth(),
                imageView0.getHeight(), mBmps));
        mBmps.add(avatar2);
        imageView1.setImageBitmap(JoinBitmaps.createBitmap(imageView1.getWidth(),
                imageView1.getHeight(), mBmps));
        mBmps.add(avatar3);
        imageView2.setImageBitmap(JoinBitmaps.createBitmap(imageView2.getWidth(),
                imageView2.getHeight(), mBmps));
        mBmps.add(avatar4);
        imageView3.setImageBitmap(JoinBitmaps.createBitmap(imageView3.getWidth(),
                imageView3.getHeight(), mBmps));
//        hello("Ben", "George", "mack", "piece", "piece", "tank", "mandi", "joy", "cri", "piece");
//        hello1();
//        flatMap();
//        lift();
//        clickMore();

    }


    public void clickMore() {
        mResponse.setText(mResponse.getText().toString() + "\n" + "click");

//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tonmind.xiangpai");
//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.vyou.vcameraclient");
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setData(Uri.parse("vyoucamclient://"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vyoucamclient://app.ddpai.com"));
//        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("com.xplore.xiangpai://xiangpai.welcome"));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void hello(String... names) {
        Observable<String> observable = Observable.from(names);

        observable.filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.contains("e");
            }
        })
                .distinct()//过滤
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io()) //设置操作在io线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mResponse.setText(mResponse.getText().toString() + "\n" + s);
                    }
                });


    }


    /**
     * ;
     * 同步的Observable事例
     */
    public void hello1() {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {

                    for (int i = 0; i < 12; i++) {
                        Thread.sleep(100);
                        subscriber.onNext(i + "jay");
                    }
                    subscriber.onCompleted();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        })

//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
//                        mResponse.setText(mResponse.getText().toString() + "\n" + "Complete");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(String s) {
//                        mResponse.setText(mResponse.getText().toString() + "\n" + "next" + s);
                        Log.i("onNext", "当前时间：" + System.currentTimeMillis() + "Thread=" + Thread
                                .currentThread().getName());

                    }
                });
    }

    //获取所有student名下课程的名字集合
    private void flatMap() {
        List<Course> courses = new ArrayList<>();
        List<Student> Students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course("Course" + i);
            courses.add(course);
        }
        for (int i = 0; i < 10; i++) {
            Student student = new Student("Student" + i, courses);
            Students.add(student);
        }

        Observable.from(Students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.courses);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io()) //
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course c) {
                        mResponse.setText(mResponse.getText().toString() + "\n" + c.name);
                    }
                });

    }


    private void lift() {
        List<Course> courses = new ArrayList<>();
        List<Student> Students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course("Course" + i);
            courses.add(course);
        }
        for (int i = 0; i < 10; i++) {
            Student student = new Student("Student" + i, courses);
            Students.add(student);
        }
        Observable.from(Students)
                .lift(new Observable.Operator<String, Student>() {
                    @Override
                    public Subscriber<? super Student> call(
                            final Subscriber<? super String> subscriber) {
                        return new Subscriber<Student>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(Student student) {
                                subscriber.onNext(student.name);
                            }
                        };
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mResponse.setText(mResponse.getText().toString() + "\n" + s);
                    }
                });
    }

    /**
     * 结合不同的观察流
     */


    private void zipObservables() {

//         Observable.zip(new Observable.OnSubscribe<Item>() {
//             @Override
//             public void call(Subscriber<? super Item> subscriber) {
//
//             }
//         }, new Observable.OnSubscribe<Day>() {
//             @Override
//             public void call(Subscriber<? super Day> subscriber) {
//
//             }
//         }, new Func2<Item, Day, Object>() {
//             @Override
//             public Object call(Item item, Day day) {
//                 return null;
//             }
//         });

    }

    class Student implements Runnable {

        public String name;

        public List<Course> courses;

        Call mCall;

        Student(String name, List<Course> courses) {
            this.name = name;
            this.courses = courses;
        }

        void register(Call call) {
            mCall = call;
        }


        @Override
        public void run() {
            try {
                Log.i("mRunnable",
                        "当前时间：" + System.currentTimeMillis() + "Thread=" + Thread.currentThread()
                                .getName());

                Thread.sleep(100);
                mCall.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    interface Call {

        void call();
    }

    class Course {

        public String name;

        Course(String name) {
            this.name = name;
        }
    }

//    private Observable getObservable() {
//
//    }

    public class Item {

        String name;

        String phone;
    }

    public class Day {

        String name;

        String phone;
    }

}
