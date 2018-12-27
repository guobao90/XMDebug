//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.leo.xmdebug.widget.floating.provider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.baseui.mutiType.base.ItemViewProvider;
import com.leo.xmdebug.R;
import com.leo.xmdebug.utils.DebugReflectionUtil;
import com.leo.xmdebug.widget.DebugFloatingWindow;
import com.leo.xmdebug.widget.floating.model.DebugFloatingViewsMultiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DebugFloatingViewsMultiProvider extends ItemViewProvider<DebugFloatingViewsMultiModel, DebugFloatingViewsMultiProvider.FloatingViewsViewHolder> {
    private Context context;
    private boolean showingLevel;
    private Map<View, DebugFloatingViewsMultiProvider.FloatingViewsProperties> viewsProperties;
    private SparseArray<List<View>> views;
    private int lastLevel;
    private Activity currentActivity;
    private View blockView;
    private DebugFloatingViewsMultiProvider.FloatingViewsViewHolder viewHolder;

    public DebugFloatingViewsMultiProvider(Context context) {
        this.context = context;
        this.viewsProperties = new HashMap();
        this.views = new SparseArray();
    }

    @NonNull
    protected DebugFloatingViewsMultiProvider.FloatingViewsViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DebugFloatingViewsMultiProvider.FloatingViewsViewHolder(inflater.inflate(R.layout.cld_row_float_views, parent, false));
    }

    protected void onBindViewHolder(@NonNull final DebugFloatingViewsMultiProvider.FloatingViewsViewHolder holder, @NonNull DebugFloatingViewsMultiModel debugFloatingViewsMultiModel) {
        this.viewHolder = holder;
        holder.viewsSwitch.setOnCheckedChangeListener(null);
        if (this.showingLevel) {
            holder.viewsSwitch.setChecked(true);
            holder.viewsLevelLinearLayout.setVisibility(View.VISIBLE);
        } else {
            holder.viewsSwitch.setChecked(false);
            holder.viewsLevelLinearLayout.setVisibility(View.GONE);
        }

        holder.viewsSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (DebugFloatingWindow.getInstance(DebugFloatingViewsMultiProvider.this.context).isShowing3D()) {
                        Toast.makeText(DebugFloatingViewsMultiProvider.this.context, "请先关闭3D展示，再打开本功能", Toast.LENGTH_SHORT).show();
                        holder.viewsSwitch.setOnCheckedChangeListener(null);
                        holder.viewsSwitch.setChecked(false);
                        holder.viewsSwitch.setOnCheckedChangeListener(this);
                    } else {
                        DebugFloatingViewsMultiProvider.this.showViewLevel(holder);
                    }
                } else {
                    DebugFloatingViewsMultiProvider.this.hideViewLevel(holder);
                }

            }
        });
    }

    private void showViewLevel(final DebugFloatingViewsMultiProvider.FloatingViewsViewHolder holder) {
        this.showingLevel = true;
        this.currentActivity = DebugReflectionUtil.getShowingActivity();
        if (this.currentActivity == null) {
            holder.viewsSwitch.setChecked(false);
            this.showingLevel = false;
        } else {
            this.blockEverything(true);
            holder.viewsLevelLinearLayout.setVisibility(View.VISIBLE);
            holder.levelSeekBar.setVisibility(View.VISIBLE);
            View contentView = this.currentActivity.findViewById(16908290);
            this.viewsProperties.put(contentView, this.readViewProperties(contentView));
            List<View> list = new ArrayList();
            list.add(contentView);
            this.views.put(0, list);
            this.saveViewsState(contentView, 0);
            holder.totalLevelTextView.setText(this.context.getString(R.string.cld_views_total_level, new Object[]{this.views.size() - 1}));
            holder.currentLevelTextView.setText(this.context.getString(R.string.cld_views_current_level, new Object[]{0}));
            holder.levelSeekBar.setOnSeekBarChangeListener(null);
            holder.levelSeekBar.setMax(this.views.size() - 1);
            holder.levelSeekBar.setProgress(0);
            holder.levelSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    holder.currentLevelTextView.setText(DebugFloatingViewsMultiProvider.this.context.getString(R.string.cld_views_current_level, new Object[]{progress}));
                    DebugFloatingViewsMultiProvider.this.showViewsInCurrentLevel(seekBar.getProgress());
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            this.lastLevel = 0;
        }
    }

    private void hideViewLevel(DebugFloatingViewsMultiProvider.FloatingViewsViewHolder holder) {
        this.restoreViewState();
        this.viewsProperties.clear();
        this.views.clear();
        this.blockEverything(false);
        this.showingLevel = false;
        holder.viewsLevelLinearLayout.setVisibility(View.GONE);
        holder.levelSeekBar.setVisibility(View.GONE);
    }

    private void saveViewsState(View view, int level) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int childCount = viewGroup.getChildCount();
            if (childCount != 0) {
                ++level;
                List<View> list = (List)this.views.get(level);
                if (list == null) {
                    list = new ArrayList();
                }

                for(int i = 0; i < viewGroup.getChildCount(); ++i) {
                    View child = viewGroup.getChildAt(i);
                    ((List)list).add(child);
                    this.viewsProperties.put(child, this.readViewProperties(child));
                    child.setVisibility(View.INVISIBLE);
                    this.saveViewsState(viewGroup.getChildAt(i), level);
                }

                this.views.put(level, list);
            }
        }
    }

    private void restoreViewState() {
        Iterator var1 = this.viewsProperties.entrySet().iterator();

        while(var1.hasNext()) {
            Entry<View, DebugFloatingViewsMultiProvider.FloatingViewsProperties> entry = (Entry)var1.next();
            this.writeViewProperties((View)entry.getKey(), (DebugFloatingViewsMultiProvider.FloatingViewsProperties)entry.getValue());
        }

    }

    private void showViewsInCurrentLevel(int currentLevel) {
        int i;
        Iterator var3;
        View view;
        if (currentLevel < this.lastLevel) {
            for(i = this.lastLevel; i > currentLevel; --i) {
                var3 = ((List)this.views.get(i)).iterator();

                while(var3.hasNext()) {
                    view = (View)var3.next();
                    view.setVisibility(View.INVISIBLE);
                }
            }
        } else if (currentLevel > this.lastLevel) {
            for(i = this.lastLevel + 1; i <= currentLevel; ++i) {
                var3 = ((List)this.views.get(i)).iterator();

                while(var3.hasNext()) {
                    view = (View)var3.next();
                    view.setVisibility(View.GONE);
                }
            }
        }

        this.lastLevel = currentLevel;
    }

    private void blockEverything(boolean isBlock) {
        if (this.blockView == null) {
            this.blockView = new View(this.context);
            this.blockView.setLayoutParams(new LayoutParams(-1, -1));
            this.blockView.setBackgroundColor(0);
            this.blockView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        if (isBlock) {
            if (this.blockView.getParent() != null) {
                ((ViewGroup)this.blockView.getParent()).removeView(this.blockView);
            }

            ((ViewGroup)this.currentActivity.getWindow().getDecorView()).addView(this.blockView);
            DebugFloatingWindow.getInstance(this.context).setFlags(32);
        } else {
            ((ViewGroup)this.currentActivity.getWindow().getDecorView()).removeView(this.blockView);
            DebugFloatingWindow.getInstance(this.context).setFlags(8);
        }

    }

    private DebugFloatingViewsMultiProvider.FloatingViewsProperties readViewProperties(View view) {
        DebugFloatingViewsMultiProvider.FloatingViewsProperties properties = new DebugFloatingViewsMultiProvider.FloatingViewsProperties();
        properties.setVisibility(view.getVisibility());
        return properties;
    }

    private void writeViewProperties(View view, DebugFloatingViewsMultiProvider.FloatingViewsProperties properties) {
        view.setVisibility(properties.getVisibility());
    }

    public boolean isShowingLevel() {
        return this.showingLevel;
    }

    public void close() {
        if (this.showingLevel) {
            if (this.viewHolder != null) {
                this.viewHolder.viewsLevelLinearLayout.setVisibility(View.GONE);
                this.viewHolder.levelSeekBar.setVisibility(View.GONE);
                this.viewHolder.levelSeekBar.setOnSeekBarChangeListener((OnSeekBarChangeListener)null);
            }

            this.restoreViewState();
            this.viewsProperties.clear();
            this.views.clear();
            this.blockEverything(false);
            this.showingLevel = false;
        }

    }

    private static class FloatingViewsProperties {
        private int visibility;

        private FloatingViewsProperties() {
        }

        int getVisibility() {
            return this.visibility;
        }

        void setVisibility(int visibility) {
            this.visibility = visibility;
        }
    }

    static class FloatingViewsViewHolder extends ViewHolder {
        private Switch viewsSwitch;
        private LinearLayout viewsLevelLinearLayout;
        private TextView totalLevelTextView;
        private TextView currentLevelTextView;
        private SeekBar levelSeekBar;

        FloatingViewsViewHolder(View itemView) {
            super(itemView);
            this.viewsSwitch = (Switch)itemView.findViewById(R.id.cld_float_views_switch);
            this.viewsLevelLinearLayout = (LinearLayout)itemView.findViewById(R.id.cld_float_views_level_ll);
            this.totalLevelTextView = (TextView)itemView.findViewById(R.id.cld_float_total_level_tv);
            this.currentLevelTextView = (TextView)itemView.findViewById(R.id.cld_float_current_level_tv);
            this.levelSeekBar = (SeekBar)itemView.findViewById(R.id.cld_float_views_sb);
        }
    }
}
