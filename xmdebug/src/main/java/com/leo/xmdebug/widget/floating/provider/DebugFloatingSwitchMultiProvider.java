package com.leo.xmdebug.widget.floating.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.utils.DebugActivityLifecycleCallBacksUtil;
import com.leo.xmdebug.widget.DebugFloatingWindow;
import com.leo.xmdebug.widget.floating.model.DebugFloatingSwitchMultiModel;

public class DebugFloatingSwitchMultiProvider extends ItemViewProvider<DebugFloatingSwitchMultiModel, DebugFloatingSwitchMultiProvider.FloatingSwitchViewHolder> {
    private Context context;

    public DebugFloatingSwitchMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugFloatingSwitchMultiProvider.FloatingSwitchViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugFloatingSwitchMultiProvider.FloatingSwitchViewHolder(inflater.inflate(R.layout.cld_row_float_switch, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugFloatingSwitchMultiProvider.FloatingSwitchViewHolder holder, @NonNull DebugFloatingSwitchMultiModel debugFloatingSwitchMultiModel) {
        holder.floatingSwitch.setOnCheckedChangeListener(null);
        holder.floatingSwitch.setChecked(DebugFloatingWindow.getInstance(this.context).isShowing());
        holder.floatingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DebugFloatingWindow.getInstance(DebugFloatingSwitchMultiProvider.this.context).show();
                } else {
                    DebugFloatingWindow.getInstance(DebugFloatingSwitchMultiProvider.this.context).close();
                }

            }
        });
        holder.activitySwitch.setOnCheckedChangeListener(null);
        holder.activitySwitch.setChecked(DebugActivityLifecycleCallBacksUtil.isAdded());
        holder.activitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DebugActivityLifecycleCallBacksUtil.addCallbacks(DebugFloatingSwitchMultiProvider.this.context);
                } else {
                    DebugActivityLifecycleCallBacksUtil.removeCallbacks(DebugFloatingSwitchMultiProvider.this.context);
                }

            }
        });
    }

    static class FloatingSwitchViewHolder extends RecyclerView.ViewHolder {
        private Switch floatingSwitch;
        private Switch activitySwitch;

        FloatingSwitchViewHolder(View itemView) {
            super(itemView);
            this.floatingSwitch = (Switch)itemView.findViewById(R.id.cld_float_window_switch);
            this.activitySwitch = (Switch)itemView.findViewById(R.id.cld_float_activity_switch);
        }
    }
}