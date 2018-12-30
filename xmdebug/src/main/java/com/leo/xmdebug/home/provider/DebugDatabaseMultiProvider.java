package com.leo.xmdebug.home.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.DebugInfoDetail;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;
import com.leo.xmdebug.home.DebugDatabaseDetailsActivity;
import com.leo.xmdebug.mt.DebugInfoDetailsNormalMultiViewHolder;

public class DebugDatabaseMultiProvider extends ItemViewProvider<DebugInfoDetailsNormalModel, DebugInfoDetailsNormalMultiViewHolder> {
    private Context context;

    public DebugDatabaseMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugInfoDetailsNormalMultiViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugInfoDetailsNormalMultiViewHolder(inflater.inflate(R.layout.cld_details_normal_item, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugInfoDetailsNormalMultiViewHolder holder, @NonNull final DebugInfoDetailsNormalModel model) {
        holder.titleTv.setText(model.getFirst());
        holder.nextIv.setVisibility(0);
        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugDatabaseDetailsActivity.enterActivity(DebugDatabaseMultiProvider.this.context, (DebugInfoDetail)model.getNext().get(0));
            }
        });
    }
}