//package com.hello.app.opencv;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.hello.app.R;
//
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgproc.Imgproc;
//
//import java.util.List;
//
///**
// * Created by Lenovo on 2017/2/24.
// */
//
//public class BasicActivity extends Activity implements View.OnTouchListener, CameraBridgeViewBase.CvCameraViewListener2 {
//    private static final String  TAG              = "OCVSample::Activity";
//
//    private boolean              mIsColorSelected = false;
//    private boolean mLock = false;
//    private Mat mRgba;
//    private Scalar mBlobColorRgba;
//    private Scalar               mBlobColorHsv;
//    private ColorBlobDetector    mDetector;
//    private Mat                  mSpectrum;
//    private Size SPECTRUM_SIZE;
//    private Scalar               CONTOUR_COLOR;
//
//    private CameraBridgeViewBase mOpenCvCameraView;
//    private Button button_lock;
//    private Button button_default_green;
//    private Button button_default_yellow;
//    private Button button_default_hole;
//    private TextView text_hsv;
//
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS:
//                {
//                    Log.i(TAG, "OpenCV loaded successfully");
//                    mOpenCvCameraView.enableView();
//                    mOpenCvCameraView.setOnTouchListener(BasicActivity.this);
//                } break;
//                default:
//                {
//                    super.onManagerConnected(status);
//                } break;
//            }
//        }
//    };
//
//    public BasicActivity() {
//        Log.i(TAG, "Instantiated new " + this.getClass());
//    }
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Log.i(TAG, "called onCreate");
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        setContentView(R.layout.basic_layout);
//
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
//        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setCvCameraViewListener(this);
//
//        text_hsv = (TextView) findViewById(R.id.text_hsv);
//
//        button_lock = (Button) findViewById(R.id.button_lock);
//        button_lock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mLock == false) {
//                    mLock = true;
//                    Toast.makeText(BasicActivity.this,
//                            "HSV: (" + (int) mBlobColorHsv.val[0] + ", " + (int) mBlobColorHsv.val[1] +
//                                    ", " + (int) mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")",
//                            Toast.LENGTH_SHORT).show();
//                    button_lock.setText("UNLOCK");
//                } else {
//                    mLock = false;
//                    button_lock.setText("LOCK");
//                }
//            }
//        });
//
//        button_default_green = (Button) findViewById(R.id.button_default_green);
//        button_default_green.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDetector.setHsvColor(mBlobColorHsv,"GREEN");
//                Log.i("kekkekekke","DEFAULT GREEN IS SET, HSV (73,132,77)");
//                mBlobColorHsv.val[0] = 73;
//                mBlobColorHsv.val[1] = 132;
//                mBlobColorHsv.val[2] = 77;
//                text_hsv.setText("HSV: (" + (int) mBlobColorHsv.val[0] + ", " + (int) mBlobColorHsv.val[1] +
//                        ", " + (int) mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")");
//                Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
//                mIsColorSelected = true;
//                Toast.makeText(BasicActivity.this,
//                        "DEFAULT GREEN IS SET", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        button_default_yellow = (Button) findViewById(R.id.button_default_yellow);
//        button_default_yellow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDetector.setHsvColor(mBlobColorHsv,"YELLOW");
//                Log.i("kekkekekke","DEFAULT YELLOW IS SET, HSV (37,228,127)");
//                mBlobColorHsv.val[0] = 37;
//                mBlobColorHsv.val[1] = 228;
//                mBlobColorHsv.val[2] = 127;
//                text_hsv.setText("HSV: (" + (int) mBlobColorHsv.val[0] + ", " + (int) mBlobColorHsv.val[1] +
//                        ", " + (int) mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")");
//                Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
//                mIsColorSelected = true;
//                Toast.makeText(BasicActivity.this,
//                        "DEFAULT YELLOW IS SET", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        button_default_hole = (Button) findViewById(R.id.button_default_hole);
//        button_default_hole.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDetector.setHsvColor(mBlobColorHsv,"HOLE");
//                Log.i("kekkekekke","DEFAULT HOLE IS SET, HSV (28,121,51)");
//                mBlobColorHsv.val[0] = 28;
//                mBlobColorHsv.val[1] = 121;
//                mBlobColorHsv.val[2] = 51;
//                text_hsv.setText("HSV: (" + (int) mBlobColorHsv.val[0] + ", " + (int) mBlobColorHsv.val[1] +
//                        ", " + (int) mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")");
//                Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
//                mIsColorSelected = true;
//                Toast.makeText(BasicActivity.this,
//                        "DEFAULT HOLE IS SET", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        if (!OpenCVLoader.initDebug()) {
//            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
//        } else {
//            Log.d(TAG, "OpenCV library found inside package. Using it!");
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//    }
//
//    public void onCameraViewStarted(int width, int height) {
//        mRgba = new Mat(height, width, CvType.CV_8UC4);
//        mDetector = new ColorBlobDetector();
//        mSpectrum = new Mat();
//        mBlobColorRgba = new Scalar(255);
//        mBlobColorHsv = new Scalar(255);
//        SPECTRUM_SIZE = new Size(200, 64);
//        CONTOUR_COLOR = new Scalar(255,0,0,255);
//    }
//
//    public void onCameraViewStopped() {
//        mRgba.release();
//    }
//
//    public boolean onTouch(View v, MotionEvent event) {
//        if (mLock == true) {
//            Toast.makeText(BasicActivity.this,
//                    "AREA IS LOCKED",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        int cols = mRgba.cols();
//        int rows = mRgba.rows();
//
//        int xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
//        int yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;
//
//        int x = (int)event.getX() - xOffset;
//        int y = (int)event.getY() - yOffset;
//
//        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");
//
//        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;
//
//        Rect touchedRect = new Rect();
//
//        touchedRect.x = (x>4) ? x-4 : 0;
//        touchedRect.y = (y>4) ? y-4 : 0;
//
//        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
//        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;
//
//        Log.i("cols",String.valueOf(cols));
//        Log.i("rows",String.valueOf(rows));
//        Log.i("mOCvCamView.getWidth",String.valueOf(mOpenCvCameraView.getWidth()));
//        Log.i("mOCvCamView.getHeight",String.valueOf(mOpenCvCameraView.getHeight()));
//        Log.i("xOffset",String.valueOf(xOffset));
//        Log.i("yOffset",String.valueOf(yOffset));
//        Log.i("event.getX",String.valueOf(event.getX()));
//        Log.i("event.getY",String.valueOf(event.getY()));
//        Log.i("x",String.valueOf(x));
//        Log.i("y",String.valueOf(y));
//        Log.i("touchedRect.x",String.valueOf(touchedRect.x));
//        Log.i("touchedRect.y",String.valueOf(touchedRect.y));
//        Log.i("touchedRect.width",String.valueOf(touchedRect.width));
//        Log.i("touchedRect.height",String.valueOf(touchedRect.height));
//
//
//        Mat touchedRegionRgba = mRgba.submat(touchedRect);
//
//        Mat touchedRegionHsv = new Mat();
//        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);
//
//        // Calculate average color of touched region
//        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
//        int pointCount = touchedRect.width*touchedRect.height;
//        for (int i = 0; i < mBlobColorHsv.val.length; i++)
//            mBlobColorHsv.val[i] /= pointCount;
//
//        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);
//
//        text_hsv.setText("HSV: (" + (int) mBlobColorHsv.val[0] + ", " + (int) mBlobColorHsv.val[1] +
//                ", " + (int) mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")");
//
//        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
//                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");
//        Log.i(TAG, "Touched HSV color: (" + mBlobColorHsv.val[0] + ", " + mBlobColorHsv.val[1] +
//                ", " + mBlobColorHsv.val[2] + ", " + mBlobColorHsv.val[3] + ")");
//
//        mDetector.setHsvColor(mBlobColorHsv,"");
//
//        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
//
//        mIsColorSelected = true;
//
//        touchedRegionRgba.release();
//        touchedRegionHsv.release();
//
//        return false; // don't need subsequent touch events
//    }
//
//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        mRgba = inputFrame.rgba();
//
//        if (mIsColorSelected) {
//            mDetector.process(mRgba);
//            List<MatOfPoint> contours = mDetector.getContours();
//            Log.e(TAG, "Contours count: " + contours.size());
//            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR, 3);
//
//            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
//            colorLabel.setTo(mBlobColorRgba);
//
//            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
//            mSpectrum.copyTo(spectrumLabel);
//        }
//
//        return mRgba;
//    }
//
//    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
//        Mat pointMatRgba = new Mat();
//        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
//        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);
//
//        return new Scalar(pointMatRgba.get(0, 0));
//    }
//}
