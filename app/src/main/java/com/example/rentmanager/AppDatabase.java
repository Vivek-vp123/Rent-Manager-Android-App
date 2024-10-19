package com.example.rentmanager;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.content.Context;

@Database(entities = {messEntry.class, Entry.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessEntryDao messEntryDao();
    public abstract EntryDao EntryDao();
}
