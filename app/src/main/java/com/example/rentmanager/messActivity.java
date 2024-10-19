package com.example.rentmanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import java.util.Calendar;
import androidx.room.Room;

import android.os.AsyncTask;

import java.util.List;

public class messActivity {
    private AppDatabase db;
    private MessEntryDao messEntryDao;
    private LinearLayout entriesLayout;
    private Button addEntryButton;
    private Context context;

    public messActivity(Context context, View rootView) {
        this.context = context;
        this.entriesLayout = rootView.findViewById(R.id.entries_layout);
        this.addEntryButton = rootView.findViewById(R.id.add_entry_button);

        // Initialize the database
        db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "rentManager.db")
                .fallbackToDestructiveMigration() // Allows deleting data on schema changes
                .build();
        messEntryDao = db.messEntryDao();


        displayMessEntries();

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        showAmountDialog(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showAmountDialog(final String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Details");

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom_edittext, null);
        builder.setView(dialogView);

        final EditText amountInput = dialogView.findViewById(R.id.amount_input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String amount = amountInput.getText().toString();
            if (!amount.isEmpty()) {
                messEntry newEntry = new messEntry(date, amount);
                addMessEntry(newEntry);
            } else {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_edittext_background);
        dialog.show();
    }

    private void displayMessEntries() {
        if (entriesLayout != null) {
            entriesLayout.removeAllViews();
            // Fetch the entries from the database
            new AsyncTask<Void, Void, List<messEntry>>() {
                @Override
                protected List<messEntry> doInBackground(Void... voids) {
                    return messEntryDao.getAllMessEntries();
                }

                @Override
                protected void onPostExecute(List<messEntry> messEntries) {
                    for (messEntry mentry : messEntries) {
                        addEntryToLayout(mentry);
                    }
                }
            }.execute();
        }
    }

    private void addEntryToLayout(messEntry mentry) {
        LinearLayout row = new LinearLayout(context);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 8, 8, 8);

        TextView dateView = new TextView(context);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        dateView.setText(mentry.getDate());

        TextView amountView = new TextView(context);
        amountView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        amountView.setText(mentry.getAmount());

        ImageView removeIcon = new ImageView(context);
        removeIcon.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        removeIcon.setImageResource(R.drawable.ic_remove); // Use your remove icon resource
        removeIcon.setPadding(16, 0, 0, 0);
        removeIcon.setContentDescription("Remove Entry");

        removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMessEntry(mentry);
            }
        });

        row.addView(dateView);
        row.addView(amountView);
        row.addView(removeIcon);

        entriesLayout.addView(row);
    }

    private void addMessEntry(messEntry entry) {
        new AsyncTask<messEntry, Void, Void>() {
            @Override
            protected Void doInBackground(messEntry... entries) {
                messEntryDao.insertMessEntry(entries[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                displayMessEntries();  // Refresh the list after adding
            }
        }.execute(entry);
    }

    private void removeMessEntry(messEntry entry) {
        new AsyncTask<messEntry, Void, Void>() {
            @Override
            protected Void doInBackground(messEntry... entries) {
                messEntryDao.deleteMessEntry(entries[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                displayMessEntries();  // Refresh the list after removal
            }
        }.execute(entry);
    }
}