package com.leo.xmdebug;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leo.baseui.mutiType.base.Items;
import com.leo.xmdebug.home.DebugSwitchMultiProvider;
import com.leo.xmdebug.home.model.DebugSwitchMultiModel;
import com.leo.xmdebug.main.DebugTopMultiProvider;
import com.leo.xmdebug.main.adapter.DebugListAdapter;
import com.leo.xmdebug.main.model.DebugTopMultiModel;
import com.leo.xmdebug.widget.floating.model.DebugBaseInfoMultiModel;
import com.leo.xmdebug.widget.floating.provider.DebugBaseInfoMultiProvider;

public class DebugHomeActivity extends DebugBaseActivity {
    private RecyclerView contentRv;
    private DebugListAdapter adapter;

    public DebugHomeActivity() {
    }

    public static void enterActivity(Context context) {
        Intent intent = new Intent(context, DebugHomeActivity.class);
//        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    protected void init() {
        this.initToolBar();
        this.initViews();
        this.initData();
    }

    private void initToolBar() {
        this.getToolbar().setTitle("Debug");
    }

    private void initViews() {
        this.contentRv = this.findViewById(R.id.content_rv);
    }

    private void initData() {
        this.adapter = new DebugListAdapter();
        this.adapter.register(DebugTopMultiModel.class, new DebugTopMultiProvider(this));
        this.adapter.register(DebugBaseInfoMultiModel.class, new DebugBaseInfoMultiProvider(this));
        this.adapter.register(DebugSwitchMultiModel.class, new DebugSwitchMultiProvider(this));
        this.adapter.register(DebugLocalDataMultiModel.class, new DebugLocalDataMultiProvider(this));
        this.adapter.register(DebugReactNativeMultiModel.class, new DebugReactNativeMultiProvider(this));
        this.adapter.register(DebugEntranceMultiModel.class, new DebugEntranceMultiProvider(this));
        this.adapter.register(DebugLogMultiModel.class, new DebugLogMultiProvider(this));
        this.contentRv.setLayoutManager(new LinearLayoutManager(this));
        this.contentRv.setAdapter(this.adapter);
        Items items = new Items();
        items.add(new DebugTopMultiModel());
        items.add(new DebugBaseInfoMultiModel());
        items.add(new DebugSwitchMultiModel());
        items.add(new DebugNetworkMultiModel());
        items.add(new DebugLocalDataMultiModel());
        if (Debug.mainProject == 1) {
            items.add(new DebugReactNativeMultiModel());
        }

        items.add(new DebugEntranceMultiModel());
        items.add(new DebugLogMultiModel());
        this.adapter.setItems(items);
    }
}