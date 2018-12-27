package com.leo.xmdebug;

import android.os.Bundle;

import com.leo.baseui.dialog.TipsBaseDialog;
import com.leo.baseui.ui.BaseActivity;

public abstract class DebugBaseActivity extends BaseActivity {
    protected TipsBaseDialog tipDialog;

    public DebugBaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = this.getLayoutId();
        if (layoutId != 0) {
            this.setContentView(layoutId);
        }
        this.tipDialog = new TipsBaseDialog(this);
        this.init();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

}