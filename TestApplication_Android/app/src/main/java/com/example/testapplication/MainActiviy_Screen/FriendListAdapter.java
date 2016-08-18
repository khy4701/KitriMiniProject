package com.example.testapplication.MainActiviy_Screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapplication.LoginActivity;
import com.example.testapplication.R;
import com.example.testapplication.User;

import java.util.List;

/**
 * Created by Sioin on 2016-08-14.
 */
public class FriendListAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private List<User> objects;
    private LayoutInflater inflater;

    public FriendListAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(resource,null);

            holder.friend_id = (TextView) convertView.findViewById(R.id.txtFriendId);
            holder.friend_name = (TextView) convertView.findViewById(R.id.txtFriendName);
            holder.btnDelete =  (Button)convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();

        User friend = objects.get(position);

        holder.friend_id.setText(friend.getId());
        holder.friend_name.setText(friend.getName());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("친구 삭제");
                alertDialog.setMessage("해당 친구를 삭제 하시겠습니까?");

                // OK 를 누르게 되면 설정창으로 이동합니다.
                alertDialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {

                                // DB
                                FriendManagerScreen.deleteFriend(objects.get(position).getId());

                                // adapter
                                remove(objects.get(position));
                            }
                        });
                // Cancle 하면 종료 합니다.
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        TextView friend_id;
        TextView friend_name;
        Button btnDelete;
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
    }
}
