package com.example.rentmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import android.os.AsyncTask;
import java.util.Calendar;
import java.util.List;

public class roomActivity {

    private LinearLayout entriesLayout;
    private Button addEntryButton;
    private Context context;
    private AppDatabase db;
    private EntryDao entryDao;

    public roomActivity(Context context, View rootView) {
        this.context = context;
        this.entriesLayout = rootView.findViewById(R.id.entries_layout);
        this.addEntryButton = rootView.findViewById(R.id.add_entry_button);

        db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "rentManager.db")
                .fallbackToDestructiveMigration() // Allows deleting data on schema changes
                .build();
        entryDao= db.EntryDao();

        loadEntries();

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
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    showAmountDialog(selectedDate);
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
        final EditText electricityInput = dialogView.findViewById(R.id.electricity_input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String amount = amountInput.getText().toString();
            String electricityBill = electricityInput.getText().toString();
            if (!amount.isEmpty() && !electricityBill.isEmpty()) {
                Entry newEntry = new Entry(date, amount, electricityBill);
                saveEntry(newEntry);
            } else {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_edittext_background);
        dialog.show();
    }

    private void saveEntry(Entry entry) {
        AsyncTask.execute(() -> {
            entryDao.insert(entry);
            loadEntries();
        });
    }

    private void deleteEntry(Entry entry) {
        AsyncTask.execute(() -> {
            entryDao.delete(entry);
            loadEntries();
        });
    }

    private void loadEntries() {
        AsyncTask.execute(() -> {
            List<Entry> entries = entryDao.getAllEntries();
            ((Activity) context).runOnUiThread(() -> displayEntries(entries));
        });
    }

    private void displayEntries(List<Entry> entries) {
        if (entriesLayout != null) {
            entriesLayout.removeAllViews();
            for (Entry entry : entries) {
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
                dateView.setText(entry.getDate());

                TextView amountView = new TextView(context);
                amountView.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                amountView.setText(entry.getAmount());

                TextView electricityView = new TextView(context);
                electricityView.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                electricityView.setText(entry.getElectricityBill());

                ImageView removeIcon = new ImageView(context);
                removeIcon.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                removeIcon.setImageResource(R.drawable.ic_remove); // Use your remove icon resource
                removeIcon.setPadding(16, 0, 0, 0);
                removeIcon.setContentDescription("Remove Entry");

                removeIcon.setOnClickListener(v -> deleteEntry(entry));

                row.addView(dateView);
                row.addView(amountView);
                row.addView(electricityView);
                row.addView(removeIcon);

                entriesLayout.addView(row);
            }

            // Dynamically shift the "Add Entry" button to below the entries
            if (addEntryButton != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) addEntryButton.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.entries_layout);
                addEntryButton.setLayoutParams(params);
            }
        }
    }
}
