package com.example.testapplication.MainActiviy_Screen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;
import com.example.testapplication.User;

import java.util.List;

/**
 * Created by 호영 on 2016-08-17.
 */
public class ParticipantListAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<User> objects;
    private LayoutInflater inflater;

    public ParticipantListAdapter(Context context, int resource, List<User> objects) {
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
            holder.partiId = (TextView) convertView.findViewById(R.id.txtParti_id);
            holder.partiStatus = (TextView) convertView.findViewById(R.id.txtParti_status);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();

        User user = objects.get(position);

        String user_id = user.getId();

        if( MainActivity.myuserInfo.getId().equals(user_id))
            holder.partiId.setText(user.getId()+"(나)");
        else
            holder.partiId.setText(user.getId());


        holder.partiStatus.setText("접속 중");

        if(user.isConnected()) {
            holder.partiStatus.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.partiStatus.setBackgroundColor(Color.RED);
        }

        return convertView;
    }

    class ViewHolder
    {
        TextView partiId;
        TextView partiStatus;

    }

    @Override
    public void remove(Object object) {
        super.remove(object);
    }
}
