package com.leo.xmdebug.home;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugSwitchMultiModel;
import com.leo.xmdebug.utils.DebugActivityLifecycleCallBacksUtil;

public class DebugSwitchMultiProvider extends ItemViewProvider<DebugSwitchMultiModel, DebugSwitchMultiProvider.DebugSwitchViewHolder> {
    private Context context;

    public DebugSwitchMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugSwitchMultiProvider.DebugSwitchViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugSwitchMultiProvider.DebugSwitchViewHolder(inflater.inflate(R.layout.cld_debug_switch, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugSwitchMultiProvider.DebugSwitchViewHolder holder, @NonNull DebugSwitchMultiModel debugSwitchMultiModel) {
        holder.activitySwitch.setOnCheckedChangeListener(null);
        holder.activitySwitch.setChecked(DebugActivityLifecycleCallBacksUtil.isAdded());
        holder.activitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DebugActivityLifecycleCallBacksUtil.addCallbacks(DebugSwitchMultiProvider.this.context);
                } else {
                    DebugActivityLifecycleCallBacksUtil.removeCallbacks(DebugSwitchMultiProvider.this.context);
                }

            }
        });
        holder.statisticLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = (new AlertDialog.Builder(DebugSwitchMultiProvider.this.context)).setTitle("如何查看友盟统计事件").setMessage(Html.fromHtml("请将手机连接至电脑后，Mac/Linux请在Terminal中、Windows请在CMD中输入<font color='red'>adb logcat -s '事件'</font>\n此功能需要安装ADB并正确设置环境变量(Windows)，或定位至adb目录再执行上述命令")).setPositiveButton((CharSequence)null, (android.content.DialogInterface.OnClickListener)null).setNegativeButton("知道了", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                dialog.setCanceledOnTouchOutside(true);
            }
        });
    }

    static class DebugSwitchViewHolder extends RecyclerView.ViewHolder {
        private Switch activitySwitch;
        private LinearLayout statisticLinearLayout;

        DebugSwitchViewHolder(View itemView) {
            super(itemView);
            this.activitySwitch = (Switch)itemView.findViewById(R.id.cld_activity_switch);
            this.statisticLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_statistic_switch_ll);
        }
    }
}
