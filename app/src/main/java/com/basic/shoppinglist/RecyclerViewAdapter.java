package com.basic.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.shoppinglist.model.entity.Item;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    ArrayList<Item> dataSet;

    public RecyclerViewAdapter(ArrayList<Item> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.getItemName().setText(dataSet.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addItem(Item item) {
        dataSet.add(item);
        this.notifyDataSetChanged();
    }

    public void emptyList() {
        dataSet = new ArrayList<>();
        this.emptyList();
        this.notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        dataSet.remove(position);
        this.notifyItemRemoved(position);
    }

    public ArrayList<Item> getDataSet() {
        return dataSet;
    }
}
