package com.example.to_dolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        EditText feedbackInput = findViewById(R.id.feedbackInput);
        Button submitFeedbackBtn = findViewById(R.id.submitFeedbackBtn);

        submitFeedbackBtn.setOnClickListener(v -> {
            String feedback = feedbackInput.getText().toString().trim();

            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please write some feedback!", Toast.LENGTH_SHORT).show();
            } else {
                // Save feedback to a database or send it to a server
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                feedbackInput.setText(""); // Clear the input field
            }
        });
    }
}
