package com.leo.xmdebug;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.leo.baseui.dialog.TipsBaseDialog;
import com.leo.baseui.toolbar.GBToolbar;

public abstract class DebugBaseFragment extends Fragment {
    public DebugBaseFragment() {
    }

    protected final boolean isActivityDead() {
        boolean dead = false;
        FragmentActivity activity = this.getActivity();
        if (activity != null) {
            dead = activity.isFinishing();
            if (Build.VERSION.SDK_INT >= 17) {
                return dead || activity.isDestroyed();
            }
        }

        return dead;
    }

    public GBToolbar getToolbar() {
        return ((DebugBaseActivity)this.getActivity()).getToolbar();
    }

    protected TipsBaseDialog getTipsBaseDialog() {
        return ((DebugBaseActivity)this.getActivity()).tipDialog;
    }
}