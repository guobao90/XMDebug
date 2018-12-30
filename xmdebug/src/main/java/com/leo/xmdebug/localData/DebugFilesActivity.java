package com.leo.xmdebug.localData;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugFilesActivity extends DebugInfoDetailsActivity {
    public DebugFilesActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugFilesActivity.class);
        context.startActivity(intent);
    }

    protected String getTitleString() {
        return "文件浏览";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.FILES);
        detail.setStringExtra(this.getFilesDir().getParent());
        list.add(detail);
        return list;
    }
}