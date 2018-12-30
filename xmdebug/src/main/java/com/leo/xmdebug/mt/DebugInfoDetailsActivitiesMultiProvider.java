package com.leo.xmdebug.mt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;

public class DebugInfoDetailsActivitiesMultiProvider extends ItemViewProvider<DebugInfoDetailsNormalModel, DebugInfoDetailsNormalMultiViewHolder> {
    private Context context;

    public DebugInfoDetailsActivitiesMultiProvider(Context context) {
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
                try {
                    Class clazz = Class.forName(model.getFirst());
                    Intent intent = new Intent(DebugInfoDetailsActivitiesMultiProvider.this.context, clazz);
                    DebugInfoDetailsActivitiesMultiProvider.this.context.startActivity(intent);
                } catch (Exception var4) {
                    ;
                }

            }
        });
    }
}
