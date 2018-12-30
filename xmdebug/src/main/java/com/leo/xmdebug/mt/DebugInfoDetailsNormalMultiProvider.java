package com.leo.xmdebug.mt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;

public class DebugInfoDetailsNormalMultiProvider extends ItemViewProvider<DebugInfoDetailsNormalModel, DebugInfoDetailsNormalMultiViewHolder> {
    private DebugInfoDetailsActivity activity;

    public DebugInfoDetailsNormalMultiProvider(DebugInfoDetailsActivity activity) {
        this.activity = activity;
    }

    @NonNull
    protected DebugInfoDetailsNormalMultiViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugInfoDetailsNormalMultiViewHolder(inflater.inflate(R.layout.cld_details_normal_item, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugInfoDetailsNormalMultiViewHolder holder, @NonNull final DebugInfoDetailsNormalModel model) {
        holder.titleTv.setText(model.getFirst());
        holder.contentTv.setText(model.getSecond());
        if (model.getNext() != null) {
            holder.nextIv.setVisibility(0);
            holder.item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DebugInfoDetailsNormalMultiProvider.this.activity.openNextDetailFragment(model.getNextTitle(), model.getNext());
                }
            });
        } else {
            holder.nextIv.setVisibility(4);
            holder.item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)DebugInfoDetailsNormalMultiProvider.this.activity.getSystemService("clipboard");
                    ClipData clipData = ClipData.newPlainText(model.getFirst(), model.getSecond());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(DebugInfoDetailsNormalMultiProvider.this.activity, model.getFirst() + "已复制至剪切板", 0).show();
                }
            });
        }

    }
}