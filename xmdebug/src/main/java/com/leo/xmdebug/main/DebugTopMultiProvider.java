package com.leo.xmdebug.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.main.model.DebugTopMultiModel;

public class DebugTopMultiProvider extends ItemViewProvider<DebugTopMultiModel, DebugTopMultiProvider.TopViewHolder> {
    private Activity activity;

    public DebugTopMultiProvider(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    protected DebugTopMultiProvider.TopViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugTopMultiProvider.TopViewHolder(inflater.inflate(R.layout.cld_top, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugTopMultiProvider.TopViewHolder holder, @NonNull DebugTopMultiModel debugTopMultiModel) {
        holder.floatingWindowSwitch.setOnCheckedChangeListener(null);
        if (DebugFloatingWindow.getInstance(this.activity).isShowing()) {
            holder.floatingWindowSwitch.setChecked(true);
        } else {
            holder.floatingWindowSwitch.setChecked(false);
        }

        holder.floatingWindowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DebugFloatingWindow.getInstance(DebugTopMultiProvider.this.activity).show();
                } else {
                    DebugFloatingWindow.getInstance(DebugTopMultiProvider.this.activity).close();
                }

            }
        });
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {
        private Switch floatingWindowSwitch;

        TopViewHolder(View itemView) {
            super(itemView);
            this.floatingWindowSwitch = (Switch)itemView.findViewById(id.cld_floating_window_switch);
        }
    }
}
