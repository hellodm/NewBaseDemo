package com.hello.app.MyView.opengl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.hello.app.Activity.MyRender;

import javax.microedition.khronos.opengles.GL;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/12/12
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyFirstGlView extends GLSurfaceView implements GLSurfaceView.GLWrapper {

    public MyFirstGlView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }


    private void init() {
        //By default GLSurfaceView will create a PixelFormat.RGB_888 format surface
//        getHolder().setFormat(PixelFormat.TRANSLUCENT);  //设置surfaceView的surface的
//        setDebugFlags(DEBUG_LOG_GL_CALLS);//DEBUG_CHECK_GL_ERROR/DEBUG_LOG_GL_CALLS
//        setEGLConfigChooser(true); //设置默认的EGL配置
////        setEGLConfigChooser(EGLConfigChooser);
////        setEGLConfigChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize);
//        setGLWrapper(this);
//        setRenderer(new MyFirstRender());//设置渲染器
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); //设置渲染模式--一直刷新和请求刷新
    }

    @Override
    public GL wrap(GL gl) {
        return null;
    }


}
