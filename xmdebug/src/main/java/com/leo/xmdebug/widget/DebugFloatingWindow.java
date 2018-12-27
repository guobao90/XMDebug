package com.leo.xmdebug.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.baseui.mutiType.base.Items;
import com.leo.xmdebug.main.adapter.DebugListAdapter;
import com.leo.xmdebug.widget.floating.DebugFloatingItemDecoration;
import com.leo.xmdebug.widget.floating.model.DebugFloatingSwitchMultiModel;
import com.leo.xmdebug.widget.floating.provider.DebugFloatingSwitchMultiProvider;

import java.util.Random;

public class DebugFloatingWindow {
    private static DebugFloatingWindow instance;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View floatingView;
    private Context applicationContext;
    private Handler handler;
    private ImageView iconView;
    private RecyclerView container;
    private TextView closeTextView;
    private LinearLayout contentLinearLayout;
    private GestureDetector detector;
    private int screenWidth;
    private int screenHeight;
    private DebugListAdapter adapter;
    private DebugFloatingViewsMultiProvider viewsProvider;
    private DebugFloatingThreeDimensionMultiProvider threeDimensionProvider;
    private DebugFloatingItemDecoration decoration;
    private boolean showing;

    private DebugFloatingWindow(Context context) {
        this.applicationContext = context;
        this.initData();
        this.initView();
        this.initWindow();
        this.initLayoutParams();
        this.initDrag();
    }

    public static DebugFloatingWindow getInstance(Context context) {
        if (instance == null) {
            Class var1 = DebugFloatingWindow.class;
            synchronized(DebugFloatingWindow.class) {
                if (instance == null) {
                    instance = new DebugFloatingWindow(context);
                }
            }
        }

        return instance;
    }

    private void initData() {
        this.handler = new Handler(Looper.getMainLooper());
        this.viewsProvider = new DebugFloatingViewsMultiProvider(this.applicationContext);
        this.threeDimensionProvider = new DebugFloatingThreeDimensionMultiProvider(this.applicationContext);
    }

