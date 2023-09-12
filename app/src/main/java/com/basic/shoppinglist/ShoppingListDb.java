package com.basic.shoppinglist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.basic.shoppinglist.model.dao.ItemDao;
import com.basic.shoppinglist.model.entity.Item;

@Database(entities = {Item.class}, version = 2)
public abstract class ShoppingListDb extends RoomDatabase {
    public abstract ItemDao itemDao();
}
