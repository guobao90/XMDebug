
package com.leo.xmdebug.widget.floating.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.DebugHomeActivity;
import com.leo.xmdebug.R;
import com.leo.xmdebug.widget.floating.model.DebugFloatingEntranceMultiModel;

public class DebugFloatingEntranceMultiProvider extends ItemViewProvider<DebugFloatingEntranceMultiModel, DebugFloatingEntranceMultiProvider.FloatingEntranceViewHolder> {
    private Context context;

    public DebugFloatingEntranceMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugFloatingEntranceMultiProvider.FloatingEntranceViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugFloatingEntranceMultiProvider.FloatingEntranceViewHolder(inflater.inflate(R.layout.cld_row_float_entrance, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugFloatingEntranceMultiProvider.FloatingEntranceViewHolder holder, @NonNull DebugFloatingEntranceMultiModel debugFloatingEntranceMultiModel) {
        holder.enterHomeLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugHomeActivity.enterActivity(DebugFloatingEntranceMultiProvider.this.context);
            }
        });
    }

    static class FloatingEntranceViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout enterHomeLinearLayout;

        FloatingEntranceViewHolder(View itemView) {
            super(itemView);
            this.enterHomeLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_float_entrance_home_tv);
        }
    }
}
