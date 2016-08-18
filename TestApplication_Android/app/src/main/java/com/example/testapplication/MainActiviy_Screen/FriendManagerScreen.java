package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;

/**
 * Created by Sioin on 2016-08-14.
 */
public class FriendManagerScreen {
    static Activity activity;
    LinearLayout friend_layout = null;
    boolean isFriendScreenUsed = false;

    public FriendManagerScreen(final Activity activity)
    {
        this.activity = activity;
        friend_layout = (LinearLayout) activity.findViewById(R.id.addFriend_layout);
        friend_layout.setVisibility(LinearLayout.GONE);

        FriendFindAsyncTask friendFindAsyncTask = new FriendFindAsyncTask(activity);
        friendFindAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());

        Button btnFindFriend = (Button)activity.findViewById(R.id.btnFindFriend);
        btnFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText friendId = (EditText)activity.findViewById(R.id.edFriendId);

                if(! friendId.getText().toString().equals("")) {
                    FriendAddAsyncTask friendAddAsyncTask = new FriendAddAsyncTask(activity);
                    friendAddAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId(), friendId.getText().toString());
                }else
                    Toast.makeText(activity.getApplicationContext(), "추가할 아이디를 입력하세요",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Change_Visibility(boolean status)
    {
        if( status ) {
            isFriendScreenUsed = true;
            friend_layout.setVisibility(View.VISIBLE);
        }
        else {
            isFriendScreenUsed = false;
            friend_layout.setVisibility(View.GONE);
        }
    }

    public static void updateFriendList()
    {
        FriendFindAsyncTask friendFindAsyncTask = new FriendFindAsyncTask(activity);
        friendFindAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());
    }

    public static void deleteFriend(String friendId)
    {
        FriendDeleteAsyncTask friendDeleteAsyncTask = new FriendDeleteAsyncTask(activity);
        friendDeleteAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId(), friendId);
    }
}
