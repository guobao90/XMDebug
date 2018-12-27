package com.leo.xmdebug.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;

public class ThreeDimensionLayout extends FrameLayout {
    private static final int TRACKING_UNKNOWN = 0;
    private static final int TRACKING_VERTICALLY = 1;
    private static final int TRACKING_HORIZONTALLY = -1;
    private static final int ROTATION_MAX = 60;
    private static final int ROTATION_MIN = -60;
    private static final int ROTATION_DEFAULT_X = 0;
    private static final int ROTATION_DEFAULT_Y = 0;
    private static final float ZOOM_DEFAULT = 0.6F;
    private static final float ZOOM_MIN = 0.33F;
    private static final float ZOOM_MAX = 2.0F;
    private static final int SPACING_DEFAULT = 25;
    private static final int SPACING_MIN = 10;
    private static final int SPACING_MAX = 100;
    private static final int CHROME_COLOR = -7829368;
    private static final int CHROME_SHADOW_COLOR = -16777216;
    private static final int TEXT_OFFSET_DP = 2;
    private static final int TEXT_SIZE_DP = 10;
    private static final int CHILD_COUNT_ESTIMATION = 25;
    private static final boolean DEBUG = false;
    private final Rect viewBoundsRect;
    private final Paint viewBorderPaint;
    private final Camera camera;
    private final Matrix matrix;
    private final int[] location;
    private final BitSet visibilities;
    private final SparseArray<String> idNames;
    private final Deque<LayeredView> layeredViewQueue;
    private final ThreeDimensionLayout.Pool<ThreeDimensionLayout.LayeredView> layeredViewPool;
    private Resources res;
    private float density;
    private float slop;
    private float textOffset;
    private float textSize;
    private boolean enabled;
    private boolean drawViews;
    private boolean drawIds;
    private int pointerOne;
    private float lastOneX;
    private float lastOneY;
    private int pointerTwo;
    private float lastTwoX;
    private float lastTwoY;
    private int multiTouchTracking;
    private float rotationY;
    private float rotationX;
    private float zoom;
    private float spacing;
    private int chromeColor;
    private int chromeShadowColor;
    private ViewGroup targetViewGroup;

    public ThreeDimensionLayout(Context context) {
        this(context, (AttributeSet)null);
        this.init();
    }

