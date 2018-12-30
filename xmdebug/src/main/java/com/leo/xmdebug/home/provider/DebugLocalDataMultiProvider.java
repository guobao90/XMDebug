package com.leo.xmdebug.home.provider;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugLocalDataMultiModel;
import com.leo.xmdebug.localData.DebugDatabaseActivity;
import com.leo.xmdebug.localData.DebugFilesActivity;
import com.leo.xmdebug.localData.DebugSharedPreferenceActivity;

import java.io.File;

public class DebugLocalDataMultiProvider extends ItemViewProvider<DebugLocalDataMultiModel, DebugLocalDataMultiProvider.DebugLocalDataViewHolder> {
    private Context context;

    public DebugLocalDataMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugLocalDataMultiProvider.DebugLocalDataViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugLocalDataMultiProvider.DebugLocalDataViewHolder(inflater.inflate(R.layout.cld_local_data, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugLocalDataMultiProvider.DebugLocalDataViewHolder holder, @NonNull DebugLocalDataMultiModel debugLocalDataMultiModel) {
        File spfDir = new File(this.context.getFilesDir().getParent(), "shared_prefs");
        holder.sharedPreferenceCountTextView.setText(String.valueOf(spfDir.listFiles() == null ? 0 : spfDir.listFiles().length));
        holder.sharedPreferenceLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugSharedPreferenceActivity.enterActivity(DebugLocalDataMultiProvider.this.context);
            }
        });
        File databaseDir = new File(this.context.getFilesDir().getParent(), "databases");
        int databaseNumber = 0;
        if (databaseDir.exists() && databaseDir.isDirectory()) {
            File[] var6 = databaseDir.listFiles();
            int var7 = var6.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                File file = var6[var8];
                if (!file.getName().endsWith("-journal")) {
                    ++databaseNumber;
                }
            }
        }

        holder.databaseCountTextView.setText(String.valueOf(databaseNumber));
        holder.databaseLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugDatabaseActivity.enterActivity(DebugLocalDataMultiProvider.this.context);
            }
        });
        holder.appDirectoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugFilesActivity.enterActivity(DebugLocalDataMultiProvider.this.context);
            }
        });
        holder.cleanDataLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] items = DebugLocalDataMultiProvider.this.context.getResources().getStringArray(R.array.cld_clean_type);
                final boolean[] checked = new boolean[items.length];
                (new AlertDialog.Builder(DebugLocalDataMultiProvider.this.context)).setTitle("请选择要清理的数据").setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = isChecked;
                    }
                }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DebugLocalDataMultiProvider.this.cleanData(checked);
                    }
                }).show();
            }
        });
    }

    private void cleanData(boolean[] checked) {
        File databaseDir;
        if (checked[0]) {
            databaseDir = new File(this.context.getFilesDir().getParentFile(), "shared_prefs");
            if (databaseDir.exists() && databaseDir.isDirectory()) {
                this.deleteDir(databaseDir);
            }
        }

        if (checked[1]) {
            databaseDir = this.context.getCacheDir();
            if (databaseDir.exists() && databaseDir.isDirectory()) {
                this.deleteDir(databaseDir);
            }

            if (Environment.getExternalStorageState().equals("mounted")) {
                File externalCacheDir = this.context.getExternalCacheDir();
                if (externalCacheDir != null && externalCacheDir.exists() && externalCacheDir.isDirectory()) {
                    this.deleteDir(externalCacheDir);
                }
            }
        }

        if (checked[2]) {
            databaseDir = new File(this.context.getFilesDir().getParentFile(), "databases");
            if (databaseDir.exists() && databaseDir.isDirectory()) {
                this.deleteDir(databaseDir);
            }
        }

    }

    private void deleteDir(File dir) {
        File[] var2 = dir.listFiles();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (file.isFile()) {
                file.delete();
            } else {
                this.deleteDir(file);
            }
        }

        dir.delete();
    }

    static class DebugLocalDataViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout sharedPreferenceLinearLayout;
        private TextView sharedPreferenceCountTextView;
        private LinearLayout databaseLinearLayout;
        private TextView databaseCountTextView;
        private LinearLayout appDirectoryLinearLayout;
        private LinearLayout cleanDataLinearLayout;

        DebugLocalDataViewHolder(View itemView) {
            super(itemView);
            this.sharedPreferenceLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_local_data_shared_preference_ll);
            this.sharedPreferenceCountTextView = (TextView) itemView.findViewById(R.id.cld_local_data_shared_preference_count_tv);
            this.databaseLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_local_data_database_ll);
            this.databaseCountTextView = (TextView) itemView.findViewById(R.id.cld_local_data_database_count_tv);
            this.appDirectoryLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_local_data_app_directory_ll);
            this.cleanDataLinearLayout = (LinearLayout) itemView.findViewById(R.id.cld_local_data_clean_data_ll);
        }
    }
}
