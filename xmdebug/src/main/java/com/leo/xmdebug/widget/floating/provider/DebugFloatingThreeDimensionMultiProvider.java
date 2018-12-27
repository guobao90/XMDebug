package com.leo.xmdebug.widget.floating.provider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.utils.DebugReflectionUtil;
import com.leo.xmdebug.widget.DebugFloatingWindow;
import com.leo.xmdebug.widget.ThreeDimensionLayout;
import com.leo.xmdebug.widget.floating.model.DebugFloatingThreeDimensionMultiModel;

public class DebugFloatingThreeDimensionMultiProvider extends ItemViewProvider<DebugFloatingThreeDimensionMultiModel, DebugFloatingThreeDimensionMultiProvider.ThreeDimensionViewHolder> {
    private Context context;
    private ThreeDimensionLayout layout;

    public DebugFloatingThreeDimensionMultiProvider(Context context) {
        this.context = context;
        this.layout = new ThreeDimensionLayout(context);
    }

    @NonNull
    protected DebugFloatingThreeDimensionMultiProvider.ThreeDimensionViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugFloatingThreeDimensionMultiProvider.ThreeDimensionViewHolder(inflater.inflate(R.layout.cld_row_float_3d_views, parent, false));
    }

    protected void onBindViewHolder(@NonNull final DebugFloatingThreeDimensionMultiProvider.ThreeDimensionViewHolder holder, @NonNull DebugFloatingThreeDimensionMultiModel debugFloatingThreeDimensionMultiModel) {
        holder.threeDimensionSwitch.setChecked(this.layout.isLayerInteractionEnabled());
        holder.threeDimensionSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (holder.threeDimensionSwitch.isChecked()) {
                    if (DebugFloatingWindow.getInstance(DebugFloatingThreeDimensionMultiProvider.this.context).isShowingLevel()) {
                        Toast.makeText(DebugFloatingThreeDimensionMultiProvider.this.context, "请先关闭层级展示，再打开本功能", Toast.LENGTH_SHORT).show();
                        holder.threeDimensionSwitch.setChecked(false);
                    } else {
                        if (DebugFloatingThreeDimensionMultiProvider.this.layout.getParent() != null) {
                            DebugFloatingThreeDimensionMultiProvider.this.layout.setLayerInteractionEnabled(false);
                            DebugFloatingThreeDimensionMultiProvider.this.layout.setTargetViewGroup((ViewGroup) null);
                            DebugFloatingThreeDimensionMultiProvider.this.layout.reset();
                            ((ViewGroup) DebugFloatingThreeDimensionMultiProvider.this.layout.getParent()).removeView(DebugFloatingThreeDimensionMultiProvider.this.layout);
                        }

                        Activity currentActivity = DebugReflectionUtil.getShowingActivity();
                        if (currentActivity == null) {
                            holder.threeDimensionSwitch.setChecked(false);
                            return;
                        }

                        ViewGroup viewGroup = (ViewGroup) currentActivity.findViewById(16908290);
                        if (viewGroup == null) {
                            holder.threeDimensionSwitch.setChecked(false);
                            return;
                        }

                        DebugFloatingThreeDimensionMultiProvider.this.layout.setTargetViewGroup(viewGroup);
                        DebugFloatingThreeDimensionMultiProvider.this.layout.setLayerInteractionEnabled(true);
                        holder.showIdsLinearLayout.setVisibility(View.VISIBLE);
                        holder.onlyShowFramesLinearLayout.setVisibility(View.VISIBLE);
                        holder.showIdsSwitch.setChecked(DebugFloatingThreeDimensionMultiProvider.this.layout.isDrawingIds());
                        holder.onlyShowFramesSwitch.setChecked(!DebugFloatingThreeDimensionMultiProvider.this.layout.isDrawingViews());
                        ((ViewGroup) currentActivity.getWindow().getDecorView()).addView(DebugFloatingThreeDimensionMultiProvider.this.layout);
                    }
                } else {
                    DebugFloatingThreeDimensionMultiProvider.this.layout.setLayerInteractionEnabled(false);
                    DebugFloatingThreeDimensionMultiProvider.this.layout.setTargetViewGroup((ViewGroup) null);
                    if (DebugFloatingThreeDimensionMultiProvider.this.layout.getParent() != null) {
                        ((ViewGroup) DebugFloatingThreeDimensionMultiProvider.this.layout.getParent()).removeView(DebugFloatingThreeDimensionMultiProvider.this.layout);
                    }

                    DebugFloatingThreeDimensionMultiProvider.this.layout.setDrawIds(false);
                    DebugFloatingThreeDimensionMultiProvider.this.layout.reset();
                    holder.showIdsLinearLayout.setVisibility(View.GONE);
                    holder.onlyShowFramesLinearLayout.setVisibility(View.GONE);
                }

            }
        });
        holder.showIdsSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        holder.showIdsSwitch.setChecked(this.layout.isDrawingIds());
        holder.showIdsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DebugFloatingThreeDimensionMultiProvider.this.layout.setDrawIds(isChecked);
            }
        });
        holder.onlyShowFramesSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        holder.onlyShowFramesSwitch.setChecked(!this.layout.isDrawingViews());
        holder.onlyShowFramesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DebugFloatingThreeDimensionMultiProvider.this.layout.setDrawViews(!isChecked);
            }
        });
    }

    public boolean isShowing3D() {
        return this.layout != null && this.layout.isLayerInteractionEnabled();
    }

    static class ThreeDimensionViewHolder extends RecyclerView.ViewHolder {
        private Switch threeDimensionSwitch;
        private LinearLayout showIdsLinearLayout;
        private Switch showIdsSwitch;
        private LinearLayout onlyShowFramesLinearLayout;
        private Switch onlyShowFramesSwitch;

        ThreeDimensionViewHolder(View itemView) {
            super(itemView);
            this.threeDimensionSwitch = (Switch) itemView.findViewById(R.id.cld_float_3d_views_switch);
            this.showIdsLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_float_ids_ll);
            this.showIdsSwitch = (Switch) itemView.findViewById(R.id.cld_float_ids_switch);
            this.onlyShowFramesLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_float_only_show_frames_ll);
            this.onlyShowFramesSwitch = (Switch) itemView.findViewById(R.id.cld_float_only_show_frames_switch);
        }
    }
}
