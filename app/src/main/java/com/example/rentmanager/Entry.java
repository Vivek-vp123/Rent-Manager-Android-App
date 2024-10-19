package com.example.rentmanager;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_entries")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String amount;
    private String electricityBill;

    public Entry(String date, String amount, String electricityBill) {
        this.date = date;
        this.amount = amount;
        this.electricityBill = electricityBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getElectricityBill() {
        return electricityBill;
    }
}
