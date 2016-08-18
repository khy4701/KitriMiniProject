package com.example.testapplication.MainActiviy_Screen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapplication.R;
import com.example.testapplication.Room;

public class ParticipantActivity extends AppCompatActivity {
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("방 참여자 목록");
        setContentView(R.layout.activity_participant);

        Intent intent = getIntent();
        room = (Room)intent.getExtras().getSerializable("roomInfo");

        ParticipantFindAsyncTask findAsyncTask = new ParticipantFindAsyncTask(ParticipantActivity.this);
        findAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.toString(room.getRoomNum()));

        Button btnParti_invite = (Button)findViewById(R.id.btnParti_invite);
        btnParti_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edParti_id = (EditText)findViewById(R.id.edParti_id);
                if( !edParti_id.getText().toString().equals(""))
                {
                    ParticipantInviteAsyncTask inviteAsyncTask = new ParticipantInviteAsyncTask(ParticipantActivity.this);
                    inviteAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                            edParti_id.getText().toString(),Integer.toString(room.getRoomNum()));
                }else
                    Toast.makeText(getApplicationContext(), "추가할 아이디를 입력하세요",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
