package com.leo.xmdebug.localData;

import android.content.Context;
import android.content.Intent;

import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugSharedPreferenceActivity extends DebugInfoDetailsActivity {
    public DebugSharedPreferenceActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugSharedPreferenceActivity.class);
        context.startActivity(intent);
    }

    protected String getTitleString() {
        return "SharedPreference";
    }

    protected ArrayList<DebugInfoDetail> getDetailsInfo() {
        ArrayList<DebugInfoDetail> list = new ArrayList();
        DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.SHARED_PREFERENCE_LIST);
        list.add(detail);
        return list;
    }
}
