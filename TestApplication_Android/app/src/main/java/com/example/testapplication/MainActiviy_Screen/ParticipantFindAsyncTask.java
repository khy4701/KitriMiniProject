package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;
import com.example.testapplication.User;

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

public class ParticipantFindAsyncTask extends AsyncTask<String, Void, List<User>>{

    static Activity activity;

    public ParticipantFindAsyncTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("RoomDetailSyncTask", "onPreExecute()");

    }

    @Override
    protected List<User> doInBackground(String... params) {
        Log.e("RoomDetailSyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/parti_find.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("RoomDetailSyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "room_num="+params[0];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            Log.e("RoomDetailSyncTask",str);


            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("RoomDetailSyncTask","연결성공");

                // 데이터 수신
                BufferedReader br =  new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;
                StringBuilder json = new StringBuilder();

                // 나머지를 한줄씩 읽어서 json에 저장
                while ((temp = br.readLine()) != null) {
                    json.append(temp);
                }
                Log.e("FriendFindAsyncTask", "json="+json.toString());

                // JSONArray형태로 데이터 저장
                JSONArray array = new JSONArray(json.toString());

                // 첫 데이터는 결과정보
                JSONObject jsonObject = null;

                // 로그인에 성공하면
                    Log.e("RoomDetailSyncTask","참여자 리스트 불러오기 성공");

                    // 친구 리스트 만들기
                    List<User> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        jsonObject = (JSONObject) array.get(i);

                        User member = new User();
                        member.setId((String) jsonObject.get("user_id"));
                        member.setName((String) jsonObject.get("user_name"));
                        member.setGps_latitude( (Double)jsonObject.get("gps_lat"));
                        member.setGps_longitude( (Double)jsonObject.get("gps_lon"));

                        String status = (String) jsonObject.get("connect_status");
                        if(status.equals("true"))
                            member.setConnected(true);
                        else member.setConnected(false);

                        list.add(member);
                    }

                    //  방 아이디, list
                    //  MainActivity.updateRoomParticipate(Room_id, list);

                    MainActivity.updateRoomParticipate(params[0], list);
                    return list;
                }
                return null;


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
    protected void onPostExecute(List<User> partiList) {
        super.onPostExecute(partiList);
        Log.e("RoomDetailSyncTask", "onPostExecute()");

        if(activity != null)
        {
            // ListView 추가
            ListView listParticipant = (ListView) activity.findViewById(R.id.listParticipant);
            ParticipantListAdapter adapter = new ParticipantListAdapter(activity,R.layout.participantlist_item, partiList );

            listParticipant.setAdapter(adapter);
        }
    }
}
