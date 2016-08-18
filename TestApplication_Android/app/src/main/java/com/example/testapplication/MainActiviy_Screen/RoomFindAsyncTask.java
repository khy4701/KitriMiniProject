package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;
import com.example.testapplication.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 한국정보기술 on 2016-08-17.
 */
public class RoomFindAsyncTask extends AsyncTask<String, Void, List<Room>> {

    Activity activity;

    public RoomFindAsyncTask(Activity activity)
    {
        this.activity = activity;
    }


    @Override
    protected List<Room> doInBackground(String... params) {
        Log.e("RoomFindAsyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/room_find.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("RoomFindAsyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "user_id="+params[0];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            Log.e("RoomFindAsyncTask",str);

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("RoomFindAsyncTask","연결성공");

                // 데이터 수신
                BufferedReader br =  new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;
                StringBuilder json = new StringBuilder();

                // 나머지를 한줄씩 읽어서 json에 저장
                while ((temp = br.readLine()) != null) {
                    json.append(temp);
                }
                Log.e("RoomFindAsyncTask", "json="+json.toString());

                // JSONArray형태로 데이터 저장
                JSONArray array = new JSONArray(json.toString());

                // 첫 데이터는 결과정보
                JSONObject jsonObject = null;

                // 친구 리스트 만들기
                List<Room> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    jsonObject = (JSONObject) array.get(i);

                    Room room = new Room();
                    room.setRoomName((String) jsonObject.get("room_name"));
                    room.setRoomNum((int)jsonObject.get("room_num"));
                    room.setRoomOwner((String)jsonObject.get("room_owner"));

                    list.add(room);
                }

                MainActivity.roomList = list;

                return list;

            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(final List<Room> roomList) {
        super.onPostExecute(roomList);
        Log.e("RoomFindAsyncTask", "onPostExecute()");

        // MainScreen에 ListView 추가 - 호영
        ListView listRoom = (ListView) activity.findViewById(R.id.listRoom);
        final RoomListAdapter adapter = new RoomListAdapter(activity, R.layout.roomlist_item, roomList );
        listRoom.setAdapter(adapter);

        listRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(activity, ParticipantActivity.class);
                intent.putExtra("roomInfo", roomList.get(position));
                activity.startActivity(intent);
            }
        });


        // NaviGation에 ListView 추가 - 시온
        ListView listView;
        listView = (ListView) activity.findViewById(R.id.drawerListView);
        DrawerAdapter nav_adapter = new DrawerAdapter(activity.getApplicationContext(), R.layout.drawer_item, roomList); // 안드로이드에서 기본적으로 제공되는 레이아웃
        listView.setAdapter(nav_adapter);

        // 리스트뷰 크기 지정
        int totalHeight = 0;
        for (int i = 0; i < nav_adapter.getCount(); i++){
            View listItem = nav_adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        if(totalHeight < 920){
            totalHeight = 920;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (nav_adapter.getCount() - 1));
        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.item_layout);
                if(roomList.get(i).isChecked()) {
                    roomList.get(i).setChecked(false);
                    layout.setBackgroundColor(0);
                }
                else {
                    roomList.get(i).setChecked(true);
                    MainActivity.getParticipateInfo(roomList.get(i).getRoomNum());
                    layout.setBackgroundColor(Color.CYAN);
                }

                // listview 갱신
                adapter.notifyDataSetChanged();

                Toast.makeText(activity, "눌려따", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
