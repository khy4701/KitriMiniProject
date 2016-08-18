package com.example.testapplication.MainActiviy_Screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapplication.R;
import com.example.testapplication.Room;

import java.util.List;

/**
 * Created by Sioin on 2016-08-16.
 */
public class DrawerAdapter extends ArrayAdapter<Room> {
    Context context;
    int resource;
    List<Room> objects;
    LayoutInflater inflater;

    public DrawerAdapter(Context context, int resource, List<Room> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // xml파일을 레이아웃 서비스를 가능하게 만들어주는 객체
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(resource, null); // resource가 레이아웃 서비스를 가능하게 만들어짐

            holder = new ViewHolder();

            // 한 항목을 나타내는 뷰의 각 컨트롤 객체를 참조

            holder.roomName= (TextView)convertView.findViewById(R.id.roomName);

            convertView.setTag(holder);
            System.out.println("convertViews Null " + position);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            System.out.println("convertViews Not Null " + position);
        }
        // 데이터를 읽어와서 각 뷰에 출력을 위한 셋팅
        Room room = objects.get(position);

        holder.roomName.setText(room.getRoomName());

        return convertView;
    }

    class ViewHolder {
        TextView roomName;
    }
}
