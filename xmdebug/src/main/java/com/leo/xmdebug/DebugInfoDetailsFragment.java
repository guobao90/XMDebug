package com.leo.xmdebug;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.baseui.mutiType.base.Items;
import com.leo.xmdebug.base.DebugInfoDetailsDataProvider;
import com.leo.xmdebug.detail.DebugInfoDetail;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;
import com.leo.xmdebug.main.adapter.DebugListAdapter;
import com.leo.xmdebug.mt.DebugDatabaseMultiModel;
import com.leo.xmdebug.mt.DebugInfoDetailsActivitiesMultiModel;
import com.leo.xmdebug.mt.DebugInfoDetailsNormalMultiModel;
import com.leo.xmdebug.mt.DebugInfoDetailsPermissionsMultiModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebugInfoDetailsFragment extends DebugBaseFragment {
    private static final String DETAILS = "details";
    private static final String TITLE = "title";
    private View contentView;
    private RecyclerView containerRv;
    private DebugListAdapter adapter;
    private List<DebugInfoDetail> details;
    private String title;
    private Handler handler;
    private DebugInfoDetailsDataProvider detailsProvider;
    private Items items;

    public DebugInfoDetailsFragment() {
    }

    public static DebugInfoDetailsFragment newInstance(String title, ArrayList<DebugInfoDetail> details) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("details", details);
        args.putString("title", title);
        DebugInfoDetailsFragment fragment = new DebugInfoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.contentView == null) {
            this.contentView = inflater.inflate(R.layout.cld_details_container, container, false);
            this.containerRv = (RecyclerView)this.contentView.findViewById(R.id.cld_details_rv);
            this.initData();
        }

        return this.contentView;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !TextUtils.isEmpty(this.title)) {
            this.getToolbar().setTitle(this.title);
        }

    }

    private void initData() {
        this.handler = new Handler(Looper.getMainLooper());
        this.containerRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adapter = new DebugListAdapter() {
            @NonNull
            public Class onFlattenClass(@NonNull Object item) {
                if (item instanceof DebugInfoDetailsNormalModel) {
                    DebugInfoDetailsNormalModel model = (DebugInfoDetailsNormalModel)item;
                    if (model.getType() == DebugInfoDetailsNormalModel.NormalInfoType.NORMAL) {
                        return DebugInfoDetailsNormalMultiModel.class;
                    }

                    if (model.getType() == DebugInfoDetailsNormalModel.NormalInfoType.ACTIVITIES) {
                        return DebugInfoDetailsActivitiesMultiModel.class;
                    }

                    if (model.getType() == DebugInfoDetailsNormalModel.NormalInfoType.PERMISSIONS) {
                        return DebugInfoDetailsPermissionsMultiModel.class;
                    }

                    if (model.getType() == DebugInfoDetailsNormalModel.NormalInfoType.DATABASE) {
                        return DebugDatabaseMultiModel.class;
                    }
                }

                return super.onFlattenClass(item);
            }
        };
        this.registerProvider();
        this.containerRv.setAdapter(this.adapter);
        this.items = new Items();
        this.details = this.getArguments().getParcelableArrayList("details");
        this.title = this.getArguments().getString("title");
        this.getToolbar().setTitle(this.title);
        if (this.details != null) {
            this.getTipsBaseDialog().showLoadingDialog("加载中");
            (new Thread(new Runnable() {
                public void run() {
                    DebugInfoDetailsFragment.this.detailsProvider = new DebugInfoDetailsDataProvider(DebugInfoDetailsFragment.this.getContext());
                    Iterator var1 = DebugInfoDetailsFragment.this.details.iterator();

                    while(var1.hasNext()) {
                        DebugInfoDetail detail = (DebugInfoDetail)var1.next();
                        List<Object> data = DebugInfoDetailsFragment.this.detailsProvider.getDetail(detail);
                        if (data != null && !data.isEmpty()) {
                            DebugInfoDetailsFragment.this.items.addAll(data);
                        }
                    }

                    DebugInfoDetailsFragment.this.handler.post(new Runnable() {
                        public void run() {
                            DebugInfoDetailsFragment.this.getTipsBaseDialog().dismiss();
                            DebugInfoDetailsFragment.this.adapter.setItems(DebugInfoDetailsFragment.this.items);
                        }
                    });
                }
            })).start();
            this.adapter.setItems(this.items);
        }
    }

    private void registerProvider() {
        this.adapter.register(DebugInfoDetailsNormalMultiModel.class, new DebugInfoDetailsNormalMultiProvider((DebugInfoDetailsActivity)this.getActivity()));
        this.adapter.register(DebugInfoDetailsActivitiesMultiModel.class, new DebugInfoDetailsActivitiesMultiProvider(this.getActivity()));
        this.adapter.register(DebugInfoDetailsPermissionsMultiModel.class, new DebugInfoDetailsPermissionsMultiProvider(this.getContext()));
        this.adapter.register(DebugInfoDetailsInstalledAppModel.class, new DebugInfoDetailsInstalledAppMultiProvider());
        this.adapter.register(DebugCrashLogDetailModel.class, new DebugCrashLogDetailMultiProvider(this.getContext()));
        this.adapter.register(DebugFilesMultiModel.class, new DebugFilesMultiProvider((DebugInfoDetailsActivity)this.getActivity()));
        this.adapter.register(DebugDatabaseMultiModel.class, new DebugDatabaseMultiProvider(this.getContext()));
    }
}