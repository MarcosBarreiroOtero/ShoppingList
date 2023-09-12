package com.basic.shoppinglist.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.basic.shoppinglist.model.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Insert
    long insert(Item item);

    @Query("DELETE FROM item WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM item")
    void deleteAll();


}
