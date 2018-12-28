package com.leo.xmdebug.localData;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugDatabaseActivity extends DebugInfoDetailsActivity {
    public DebugDatabaseActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugDatabaseActivity.class);
        context.startActivity(intent);



    }

    protected String getTitleString() {
        return "数据库";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.DATABASE_LIST);
        list.add(detail);

        return list;
    }
}