    private void initView() {
        this.floatingView = LayoutInflater.from(this.applicationContext).inflate(layout.cld_float_window, (ViewGroup)null, false);
        this.applicationContext = this.applicationContext.getApplicationContext();
        this.iconView = (ImageView)this.floatingView.findViewById(id.cld_float_icon_iv);
        this.container = (RecyclerView)this.floatingView.findViewById(id.cld_float_container_rv);
        this.closeTextView = (TextView)this.floatingView.findViewById(id.cld_float_close_tv);
        this.contentLinearLayout = (LinearLayout)this.floatingView.findViewById(id.cld_float_content_ll);
        this.floatingView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch(keyCode) {
                case 4:
                    DebugFloatingWindow.this.contentLinearLayout.setVisibility(8);
                    return true;
                default:
                    return false;
                }
            }
        });
        this.closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugFloatingWindow.this.contentLinearLayout.setVisibility(8);
            }
        });
        this.iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DebugFloatingWindow.this.contentLinearLayout.getVisibility() == 0) {
                    DebugFloatingWindow.this.contentLinearLayout.setVisibility(8);
                } else if (DebugFloatingWindow.this.contentLinearLayout.getVisibility() == 8) {
                    DebugFloatingWindow.this.contentLinearLayout.setVisibility(0);
                }

            }
        });
        this.adapter = new DebugListAdapter();
        this.addDelegate();
        this.container.setLayoutManager(new LinearLayoutManager(this.applicationContext));
        this.decoration = new DebugFloatingItemDecoration();
        this.container.addItemDecoration(this.decoration);
        this.container.setAdapter(this.adapter);
        Items items = new Items();
        this.addItems(items);
        this.adapter.setItems(items);
    }

    private void addDelegate() {
        this.adapter.register(DebugFloatingSwitchMultiModel.class, new DebugFloatingSwitchMultiProvider(this.applicationContext));
        this.adapter.register(DebugFloatingEntranceMultiModel.class, new DebugFloatingEntranceMultiProvider(this.applicationContext));
        this.adapter.register(DebugFloatingViewsMultiModel.class, this.viewsProvider);
        this.adapter.register(DebugFloatingThreeDimensionMultiModel.class, this.threeDimensionProvider);
    }

    private void addItems(Items items) {
        items.add(new DebugFloatingEntranceMultiModel());
        items.add(new DebugFloatingSwitchMultiModel());
        items.add(new DebugFloatingViewsMultiModel());
        items.add(new DebugFloatingThreeDimensionMultiModel());
    }

    private void initWindow() {
        this.windowManager = (WindowManager)this.applicationContext.getSystemService("window");
    }

    private void initLayoutParams() {
        this.screenHeight = this.applicationContext.getResources().getDisplayMetrics().heightPixels;
        this.screenWidth = this.applicationContext.getResources().getDisplayMetrics().widthPixels;
        this.layoutParams = new ViewGroup.LayoutParams();
        this.layoutParams.type = 2003;
        this.layoutParams.flags = 8;
        this.layoutParams.gravity = 8388659;
        this.layoutParams.width = -2;
        this.layoutParams.height = -2;
        this.layoutParams.format = -3;
        this.layoutParams.horizontalMargin = 0.1F;
        this.layoutParams.verticalMargin = 0.1F;
        this.layoutParams.alpha = 0.8F;
        this.layoutParams.x = 0;
        this.layoutParams.y = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initDrag() {
        this.detector = new GestureDetector(this.applicationContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                ViewGroup.LayoutParams var10000 = DebugFloatingWindow.this.layoutParams;
                var10000.x = (int)((float)var10000.x + distanceX);
                var10000 = DebugFloatingWindow.this.layoutParams;
                var10000.y = (int)((float)var10000.y + distanceY);
                if (DebugFloatingWindow.this.layoutParams.x < 0) {
                    DebugFloatingWindow.this.layoutParams.x = 0;
                }

                if (DebugFloatingWindow.this.layoutParams.x > DebugFloatingWindow.this.screenWidth - DebugFloatingWindow.this.floatingView.getWidth()) {
                    DebugFloatingWindow.this.layoutParams.x = DebugFloatingWindow.this.screenWidth - DebugFloatingWindow.this.floatingView.getWidth();
                }

                if (DebugFloatingWindow.this.layoutParams.y < 0) {
                    DebugFloatingWindow.this.layoutParams.y = 0;
                }

                if (DebugFloatingWindow.this.layoutParams.y > DebugFloatingWindow.this.screenHeight - DebugFloatingWindow.this.floatingView.getHeight() * 2) {
                    DebugFloatingWindow.this.layoutParams.y = DebugFloatingWindow.this.screenHeight - DebugFloatingWindow.this.floatingView.getHeight() * 2;
                }

                try {
                    DebugFloatingWindow.this.windowManager.updateViewLayout(DebugFloatingWindow.this.floatingView, DebugFloatingWindow.this.layoutParams);
                } catch (Exception var6) {
                    var6.printStackTrace();
                }

                return false;
            }

            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void show() {
        if (!this.showing) {
            this.showing = true;
            this.iconView.getDrawable().clearColorFilter();
            Random random = new Random();
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            ColorFilter colorFilter = new PorterDuffColorFilter(color, Mode.SRC_ATOP);
            this.iconView.getDrawable().setColorFilter(colorFilter);
            this.contentLinearLayout.setBackgroundColor(color);
            this.adapter.notifyDataSetChanged();
            this.windowManager.addView(this.floatingView, this.layoutParams);
        }

    }

    public boolean isShowingLevel() {
        return this.viewsProvider.isShowingLevel();
    }

    public boolean isShowing3D() {
        return this.threeDimensionProvider.isShowing3D();
    }

    public void close() {
        if (this.showing) {
            this.contentLinearLayout.setVisibility(8);
            this.windowManager.removeView(this.floatingView);
            this.viewsProvider.close();
            this.showing = false;
        }

    }

    public void setFlags(int flag) {
        this.layoutParams.flags = flag;
        this.windowManager.updateViewLayout(this.floatingView, this.layoutParams);
    }
}