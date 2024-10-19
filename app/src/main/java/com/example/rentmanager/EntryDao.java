package com.example.rentmanager;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("SELECT * FROM room_entries")
    List<Entry> getAllEntries();
}
