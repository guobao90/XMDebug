package com.leo.xmdebug;

import android.support.v4.app.Fragment;
import android.view.View;

import com.leo.baseui.ui.BaseActivity;
import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class DebugInfoDetailsActivity extends BaseActivity {
    private LinkedList<DebugInfoDetailsFragment> fragments = new LinkedList();

    public DebugInfoDetailsActivity() {
    }

    protected int getLayoutId() {
        return R.layout.cld_activity_info;
    }

    protected void init() {
        DebugInfoDetailsFragment fragment = DebugInfoDetailsFragment.newInstance(this.getTitleString(), this.getDetailsInfo());
        this.fragments.offerFirst(fragment);
        this.getSupportFragmentManager().beginTransaction().add(R.id.cld_app_details_container_fl, fragment).setTransition(4097).commit();
        this.titleBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugInfoDetailsActivity.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        if (this.fragments.size() > 1) {
            this.getSupportFragmentManager().beginTransaction().remove((Fragment)this.fragments.poll()).show((Fragment)this.fragments.peek()).setTransition(8194).commit();
        } else {
            super.onBackPressed();
        }

    }

    public void openNextDetailFragment(String title, ArrayList<DebugInfoDetail> details) {
        DebugInfoDetailsFragment fragment = DebugInfoDetailsFragment.newInstance(title, details);
        this.getSupportFragmentManager().beginTransaction().hide((Fragment)this.fragments.peek()).add(R.id.cld_app_details_container_fl, fragment).setTransition(4097).commit();
        this.fragments.offerFirst(fragment);
    }

    protected abstract String getTitleString();

    protected abstract ArrayList<DebugInfoDetail> getDetailsInfo();
}