package com.basic.shoppinglist;

import static android.view.View.OnClickListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.basic.shoppinglist.model.entity.Item;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ShoppingListDb db;
    private ItemListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        db = Room.databaseBuilder(getApplicationContext(),
                ShoppingListDb.class, "database-name").allowMainThreadQueries().build();

        final ArrayList<Item> allItems = new ArrayList<>(db.itemDao().getAll());


        ListView listView = findViewById(R.id.item_list);
        adapter = new ItemListAdapter(allItems, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(clickItemListListener(listView));

        TextInputLayout textInputLayout = findViewById(R.id.add_item_tex);
        textInputLayout.setEndIconOnClickListener(addItemsEndIconListener(textInputLayout, listView));

    }

    private AdapterView.OnItemClickListener clickItemListListener(ListView listView) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView itemIdView = view.findViewById(R.id.item_list_id);
                db.itemDao().deleteById(Integer.parseInt(itemIdView.getText().toString()));
                adapter.removeItemByPosition(i);
            }
        };
    }

    private OnClickListener addItemsEndIconListener(TextInputLayout textInputLayout, ListView listView) {
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
                    listView.setSelection(listView.getAdapter().getCount() - 1);

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