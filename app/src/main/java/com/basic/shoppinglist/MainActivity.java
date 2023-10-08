package com.basic.shoppinglist;

import static android.view.View.OnClickListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.basic.shoppinglist.model.entity.Item;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ShoppingListDb db;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        db = Room.databaseBuilder(getApplicationContext(),
                ShoppingListDb.class, "database-name").allowMainThreadQueries().build();


        RecyclerView recyclerView = findViewById(R.id.item_list);
        configureRecyclerView(recyclerView);

        TextInputLayout textInputLayout = findViewById(R.id.add_item_tex);
        textInputLayout.setEndIconOnClickListener(addItemsEndIconListener(textInputLayout, recyclerView));

    }

    private void configureRecyclerView(RecyclerView recyclerView) {
        final ArrayList<Item> allItems = new ArrayList<>(db.itemDao().getAll());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        adapter = new RecyclerViewAdapter(allItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeItemListListener());
        itemTouchhelper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ItemTouchHelper.Callback swipeItemListListener() {
        return new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Item item = adapter.getDataSet().get(position);
                db.itemDao().deleteById(item.getId());
                adapter.removeItemByPosition(position);
            }
        };
    }

    private OnClickListener addItemsEndIconListener(TextInputLayout textInputLayout, RecyclerView recyclerView) {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = textInputLayout.getEditText();

                String newItemDescription = editText.getText().toString();

                if (!"".equals(newItemDescription)) {
                    Item item = new Item();
                    item.setDescription(newItemDescription);
                    item.setState(1);

                    long newItemId = db.itemDao().insert(item);
                    item.setId((int) newItemId);

                    adapter.addItem(item);

                    //Focus on the last element of the list
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

                    //Clean input text
                    editText.getText().clear();
                }
            }
        };
    }

    public void clearAllItems(View view) {

        AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.alert_clear_items_title)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.emptyList();
                        db.itemDao().deleteAll();
                    }
                })
                .create();

        alert.show();
    }
}