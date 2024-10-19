package com.example.rentmanager;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessEntryDao {

    @Insert
    void insertMessEntry(messEntry entry);

    @Query("SELECT * FROM mess_entries")
    List<messEntry> getAllMessEntries();

    @Delete
    void deleteMessEntry(messEntry entry);
}
