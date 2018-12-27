package com.leo.xmdebug;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugAppInfoDetailsActivity extends DebugInfoDetailsActivity {
    public DebugAppInfoDetailsActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugAppInfoDetailsActivity.class);
        context.startActivity(intent);
    }

    protected String getTitleString() {
        return "应用信息";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail();
        detail.setType(DebugInfoDetail.InfoType.APP_ABSTRACT);
        list.add(detail);
        return list;
    }
}