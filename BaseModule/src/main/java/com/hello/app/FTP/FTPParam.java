package com.hello.app.FTP;

/**
 * Created by mozzie on 16-11-13.
 */

public class FTPParam {

    /**
     * 服务器名.
     */
    private String hostName;

    /**
     * 端口号
     */
    private int serverPort;

    /**
     * 用户名.
     */
    private String userName;

    /**
     * 密码.
     */
    private String password;

    private boolean isNeedLogin = true;

    public FTPParam() {
    }

    public boolean isNeedLogin() {
        return isNeedLogin;
    }


    public String getHostName() {
        return hostName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public boolean isValid() {
        return hostName != null && userName != null && password != null;
    }

    public FTPParam buildHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public FTPParam buildServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public FTPParam buildUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public FTPParam buildPassword(String password) {
        this.password = password;
        return this;
    }
}
