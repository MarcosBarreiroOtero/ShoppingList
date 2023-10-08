package com.basic.shoppinglist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder  extends RecyclerView.ViewHolder {

    private TextView itemName;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_list_text);
    }

    public TextView getItemName() {
        return itemName;
    }

    public void setItemName(TextView itemName) {
        this.itemName = itemName;
    }
}
