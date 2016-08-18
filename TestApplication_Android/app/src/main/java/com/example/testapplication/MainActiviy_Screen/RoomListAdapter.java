package com.example.testapplication.MainActiviy_Screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapplication.R;
import com.example.testapplication.Room;

import java.util.List;

/**
 * Created by 한국정보기술 on 2016-08-17.
 */
public class RoomListAdapter extends ArrayAdapter{
    private Context context;
    private int resource;
    private List<Room> objects;
    private LayoutInflater inflater;

    public RoomListAdapter(Context context, int resource, List<Room> objects) {
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

            // 수정.

            holder.roomName = (TextView) convertView.findViewById(R.id.txtRoomName);
            holder.btnDelete =  (Button)convertView.findViewById(R.id.btnRoomDelete);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();

        Room room = objects.get(position);

        holder.roomName.setText(room.getRoomName());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("방 삭제");
                alertDialog.setMessage("해당 방에서 나가시겠습니까?");

                // OK 를 누르게 되면 설정창으로 이동합니다.
                alertDialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {

                                // DB
                                RoomManagerScreen.deleteRoom(objects.get(position).getRoomNum());

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
        TextView roomName;
        Button btnDelete;
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
    }
}
