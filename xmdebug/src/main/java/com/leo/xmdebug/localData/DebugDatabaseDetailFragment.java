package com.leo.xmdebug.localData;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.xmdebug.DebugBaseFragment;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugDatabaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DebugDatabaseDetailFragment extends DebugBaseFragment {
    private static final String TABLE = "table";
    private View contentView;
    private RecyclerView container;
    private DebugDatabaseModel.Table table;
    private DebugDatabaseDetailFragment.DataBaseDetailAdapter adapter;

    public DebugDatabaseDetailFragment() {
    }

    public static DebugDatabaseDetailFragment newInstance(DebugDatabaseModel.Table table) {
        DebugDatabaseDetailFragment fragment = new DebugDatabaseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("table", table);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.contentView == null) {
            this.contentView = inflater.inflate(R.layout.cld_fragment_database_detail, container, false);
            this.container = (RecyclerView)this.contentView.findViewById(R.id.cld_database_details_container);
            this.initData();
        }

        return this.contentView;
    }

    private void initData() {
        this.table = (DebugDatabaseModel.Table)this.getArguments().getParcelable("table");
        this.getToolbar().setTitle(this.table.getName());
        if (this.table.getColumns() != null && this.table.getColumns().length != 0) {
            this.container.setLayoutManager(new GridLayoutManager(this.getContext(), this.table.getColumns().length));
            this.adapter = new DebugDatabaseDetailFragment.DataBaseDetailAdapter(this.getContext());
            this.container.setAdapter(this.adapter);
            List<Serializable> data = new ArrayList();
            Collections.addAll(data, this.table.getColumns());
            data.addAll(this.table.getData());
            this.adapter.setData(data);
        }
    }

    static class DataBaseDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView dataTextView;

        DataBaseDetailViewHolder(View itemView) {
            super(itemView);
            this.dataTextView = (TextView)itemView.findViewById(R.id.cld_database_detail_data_tv);
        }
    }

    private static class DataBaseDetailAdapter extends RecyclerView.Adapter<DataBaseDetailViewHolder> {
        private List<Serializable> data;
        private Context context;

        DataBaseDetailAdapter(Context context) {
            this.context = context;
        }

        public void setData(List<Serializable> data) {
            this.data = data;
        }

        public DebugDatabaseDetailFragment.DataBaseDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DebugDatabaseDetailFragment.DataBaseDetailViewHolder(LayoutInflater.from(this.context).inflate(R.layout.cld_database_detail_item, parent, false));
        }

        public void onBindViewHolder(DebugDatabaseDetailFragment.DataBaseDetailViewHolder holder, int position) {
            final Serializable serializable = (Serializable)this.data.get(position);
            holder.dataTextView.setText(String.valueOf(serializable));
            holder.dataTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    (new AlertDialog.Builder(DataBaseDetailAdapter.this.context)).setMessage(String.valueOf(serializable)).setPositiveButton("复制", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ClipboardManager clipboardManager = (ClipboardManager)DataBaseDetailAdapter.this.context.getSystemService("clipboard");
                            ClipData clipData = ClipData.newPlainText("database", String.valueOf(serializable));
                            if (clipboardManager != null) {
                                clipboardManager.setPrimaryClip(clipData);
                                Toast.makeText(DataBaseDetailAdapter.this.context, "数据已复制至剪切板", 0).show();
                            } else {
                                Toast.makeText(DataBaseDetailAdapter.this.context, "数据复制失败", 0).show();
                            }

                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
        }

        public int getItemCount() {
            return this.data == null ? 0 : this.data.size();
        }
    }
}
