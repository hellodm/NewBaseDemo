package com.hello.app.Util;

import java.io.File;

/**
 * Created by dong on 2014/7/23.
 */
public class Constant {

    /****voice常量************************************/


    /** 临时Voice后缀 **/
    public static String VOICE_suffix = ".voice";

    /** 存储路径 */
    public static String voicePath = File.separator + "Demo" + File.separator + "voice";

    public static String RECORD_PREPARE = "prepare";  //状态：prepare

    public static String RECORD_START = "start";    //状态：start

    public static String RECORD_STOP = "stop";     //状态：stop

    public static String RECORD_RESET = "reset";    //状态：reset

    public static String RECORD_RELEASE = "release";  //状态：release


}
