package com.example.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView regName;
    TextView regId;
    TextView regPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = (TextView) findViewById(R.id.regName);
        regId = (TextView) findViewById(R.id.regId);
        regPwd = (TextView) findViewById(R.id.regPwd);
    }

    public void registerPressed(View v){
        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this);
        registerAsyncTask.execute(regId.getText().toString(), regPwd.getText().toString(), regName.getText().toString());
    }
}
