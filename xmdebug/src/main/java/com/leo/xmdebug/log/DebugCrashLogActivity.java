package com.leo.xmdebug.log;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugCrashLogActivity extends DebugInfoDetailsActivity {
    public DebugCrashLogActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugCrashLogActivity.class);
        context.startActivity(intent);
    }

    protected String getTitleString() {
        return "崩溃日志";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.CRASH_LOG_LIST);
        list.add(detail);
        return list;
    }
}