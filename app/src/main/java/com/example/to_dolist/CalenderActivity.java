package com.example.to_dolist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.Adapter.ToDoAdapter;
import com.example.to_dolist.Model.ToDoModel;
import com.example.to_dolist.Utils.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView taskRecyclerView;
    private TextView emptyText;
    private DataBaseHelper myDB;
    private ToDoAdapter adapter;
    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        emptyText = findViewById(R.id.emptyText);

        myDB = new DataBaseHelper(CalenderActivity.this);
        taskList = new ArrayList<>();
        adapter = new ToDoAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);



        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(adapter);

        // Set initial date to today's date
        long selectedDate = calendarView.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDate);

        int yearToday = calendar.get(Calendar.YEAR);
        int monthToday = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
        int dayToday = calendar.get(Calendar.DAY_OF_MONTH);

        // Format the date as a String in "dd-MM-yyyy" format (assuming your DB uses this format)
        String dateString = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayToday, monthToday, yearToday);
        loadTasksByDate(dateString);

        // Handle date selection from the CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format the date as dd-MM-yyyy
            String dateString2 = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            loadTasksByDate(dateString2);
        });
    }

    private void loadTasksByDate(String date) {
        // Print the formatted date for debugging purposes
        Log.d("Selected Date", "Formatted date: " + date);
        taskList = myDB.getTasksByDate(date);
        if (taskList.isEmpty()) {
            taskRecyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            taskRecyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            adapter.setTasks(taskList);
        }
    }

}
