package com.leo.xmdebug.home.provider;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.home.model.DebugLogMultiModel;
import com.leo.xmdebug.log.DebugCrashLogActivity;

public class DebugLogMultiProvider extends ItemViewProvider<DebugLogMultiModel, DebugLogMultiProvider.DebugLogViewHolder> {
    private Context context;

    public DebugLogMultiProvider(Context context) {
        this.context = context;
    }

    @NonNull
    protected DebugLogMultiProvider.DebugLogViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugLogMultiProvider.DebugLogViewHolder(inflater.inflate(R.layout.cld_log, parent, false));
    }

    protected void onBindViewHolder(@NonNull DebugLogMultiProvider.DebugLogViewHolder holder, @NonNull DebugLogMultiModel debugLogMultiModel) {
        holder.crashLogLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DebugCrashLogActivity.enterActivity(DebugLogMultiProvider.this.context);
            }
        });
        holder.giveMeACrashLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String[] exceptions = DebugLogMultiProvider.this.context.getResources().getStringArray(R.array.cld_crash_type);
                AlertDialog.Builder builder = (new AlertDialog.Builder(DebugLogMultiProvider.this.context)).setTitle("崩TM的");
                builder.setSingleChoiceItems(new ArrayAdapter<String>(DebugLogMultiProvider.this.context, 17367043, 16908308, exceptions) {
                    @Nullable
                    public String getItem(int position) {
                        return exceptions[position];
                    }

                    @NonNull
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(DebugLogMultiProvider.this.context).inflate(17367043, parent, false);
                        }

                        ((TextView)convertView).setText(this.getItem(position));
                        return convertView;
                    }
                }, -1, new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                        case 0:
                            throw new NullPointerException();
                        case 1:
                            throw new IndexOutOfBoundsException();
                        case 2:
                            throw new ClassCastException();
                        case 3:
                            throw new IllegalArgumentException();
                        case 4:
                            throw new UnsupportedOperationException();
                        default:
                            throw new NullPointerException();
                        }
                    }
                }).show();
            }
        });
    }

    static class DebugLogViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout crashLogLinearLayout;
        private LinearLayout giveMeACrashLinearLayout;

        DebugLogViewHolder(View itemView) {
            super(itemView);
            this.crashLogLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_log_crash_check_ll);
            this.giveMeACrashLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_log_give_me_a_crash_ll);
        }
    }
}