package com.example.lepra.mylogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LoignActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loign);

        bindUI();

        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        setCredentialsIsExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (login(email, password)) {
                    goToMain();
                    saveOnPreferences(email, password);
                }
            }
        });

    }

    private void bindUI() {

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

    }

    private boolean login(String email, String password) {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email is not valid, please try again.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPasswor(password)) {
            Toast.makeText(this, "Password is not valid, 4 characters or more, please try again.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveOnPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
//            editor.commit();
            editor.apply();
        }
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPasswor(String password) {
        return password.length() >= 4;
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setCredentialsIsExist() {
        String email = getUserMailPrefs();
        String password = getUserPassPrefs();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
        }
    }

    private String getUserMailPrefs() {
        return sharedPreferences.getString("email", "");
    }

    private String getUserPassPrefs() {
        return sharedPreferences.getString("pass", "");
    }
}
