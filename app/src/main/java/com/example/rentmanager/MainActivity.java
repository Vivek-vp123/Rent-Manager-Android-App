package com.example.rentmanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ViewGroup contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentFrame = findViewById(R.id.content_frame);

        ImageView menuIcon = findViewById(R.id.menu_icon);
        if (menuIcon != null) {
            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });
        }

        loadRoomActivity();
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return handleMenuItemClick(menuItem);
            }
        });
        popup.show();
    }

    private boolean handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            loadRoomActivity();
            return true;
        } else if (id == R.id.action_about) {
            loadMessActivity();
            return true;
        }
        return false;
    }

    private void loadRoomActivity() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View roomView = inflater.inflate(R.layout.room_activity, contentFrame, false);
        contentFrame.removeAllViews();  // Clear previous views
        contentFrame.addView(roomView);
        roomActivity roomactivity = new roomActivity(this, roomView);
    }

    private void loadMessActivity() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View messView = inflater.inflate(R.layout.mess_activity, contentFrame, false);
        contentFrame.removeAllViews();  // Clear previous views
        contentFrame.addView(messView);
        messActivity messactivity = new messActivity(this, messView);
    }

}
