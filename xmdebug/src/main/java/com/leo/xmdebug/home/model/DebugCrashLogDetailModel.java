package com.leo.xmdebug.home.model;

import java.io.File;

public class DebugCrashLogDetailModel {
    private String time;
    private String stackTrace;
    private File screenShot;
    private File dump;

    public DebugCrashLogDetailModel() {
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public File getScreenShot() {
        return this.screenShot;
    }

    public void setScreenShot(File screenShot) {
        this.screenShot = screenShot;
    }

    public File getDump() {
        return this.dump;
    }

    public void setDump(File dump) {
        this.dump = dump;
    }
}