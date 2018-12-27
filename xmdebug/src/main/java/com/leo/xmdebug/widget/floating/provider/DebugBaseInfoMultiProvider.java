package com.leo.xmdebug.widget.floating.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.DebugAppInfoDetailsActivity;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.DebugDeviceInfoDetailsActivity;
import com.leo.xmdebug.widget.floating.model.DebugBaseInfoMultiModel;


public class DebugBaseInfoMultiProvider extends ItemViewProvider<DebugBaseInfoMultiModel, DebugBaseInfoMultiProvider.BaseInfoViewHolder> {
    private Context context;

    public DebugBaseInfoMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugBaseInfoMultiProvider.BaseInfoViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugBaseInfoMultiProvider.BaseInfoViewHolder(inflater.inflate(R.layout.cld_base_info, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugBaseInfoMultiProvider.BaseInfoViewHolder holder, @NonNull DebugBaseInfoMultiModel debugBaseInfoMultiModel) {
        try {
            String appVersion = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
            holder.appInfoTv.setText(this.context.getString(R.string.cld_app_version, new Object[]{appVersion}));
            holder.appInfoLl.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DebugAppInfoDetailsActivity.enterActivity(DebugBaseInfoMultiProvider.this.context);
                }
            });
            holder.deviceInfoTv.setText(this.context.getString(R.string.cld_android_version, new Object[]{Build.VERSION.RELEASE}));
            holder.deviceInfoLl.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DebugDeviceInfoDetailsActivity.enterActivity(DebugBaseInfoMultiProvider.this.context);
                }
            });
            SharedPreferences preferences = this.context.getSharedPreferences("chelun_userinfo", 0);
        } catch (Exception var6) {
        }

    }

    static class BaseInfoViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout appInfoLl;
        private TextView appInfoTv;
        private LinearLayout deviceInfoLl;
        private TextView deviceInfoTv;
        private LinearLayout accountInfoLl;
        private TextView accountInfoTv;

        BaseInfoViewHolder(View itemView) {
            super(itemView);
            this.appInfoLl = (LinearLayout)itemView.findViewById(R.id.cld_home_app_ll);
            this.appInfoTv = (TextView)itemView.findViewById(R.id.cld_home_app_info_tv);
            this.deviceInfoLl = (LinearLayout)itemView.findViewById(R.id.cld_home_device_ll);
            this.deviceInfoTv = (TextView)itemView.findViewById(R.id.cld_home_device_info_tv);
            this.accountInfoLl = (LinearLayout)itemView.findViewById(R.id.cld_home_account_ll);
            this.accountInfoTv = (TextView)itemView.findViewById(R.id.cld_home_account_info_tv);
        }
    }
}
