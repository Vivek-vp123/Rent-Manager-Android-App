package com.example.rentmanager;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mess_entries")
public class messEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String amount;

    public messEntry(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }

    // Getters and setters for Room
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
