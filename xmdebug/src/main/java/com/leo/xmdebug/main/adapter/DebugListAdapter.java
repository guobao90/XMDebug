package com.leo.xmdebug.main.adapter;

import com.leo.baseui.mutiType.base.Items;
import com.leo.baseui.mutiType.base.MultiTypeAdapter;

public class DebugListAdapter extends MultiTypeAdapter {
    private Items mItems = new Items();

    public DebugListAdapter() {
    }

    @Override
    public Object getItem(int pos) {
        return this.mItems.get(pos);
    }

    public Items getItems() {
        return this.mItems;
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public void setItems(Items items) {
        if (items != null && !items.isEmpty()) {
            this.mItems.clear();
            this.mItems.addAll(items);
            this.notifyDataSetChanged();
        }
    }
}