    public ThreeDimensionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.init();
    }

    public ThreeDimensionLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.viewBoundsRect = new Rect();
        this.viewBorderPaint = new Paint(1);
        this.camera = new Camera();
        this.matrix = new Matrix();
        this.location = new int[2];
        this.visibilities = new BitSet(25);
        this.idNames = new SparseArray();
        this.layeredViewQueue = new ArrayDeque();
        this.layeredViewPool = new ThreeDimensionLayout.Pool<ThreeDimensionLayout.LayeredView>(25) {
            protected ThreeDimensionLayout.LayeredView newObject() {
                return new ThreeDimensionLayout.LayeredView(null);
            }
        };
        this.drawViews = true;
        this.pointerOne = -1;
        this.pointerTwo = -1;
        this.multiTouchTracking = 0;
        this.rotationY = 0.0F;
        this.rotationX = 0.0F;
        this.zoom = 0.6F;
        this.spacing = 25.0F;
        this.init();
    }

    private void init() {
        this.res = this.getContext().getResources();
        this.density = this.getContext().getResources().getDisplayMetrics().density;
        this.slop = (float) ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        this.textSize = 10.0F * this.density;
        this.textOffset = 2.0F * this.density;
        this.setChromeColor(-7829368);
        this.viewBorderPaint.setStyle(Paint.Style.STROKE);
        this.viewBorderPaint.setTextSize(this.textSize);
        this.setChromeShadowColor(-16777216);
        if (Build.VERSION.SDK_INT >= 16) {
            this.viewBorderPaint.setTypeface(Typeface.create("sans-serif-condensed", 0));
        }

        LayoutParams lp = new android.widget.FrameLayout.LayoutParams(-1, -1);
        this.setLayoutParams(lp);
    }

    public void setTargetViewGroup(ViewGroup viewGroup) {
        if (viewGroup == null) {
            this.targetViewGroup.setVisibility(View.GONE);
        }

        if (this.targetViewGroup != viewGroup) {
            this.targetViewGroup = viewGroup;
            this.invalidate();
        }

    }

    public void setChromeColor(int color) {
        if (this.chromeColor != color) {
            this.viewBorderPaint.setColor(color);
            this.chromeColor = color;
            this.invalidate();
        }

    }

    public int getChromeColor() {
        return this.chromeColor;
    }

    public void setChromeShadowColor(int color) {
        if (this.chromeShadowColor != color) {
            this.viewBorderPaint.setShadowLayer(1.0F, -1.0F, 1.0F, color);
            this.chromeShadowColor = color;
            this.invalidate();
        }

    }

    public int getChromeShadowColor() {
        return this.chromeShadowColor;
    }

    public void setLayerInteractionEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            this.setWillNotDraw(!enabled);
            this.invalidate();
            if (this.targetViewGroup != null) {
                this.targetViewGroup.setVisibility(enabled ? View.INVISIBLE : View.VISIBLE);
            }
        }

    }

    public boolean isLayerInteractionEnabled() {
        return this.enabled;
    }

    public void setDrawViews(boolean drawViews) {
        if (this.drawViews != drawViews) {
            this.drawViews = drawViews;
            this.invalidate();
        }

    }

    public boolean isDrawingViews() {
        return this.drawViews;
    }

    public void setDrawIds(boolean drawIds) {
        if (this.drawIds != drawIds) {
            this.drawIds = drawIds;
            this.invalidate();
        }

    }

    public boolean isDrawingIds() {
        return this.drawIds;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.enabled || super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.enabled) {
            return super.onTouchEvent(event);
        } else {
            int action = event.getActionMasked();
            int i;
            int count;
            switch(action) {
            case 0:
            case 5:
                i = action == 0 ? 0 : event.getActionIndex();
                if (this.pointerOne == -1) {
                    this.pointerOne = event.getPointerId(i);
                    this.lastOneX = event.getX(i);
                    this.lastOneY = event.getY(i);
                } else if (this.pointerTwo == -1) {
                    this.pointerTwo = event.getPointerId(i);
                    this.lastTwoX = event.getX(i);
                    this.lastTwoY = event.getY(i);
                }
                break;
            case 1:
            case 3:
            case 6:
                i = action != 6 ? 0 : event.getActionIndex();
                count = event.getPointerId(i);
                if (this.pointerOne == count) {
                    this.pointerOne = this.pointerTwo;
                    this.lastOneX = this.lastTwoX;
                    this.lastOneY = this.lastTwoY;
                    this.pointerTwo = -1;
                    this.multiTouchTracking = 0;
                } else if (this.pointerTwo == count) {
                    this.pointerTwo = -1;
                    this.multiTouchTracking = 0;
                }
                break;
            case 2:
                float xOne;
                float yOne;
                float xTwo;
                float yTwo;
                float dxOne;
                float dyOne;
                if (this.pointerTwo == -1) {
                    i = 0;

                    for(count = event.getPointerCount(); i < count; ++i) {
                        if (this.pointerOne == event.getPointerId(i)) {
                            xOne = event.getX(i);
                            yOne = event.getY(i);
                            xTwo = xOne - this.lastOneX;
                            yTwo = yOne - this.lastOneY;
                            dxOne = 90.0F * (xTwo / (float)this.getWidth());
                            dyOne = 90.0F * (-yTwo / (float)this.getHeight());
                            this.rotationY = Math.min(Math.max(this.rotationY + dxOne, -60.0F), 60.0F);
                            this.rotationX = Math.min(Math.max(this.rotationX + dyOne, -60.0F), 60.0F);
                            this.lastOneX = xOne;
                            this.lastOneY = yOne;
                            this.invalidate();
                        }
                    }
                } else {
                    i = event.findPointerIndex(this.pointerOne);
                    count = event.findPointerIndex(this.pointerTwo);
                    xOne = event.getX(i);
                    yOne = event.getY(i);
                    xTwo = event.getX(count);
                    yTwo = event.getY(count);
                    dxOne = xOne - this.lastOneX;
                    dyOne = yOne - this.lastOneY;
                    float dxTwo = xTwo - this.lastTwoX;
                    float dyTwo = yTwo - this.lastTwoY;
                    if (this.multiTouchTracking == 0) {
                        float adx = Math.abs(dxOne) + Math.abs(dxTwo);
                        float ady = Math.abs(dyOne) + Math.abs(dyTwo);
                        if (adx > this.slop * 2.0F || ady > this.slop * 2.0F) {
                            if (adx > ady) {
                                this.multiTouchTracking = -1;
                            } else {
                                this.multiTouchTracking = 1;
                            }
                        }
                    }

                    if (this.multiTouchTracking == 1) {
                        if (yOne >= yTwo) {
                            this.zoom += dyOne / (float)this.getHeight() - dyTwo / (float)this.getHeight();
                        } else {
                            this.zoom += dyTwo / (float)this.getHeight() - dyOne / (float)this.getHeight();
                        }

                        this.zoom = Math.min(Math.max(this.zoom, 0.33F), 2.0F);
                        this.invalidate();
                    } else if (this.multiTouchTracking == -1) {
                        if (xOne >= xTwo) {
                            this.spacing += dxOne / (float)this.getWidth() * 100.0F - dxTwo / (float)this.getWidth() * 100.0F;
                        } else {
                            this.spacing += dxTwo / (float)this.getWidth() * 100.0F - dxOne / (float)this.getWidth() * 100.0F;
                        }

                        this.spacing = Math.min(Math.max(this.spacing, 10.0F), 100.0F);
                        this.invalidate();
                    }

                    if (this.multiTouchTracking != 0) {
                        this.lastOneX = xOne;
                        this.lastOneY = yOne;
                        this.lastTwoX = xTwo;
                        this.lastTwoY = yTwo;
                    }
                }
            case 4:
            }

            return true;
        }
    }

    public void draw(Canvas canvas) {
        if (!this.enabled) {
            super.draw(canvas);
        } else {
            this.getLocationInWindow(this.location);
            float x = (float)this.location[0];
            float y = (float)this.location[1];
            int saveCount = canvas.save();
            float cx = (float)this.getWidth() / 2.0F;
            float cy = (float)this.getHeight() / 2.0F;
            this.camera.save();
            this.camera.rotate(this.rotationX, this.rotationY, 0.0F);
            this.camera.getMatrix(this.matrix);
            this.camera.restore();
            this.matrix.preTranslate(-cx, -cy);
            this.matrix.postTranslate(cx, cy);
            canvas.concat(this.matrix);
            canvas.scale(this.zoom, this.zoom, cx, cy);
            if (!this.layeredViewQueue.isEmpty()) {
                throw new AssertionError("View queue is not empty.");
            } else {
                if (this.targetViewGroup != null) {
                    int i = 0;

                    for(int count = this.targetViewGroup.getChildCount(); i < count; ++i) {
                        ThreeDimensionLayout.LayeredView layeredView = (ThreeDimensionLayout.LayeredView)this.layeredViewPool.obtain();
                        layeredView.set(this.targetViewGroup.getChildAt(i), 0);
                        this.layeredViewQueue.add(layeredView);
                    }
                }

                while(true) {
                    View view;
                    int layer;
                    do {
                        if (this.layeredViewQueue.isEmpty()) {
                            canvas.restoreToCount(saveCount);
                            return;
                        }

                        ThreeDimensionLayout.LayeredView layeredView = (ThreeDimensionLayout.LayeredView)this.layeredViewQueue.removeFirst();
                        view = layeredView.view;
                        layer = layeredView.layer;
                        layeredView.clear();
                        this.layeredViewPool.restore(layeredView);
                        if (view instanceof ViewGroup) {
                            ViewGroup viewGroup = (ViewGroup)view;
                            this.visibilities.clear();
                            int i = 0;

                            for(int count = viewGroup.getChildCount(); i < count; ++i) {
                                View child = viewGroup.getChildAt(i);
                                if (child.getVisibility() == 0) {
                                    this.visibilities.set(i);
                                    child.setVisibility(4);
                                }
                            }
                        }

                        int viewSaveCount = canvas.save();
                        float translateShowX = this.rotationY / 60.0F;
                        float translateShowY = this.rotationX / 60.0F;
                        float tx = (float)layer * this.spacing * this.density * translateShowX;
                        float ty = (float)layer * this.spacing * this.density * translateShowY;
                        canvas.translate(tx, -ty);
                        view.getLocationInWindow(this.location);
                        canvas.translate((float)this.location[0] - x, (float)this.location[1] - y);
                        this.viewBoundsRect.set(0, 0, view.getWidth(), view.getHeight());
                        canvas.drawRect(this.viewBoundsRect, this.viewBorderPaint);
                        if (this.drawViews) {
                            view.draw(canvas);
                        }

                        if (this.drawIds) {
                            int id = view.getId();
                            if (id != -1) {
                                canvas.drawText(this.nameForId(id), this.textOffset, this.textSize, this.viewBorderPaint);
                            }
                        }

                        canvas.restoreToCount(viewSaveCount);
                    } while(!(view instanceof ViewGroup));

                    ViewGroup viewGroup = (ViewGroup)view;
                    int i = 0;

                    for(int count = viewGroup.getChildCount(); i < count; ++i) {
                        if (this.visibilities.get(i)) {
                            View child = viewGroup.getChildAt(i);
                            child.setVisibility(0);
                            ThreeDimensionLayout.LayeredView childLayeredView = (ThreeDimensionLayout.LayeredView)this.layeredViewPool.obtain();
                            childLayeredView.set(child, layer + 1);
                            this.layeredViewQueue.add(childLayeredView);
                        }
                    }
                }
            }
        }
    }

    private String nameForId(int id) {
        String name = (String)this.idNames.get(id);
        if (name == null) {
            try {
                name = this.res.getResourceEntryName(id);
            } catch (Resources.NotFoundException var4) {
                name = String.format("0x%8x", id);
            }

            this.idNames.put(id, name);
        }

        return name;
    }

    public void reset() {
        this.rotationY = 0.0F;
        this.rotationX = 0.0F;
        this.zoom = 0.6F;
        this.spacing = 25.0F;
        this.drawIds = false;
        this.drawViews = true;
        this.invalidate();
    }

    private abstract static class Pool<T> {
        private final Deque<T> pool;

        Pool(int initialSize) {
            this.pool = new ArrayDeque(initialSize);

            for(int i = 0; i < initialSize; ++i) {
                this.pool.addLast(this.newObject());
            }

        }

        T obtain() {
            return this.pool.isEmpty() ? this.newObject() : this.pool.removeLast();
        }

        void restore(T instance) {
            this.pool.addLast(instance);
        }

        protected abstract T newObject();
    }

    private static class LayeredView {
        View view;
        int layer;

        private LayeredView() {
        }

        void set(View view, int layer) {
            this.view = view;
            this.layer = layer;
        }

        void clear() {
            this.view = null;
            this.layer = -1;
        }
    }
}
