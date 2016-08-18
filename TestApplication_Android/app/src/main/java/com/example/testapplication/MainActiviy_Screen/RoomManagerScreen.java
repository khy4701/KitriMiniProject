package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;

public class RoomManagerScreen {
    static Activity activity;
    boolean isRoomScreenUsed = false;
    LinearLayout room_layout = null;

    public RoomManagerScreen(final Activity activity)
    {
        this.activity = activity;

        room_layout = (LinearLayout) activity.findViewById(R.id.room_layout);
        room_layout.setVisibility(LinearLayout.GONE);

        RoomFindAsyncTask roomFindAsyncTask = new RoomFindAsyncTask(activity);
        roomFindAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());

        Button btnRoomAdd = (Button)activity.findViewById(R.id.btnRoomAdd);
        btnRoomAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edRoomName = (EditText) activity.findViewById(R.id.edRoomName);

                if(!edRoomName.getText().toString().equals("")){
                    RoomAddAsyncTask roomAddAsyncTask = new RoomAddAsyncTask(activity);
                    roomAddAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId(), edRoomName.getText().toString() );
                }else
                    Toast.makeText(activity.getApplicationContext(), "추가할 방이름을 입력하세요",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Change_Visibility(boolean status)
    {
        if( status ) {
            isRoomScreenUsed = true;
            room_layout.setVisibility(View.VISIBLE);
        }
        else {
            isRoomScreenUsed = false;
            room_layout.setVisibility(View.GONE);
        }
    }

    public static void updateRoomList()
    {
        RoomFindAsyncTask roomFindAsyncTask = new RoomFindAsyncTask(activity);
        roomFindAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());
    }

    public static void deleteRoom(int room_num)
    {
        RoomDeleteAsyncTask deleteAsyncTask = new RoomDeleteAsyncTask(activity);
        deleteAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.toString(room_num));
    }


}
