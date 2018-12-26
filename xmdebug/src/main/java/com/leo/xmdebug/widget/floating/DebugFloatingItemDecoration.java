package com.leo.xmdebug.widget.floating;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leo.utils.DipUtils;

public class DebugFloatingItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable divider = new ColorDrawable(0);
    private int gapSize = DipUtils.dip2px(2.0F);

    public DebugFloatingItemDecoration() {
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for(int i = 0; i < parent.getChildCount(); ++i) {
            View child = parent.getChildAt(i);
            this.divider.setBounds(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + this.gapSize);
            this.divider.draw(c);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, this.gapSize);
    }
}