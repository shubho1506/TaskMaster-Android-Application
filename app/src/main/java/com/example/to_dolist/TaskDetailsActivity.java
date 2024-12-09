package com.example.to_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {
    private TextView titleText, categoryText, locationText, descriptionText, statusText, targetDateText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        titleText = findViewById(R.id.titleText);
        categoryText = findViewById(R.id.categoryText);
        locationText = findViewById(R.id.locationText);
        descriptionText = findViewById(R.id.descriptionText);
        statusText = findViewById(R.id.statusText);
        targetDateText = findViewById(R.id.targetDateText);

        Intent intent = getIntent();
        if (intent != null) {
            titleText.setText(intent.getStringExtra("title"));
            categoryText.setText("Category: " + intent.getStringExtra("category"));
            locationText.setText("Location: " + intent.getStringExtra("location"));
            descriptionText.setText("Description: " + intent.getStringExtra("description"));
            statusText.setText("Status: " + intent.getStringExtra("status"));
            targetDateText.setText("Target Date: " + intent.getStringExtra("targetDate"));

        }
    }
}
