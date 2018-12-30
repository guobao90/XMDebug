package com.leo.xmdebug.mt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;

public class DebugInfoDetailsPermissionsMultiProvider extends ItemViewProvider<DebugInfoDetailsNormalModel, DebugInfoDetailsNormalMultiViewHolder> {
    private Context context;
    private boolean isAboveM;

    public DebugInfoDetailsPermissionsMultiProvider(Context context) {
        this.context = context;
        this.isAboveM = Build.VERSION.SDK_INT >= 23;
    }

    @NonNull
    protected DebugInfoDetailsNormalMultiViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugInfoDetailsNormalMultiViewHolder(inflater.inflate(R.layout.cld_details_normal_item, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugInfoDetailsNormalMultiViewHolder holder, @NonNull final DebugInfoDetailsNormalModel model) {
        holder.contentTv.setVisibility(8);
        holder.titleTv.setText(model.getFirst());
        if (this.isAboveM) {
            holder.nextIv.setVisibility(0);
            if (this.checkPermission(model.getSecond())) {
                holder.titleTv.setTextColor(this.context.getResources().getColor(R.color.cld_normal_green));
            } else {
                holder.titleTv.setTextColor(this.context.getResources().getColor(R.color.cld_normal_red));
            }
        } else {
            holder.nextIv.setVisibility(8);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    PermissionInfo permissionInfo = DebugInfoDetailsPermissionsMultiProvider.this.context.getPackageManager().getPermissionInfo(model.getFirst(), 128);
                    CharSequence label = permissionInfo.loadLabel(DebugInfoDetailsPermissionsMultiProvider.this.context.getPackageManager());
                    CharSequence description = permissionInfo.loadDescription(DebugInfoDetailsPermissionsMultiProvider.this.context.getPackageManager());
                    AlertDialog dialog = (new AlertDialog.Builder(DebugInfoDetailsPermissionsMultiProvider.this.context)).setTitle(label).setMessage(description).setView(R.layout.cld_widget_permission_dialog).setNegativeButton((CharSequence)null, (android.content.DialogInterface.OnClickListener)null).setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    TextView authorityStateTv = (TextView)dialog.getWindow().getDecorView().findViewById(R.id.cld_permission_dialog_authority_state_tv);
                    LinearLayout authorityLinearLayout = (LinearLayout)dialog.getWindow().getDecorView().findViewById(R.id.cld_permission_dialog_authority_ll);
                    dialog.setCanceledOnTouchOutside(true);
                    if (DebugInfoDetailsPermissionsMultiProvider.this.isAboveM) {
                        authorityLinearLayout.setVisibility(0);
                        if (ContextCompat.checkSelfPermission(DebugInfoDetailsPermissionsMultiProvider.this.context, model.getFirst()) == 0) {
                            authorityStateTv.setText("已授权");
                            authorityStateTv.setTextColor(DebugInfoDetailsPermissionsMultiProvider.this.context.getResources().getColor(R.color.cld_normal_green));
                        } else {
                            authorityStateTv.setText("未授权");
                            authorityStateTv.setTextColor(DebugInfoDetailsPermissionsMultiProvider.this.context.getResources().getColor(R.color.cld_normal_red));
                        }
                    } else {
                        authorityLinearLayout.setVisibility(8);
                    }
                } catch (PackageManager.NameNotFoundException var8) {
                    ;
                }

            }
        });
    }

    private boolean checkPermission(String permission) {
        try {
            if (TextUtils.isEmpty(permission)) {
                return false;
            } else {
                int targetSdkVersion = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0).targetSdkVersion;
                if (targetSdkVersion >= 23) {
                    return ContextCompat.checkSelfPermission(this.context, permission) == 0;
                } else {
                    return PermissionChecker.checkSelfPermission(this.context, permission) == 0;
                }
            }
        } catch (PackageManager.NameNotFoundException var3) {
            return false;
        }
    }
}