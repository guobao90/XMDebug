package com.leo.xmdebug.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.utils.DateUtils;
import com.leo.xmdebug.DebugInfoDetailsActivity;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.DebugInfoDetail;
import com.leo.xmdebug.mt.model.DebugFilesMultiModel;
import com.leo.xmdebug.utils.DebugOpenFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class DebugFilesMultiProvider extends ItemViewProvider<DebugFilesMultiModel, DebugFilesMultiProvider.DebugFilesViewHolder> {
    private DebugInfoDetailsActivity activity;

    public DebugFilesMultiProvider(DebugInfoDetailsActivity activity) {
        this.activity = activity;
    }

    @NonNull
    protected DebugFilesMultiProvider.DebugFilesViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugFilesMultiProvider.DebugFilesViewHolder(inflater.inflate(R.layout.cld_files_item, parent, false));
    }

    protected void onBindViewHolder(@NonNull final DebugFilesMultiProvider.DebugFilesViewHolder holder, @NonNull final DebugFilesMultiModel model) {
        holder.filenameTextView.setText(model.getFile().getName());
        if (model.getFile().isFile()) {
            holder.fileIcon.setImageResource(R.drawable.cld_svg_file);
            holder.nextImageView.setVisibility(8);
            String details = DateUtils.getTimeString(model.getFile().lastModified() / 1000L, "yyyy/MM/dd HH:mm:ss") + "   ";
            details = details + this.getFileLengthString(model.getFile().length());
            holder.fileDetailsTextView.setText(details);
            holder.filesLinearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DebugOpenFileUtil.openFile(holder.itemView.getContext(), model.getFile());
                }
            });
        } else {
            holder.fileIcon.setImageResource(R.drawable.cld_svg_folder);
            int files = 0;
            int dirs = 0;
            if (model.getFile().listFiles() != null) {
                File[] var5 = model.getFile().listFiles();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    File file = var5[var7];
                    if (file.isFile()) {
                        ++files;
                    } else {
                        ++dirs;
                    }
                }
            }

            holder.fileDetailsTextView.setText(this.activity.getString(R.string.cld_dir_details, new Object[]{files, dirs}));
            holder.nextImageView.setVisibility(0);
            holder.filesLinearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ArrayList<DebugInfoDetail> list = new ArrayList();
                    DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.FILES);
                    detail.setStringExtra(model.getFile().getAbsolutePath());
                    list.add(detail);
                    DebugFilesMultiProvider.this.activity.openNextDetailFragment(model.getFile().getName(), list);
                }
            });
        }

    }

    private String getFileLengthString(long length) {
        double d = (double)length;
        if (d < 1024.0D) {
            return String.format(Locale.CHINA, "%.2f B", d);
        } else {
            d /= 1024.0D;
            if (d < 1024.0D) {
                return String.format(Locale.CHINA, "%.2f KB", d);
            } else {
                d /= 1024.0D;
                if (d < 1024.0D) {
                    return String.format(Locale.CHINA, "%.2f MB", d);
                } else {
                    d /= 1024.0D;
                    if (d < 1024.0D) {
                        return String.format(Locale.CHINA, "%.2f GB", d);
                    } else {
                        d /= 1024.0D;
                        return String.format(Locale.CHINA, "%.2f TB", d);
                    }
                }
            }
        }
    }

    static class DebugFilesViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout filesLinearLayout;
        private ImageView fileIcon;
        private TextView filenameTextView;
        private TextView fileDetailsTextView;
        private ImageView nextImageView;

        DebugFilesViewHolder(View itemView) {
            super(itemView);
            this.filesLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_files_ll);
            this.fileIcon = (ImageView)itemView.findViewById(R.id.cld_files_file_icon);
            this.filenameTextView = (TextView)itemView.findViewById(R.id.cld_files_filename_tv);
            this.fileDetailsTextView = (TextView)itemView.findViewById(R.id.cld_files_file_details_tv);
            this.nextImageView = (ImageView)itemView.findViewById(R.id.cld_files_next_iv);
        }
    }
}