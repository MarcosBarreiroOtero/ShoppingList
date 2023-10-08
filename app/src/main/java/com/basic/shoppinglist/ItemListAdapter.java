package com.basic.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.basic.shoppinglist.model.entity.Item;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> dataSet;
    Context mContext;

    public ItemListAdapter(ArrayList<Item> dataSet, Context context) {
        super(context, R.layout.item_list_view, dataSet);
        this.dataSet = dataSet;
        this.mContext = context;
    }

    //TODO add methods to manage the dataset and update the adapter

    public void addItem(Item item) {
        dataSet.add(item);
        this.notifyDataSetChanged();
    }
    public void emptyList() {
        dataSet = new ArrayList<>();
        this.clear();
        this.notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        dataSet.remove(position);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            convertView = vi.inflate(R.layout.item_list_view, null);
        }


        TextView itemNameView = convertView.findViewById(R.id.item_list_text);
        itemNameView.setText(item.getDescription());

//        TextView itemIdView = convertView.findViewById(R.id.item_list_id);
//        itemIdView.setText(String.valueOf(item.getId()));

        return convertView;
    }
}
