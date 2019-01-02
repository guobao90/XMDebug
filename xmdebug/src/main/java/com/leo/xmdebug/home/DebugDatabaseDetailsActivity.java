package com.leo.xmdebug.home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.leo.baseui.dialog.TipsBaseDialog;
import com.leo.baseui.tabview.TabsView;
import com.leo.baseui.ui.BaseActivity;
import com.leo.xmdebug.DebugBaseFragment;
import com.leo.xmdebug.R;
import com.leo.xmdebug.base.DebugInfoDetailsDataProvider;
import com.leo.xmdebug.detail.DebugInfoDetail;
import com.leo.xmdebug.home.model.DebugDatabaseModel;
import com.leo.xmdebug.localData.DebugDatabaseDetailFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebugDatabaseDetailsActivity extends BaseActivity {
    private List<DebugBaseFragment> fragments;
    private TabsView tabs;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;
    private DebugDatabaseModel database;
    private List<String> titles;
    private Handler handler;
    private TipsBaseDialog tipDialog;

    public DebugDatabaseDetailsActivity() {
    }

    public static void enterActivity(Context context, DebugInfoDetail detail) {
        Intent intent = new Intent(context, DebugDatabaseDetailsActivity.class);
        intent.putExtra("detailInfo", detail);
        context.startActivity(intent);
    }

    protected int getLayoutId() {
        return R.layout.cld_activity_database_details;
    }

    protected void init() {
        this.initViews();
        this.initData();
    }

    private void initViews() {
        tipDialog = new TipsBaseDialog(this);
        this.tabs =  this.findViewById(R.id.cld_database_tabs);
        this.viewPager = this.findViewById(R.id.cld_database_container);
    }

    private void initData() {
        this.tipDialog.showLoadingDialog("正在加载");
        this.fragments = new ArrayList();
        this.titles = new ArrayList();
        this.handler = new Handler(Looper.getMainLooper());
        final DebugInfoDetail detail = this.getIntent().getParcelableExtra("detailInfo");
        (new Thread(new Runnable() {
            public void run() {
                DebugDatabaseDetailsActivity.this.database = (DebugDatabaseModel)(new DebugInfoDetailsDataProvider(DebugDatabaseDetailsActivity.this)).getDetail(detail).get(0);
                DebugDatabaseDetailsActivity.this.handler.post(new Runnable() {
                    public void run() {
                        if (DebugDatabaseDetailsActivity.this.database != null && DebugDatabaseDetailsActivity.this.database.getTables() != null && !DebugDatabaseDetailsActivity.this.database.getTables().isEmpty()) {
                            DebugDatabaseDetailsActivity.this.titleBar.setTitle(DebugDatabaseDetailsActivity.this.database.getName());
                            DebugDatabaseDetailsActivity.this.pagerAdapter = DebugDatabaseDetailsActivity.this.new DatabasePagerAdapter(DebugDatabaseDetailsActivity.this.getSupportFragmentManager());
                            Iterator var1 = DebugDatabaseDetailsActivity.this.database.getTables().iterator();

                            DebugDatabaseModel.Table table;
                            while(var1.hasNext()) {
                                table = (DebugDatabaseModel.Table)var1.next();
                                DebugDatabaseDetailsActivity.this.fragments.add(DebugDatabaseDetailFragment.newInstance(table));
                            }

                            DebugDatabaseDetailsActivity.this.viewPager.setAdapter(DebugDatabaseDetailsActivity.this.pagerAdapter);
                            DebugDatabaseDetailsActivity.this.tabs.setupWithViewPager(DebugDatabaseDetailsActivity.this.viewPager);
                            DebugDatabaseDetailsActivity.this.tabs.setOnItemSelectListener(new TabsView.OnItemSelectListener() {
                                public void onSelect(int position, String title) {
                                    DebugDatabaseDetailsActivity.this.viewPager.setCurrentItem(position);
                                }
                            });
                            var1 = DebugDatabaseDetailsActivity.this.database.getTables().iterator();

                            while(var1.hasNext()) {
                                table = (DebugDatabaseModel.Table)var1.next();
                                DebugDatabaseDetailsActivity.this.titles.add(table.getName());
                            }

                            DebugDatabaseDetailsActivity.this.tabs.notifyDataChanged(DebugDatabaseDetailsActivity.this.titles, 0);
                            DebugDatabaseDetailsActivity.this.tipDialog.dismiss();
                        } else {
                            DebugDatabaseDetailsActivity.this.finish();
                        }
                    }
                });
            }
        })).start();
    }

    private class DatabasePagerAdapter extends FragmentStatePagerAdapter {
        DatabasePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment)DebugDatabaseDetailsActivity.this.fragments.get(position);
        }

        public int getCount() {
            return DebugDatabaseDetailsActivity.this.fragments.size();
        }
    }
}