package com.leo.xmdebug.mt;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.xmdebug.R;

public class DebugInfoDetailsNormalMultiViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout item;
    public TextView titleTv;
    public TextView contentTv;
    public ImageView nextIv;

    public DebugInfoDetailsNormalMultiViewHolder(View itemView) {
        super(itemView);
        this.item = (LinearLayout)itemView.findViewById(R.id.cld_details_item_ll);
        this.titleTv = (TextView)itemView.findViewById(R.id.cld_details_item_title_tv);
        this.contentTv = (TextView)itemView.findViewById(R.id.cld_details_item_content_tv);
        this.nextIv = (ImageView)itemView.findViewById(R.id.cld_details_item_next_iv);
    }
}