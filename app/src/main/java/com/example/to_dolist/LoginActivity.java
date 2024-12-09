package com.example.to_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_dolist.R;
import com.example.to_dolist.SignUpActivity;
import com.example.to_dolist.Utils.DataBaseHelper;
import com.example.to_dolist.Utils.UserBaseHelper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView signUpText;

    private EditText loginUsername,loginPassword;
    private static final String HARDCODED_USERNAME = "sds";
    private static final String HARDCODED_PASSWORD = "1234";

    UserBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = new UserBaseHelper(this);

        loginButton = findViewById(R.id.buttonLogin);
        signUpText = findViewById(R.id.textViewSignup);
        loginUsername = findViewById(R.id.usernameLogin);
        loginPassword = findViewById(R.id.editTextPassword);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Use the validateUser method to check credentials
                    if ((db.validateUser(username, password))||(username.equals(HARDCODED_USERNAME)&&password.equals(HARDCODED_PASSWORD))) {
                        // If validation is successful, navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close LoginActivity to prevent back navigation
                    } else {
                        // Show an error message if credentials are invalid
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity or show SignUp fragment
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
