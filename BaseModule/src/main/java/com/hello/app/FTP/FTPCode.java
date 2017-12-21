package com.hello.app.FTP;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/15
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class FTPCode {

    //error
    public final static String error_other = "-1";  //不知名错误

    public final static String error_param = "4";  //参数有误

    public final static String error_connect = "1"; //连接失败

    public final static String error_login = "2";   //登录失败

    public final static String error_file_not_exist = "3";   // 文件不存在

    public final static String error_directory_empty = "5";   // 文件夹为空

    public final static String error_file_copy = "6";   // 文件保存失败

    //success

    public final static String code_success = "0";  //请求成功

}
