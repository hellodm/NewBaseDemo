package com.hello.app.FTP;

/**
 * Created by mozzie on 16-11-14.
 */

public class FileBean {

    private String name;

    private int type;

    private String path;

    private long time;

    FileBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
