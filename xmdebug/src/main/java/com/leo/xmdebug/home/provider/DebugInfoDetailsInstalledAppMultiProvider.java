package com.leo.xmdebug.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugInfoDetailsInstalledAppModel;

public class DebugInfoDetailsInstalledAppMultiProvider extends ItemViewProvider<DebugInfoDetailsInstalledAppModel, DebugInfoDetailsInstalledAppMultiProvider.AppViewHolder> {
    public DebugInfoDetailsInstalledAppMultiProvider() {
    }

    @NonNull
    protected DebugInfoDetailsInstalledAppMultiProvider.AppViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugInfoDetailsInstalledAppMultiProvider.AppViewHolder(inflater.inflate(R.layout.cld_details_installed_app_item, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugInfoDetailsInstalledAppMultiProvider.AppViewHolder holder, @NonNull final DebugInfoDetailsInstalledAppModel model) {
        if (model.getAppIcon() != null) {
            holder.appIcon.setImageDrawable(model.getAppIcon());
        } else {
            holder.appIcon.setImageResource(17301651);
        }

        holder.appName.setText(model.getName());
        holder.packageName.setText(model.getPackageName());
        holder.launchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.getContext().startActivity(model.getLaunchIntent());
            }
        });
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView appIcon;
        private TextView appName;
        private TextView packageName;
        private TextView launchBtn;

        AppViewHolder(View itemView) {
            super(itemView);
            this.appIcon = (ImageView)itemView.findViewById(R.id.cld_details_installed_app_icon);
            this.appName = (TextView)itemView.findViewById(R.id.cld_details_installed_app_name);
            this.packageName = (TextView)itemView.findViewById(R.id.cld_details_installed_app_package_name);
            this.launchBtn = (TextView)itemView.findViewById(R.id.cld_details_installed_app_launch_btn);
        }
    }
}
