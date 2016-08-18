package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    public static int TIME_OUT = 1001;
    TextView input_id;
    TextView input_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_id = (TextView) findViewById(R.id.input_id);
        input_password = (TextView) findViewById(R.id.input_password);
    }

    public void loginPressed(View v){
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
        loginAsyncTask.execute(input_id.getText().toString(), input_password.getText().toString());
    }

    public void moveRegister(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
