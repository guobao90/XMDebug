package com.leo.xmdebug.home.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class DebugInfoDetailsInstalledAppModel {
    private Drawable appIcon;
    private String name;
    private String packageName;
    private Intent launchIntent;

    public DebugInfoDetailsInstalledAppModel() {
    }

    public Drawable getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Intent getLaunchIntent() {
        return this.launchIntent;
    }

    public void setLaunchIntent(Intent launchIntent) {
        this.launchIntent = launchIntent;
    }
}