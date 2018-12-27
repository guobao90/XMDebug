package com.leo.xmdebug.detail;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.DebugInfoDetailsActivity;

import java.util.ArrayList;

public class DebugDeviceInfoDetailsActivity extends DebugInfoDetailsActivity {
    public DebugDeviceInfoDetailsActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugDeviceInfoDetailsActivity.class);
        context.startActivity(intent);
    }

    protected String getTitleString() {
        return "设备信息";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.DEVICE_ABSTRACT);
        list.add(detail);
        return list;
    }
}