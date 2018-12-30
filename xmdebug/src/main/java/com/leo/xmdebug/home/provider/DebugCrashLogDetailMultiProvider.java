package com.leo.xmdebug.home.provider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugCrashLogDetailModel;

public class DebugCrashLogDetailMultiProvider extends ItemViewProvider<DebugCrashLogDetailModel, DebugCrashLogDetailMultiProvider.DebugCrashLogDetailViewHolder> {
    private Context context;

    public DebugCrashLogDetailMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugCrashLogDetailMultiProvider.DebugCrashLogDetailViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugCrashLogDetailMultiProvider.DebugCrashLogDetailViewHolder(inflater.inflate(R.layout.cld_log_crash_detail, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugCrashLogDetailMultiProvider.DebugCrashLogDetailViewHolder holder, @NonNull final DebugCrashLogDetailModel model) {
        holder.timeTextView.setText(model.getTime());
        if (!TextUtils.isEmpty(model.getStackTrace())) {
            holder.stackTraceLinearLayout.setVisibility(0);
            holder.stackTraceTextView.setText(model.getStackTrace());
            holder.stackTraceTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)DebugCrashLogDetailMultiProvider.this.context.getSystemService("clipboard");
                    ClipData clipData = ClipData.newPlainText("stack trace", model.getStackTrace());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(DebugCrashLogDetailMultiProvider.this.context, "堆栈信息已复制至剪切板", 0).show();
                }
            });
        } else {
            holder.stackTraceLinearLayout.setVisibility(8);
            holder.stackTraceTextView.setOnClickListener((View.OnClickListener)null);
        }

        if (model.getScreenShot() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(model.getScreenShot().getAbsolutePath());
            holder.screenShotImageView.setVisibility(0);
            holder.screenShotImageView.setImageBitmap(bitmap);
        } else {
            holder.screenShotImageView.setVisibility(8);
        }

        if (model.getDump() != null) {
            holder.dumpTextView.setText(model.getDump().getAbsolutePath());
            holder.dumpLinearLayout.setVisibility(0);
            holder.dumpLinearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)DebugCrashLogDetailMultiProvider.this.context.getSystemService("clipboard");
                    ClipData clipData = ClipData.newPlainText("dump file", model.getDump().getAbsolutePath());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(DebugCrashLogDetailMultiProvider.this.context, "dump文件路径已复制至剪切板", 0).show();
                }
            });
        } else {
            holder.dumpLinearLayout.setOnClickListener(null);
            holder.dumpLinearLayout.setVisibility(8);
        }

    }

    static class DebugCrashLogDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        private LinearLayout stackTraceLinearLayout;
        private TextView stackTraceTextView;
        private ImageView screenShotImageView;
        private LinearLayout dumpLinearLayout;
        private TextView dumpTextView;

        DebugCrashLogDetailViewHolder(View itemView) {
            super(itemView);
            this.timeTextView = (TextView)itemView.findViewById(R.id.cld_log_crash_detail_time_tv);
            this.stackTraceLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_log_crash_detail_stack_trace_ll);
            this.stackTraceTextView = (TextView)itemView.findViewById(R.id.cld_log_crash_detail_stack_trace_tv);
            this.screenShotImageView = (ImageView)itemView.findViewById(R.id.cld_log_crash_detail_screen_shot_iv);
            this.dumpLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_log_crash_detail_dump_ll);
            this.dumpTextView = (TextView)itemView.findViewById(R.id.cld_log_crash_detail_dump_tv);
        }
    }
}