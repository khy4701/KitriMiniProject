package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by Sioin on 2016-08-14.
 */
public class FriendFindAsyncTask extends AsyncTask<String,Void,List<User>> {
    Activity activity;

    public FriendFindAsyncTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("FriendFindAsyncTask", "onPreExecute()");

    }

    @Override
    protected List<User> doInBackground(String... params) {
        Log.e("FriendFindAsyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/friend_find.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("FriendFindAsyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "id="+params[0];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            Log.e("FriendFindAsyncTask",str);


            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("FriendFindAsyncTask","연결성공");

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
                JSONObject jsonObject = (JSONObject) array.get(0);
                String result = ((String) jsonObject.get("result"));

                // 로그인에 성공하면
                if(result.equals("success")) {
                    Log.e("FriendFindAsyncTask","친구 찾기 성공");

                    // 친구 리스트 만들기
                    List<User> list = new ArrayList<>();
                    for (int i = 1; i < array.length(); i++) {
                        jsonObject = (JSONObject) array.get(i);

                        User member = new User();
                        member.setId((String) jsonObject.get("friend_id"));
                        member.setName((String) jsonObject.get("friend_name"));
                        member.setGps_latitude( (Double)jsonObject.get("gps_lat"));
                        member.setGps_longitude( (Double)jsonObject.get("gps_lon"));

                        String status = (String) jsonObject.get("connect_status");
                        if(status.equals("true"))
                            member.setConnected(true);
                        else member.setConnected(false);

                        list.add(member);
                    }

                    MainActivity.myuserInfo.setFreinds(list);
                    return list;
                }

                return null;
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
    protected void onPostExecute(List<User> friendList) {
        super.onPostExecute(friendList);
        Log.e("FriendFindAsyncTask", "onPostExecute()");

        for(User user : friendList)
        {
            Log.e("FriendFindAsyncTask", "id : "+user.getId() +"name : "+ user.getName());
        }

        // ListView 추가
        ListView listFriend = (ListView) activity.findViewById(R.id.listFriend);
        FriendListAdapter adapter = new FriendListAdapter(activity,R.layout.freindlist_item, friendList );

        listFriend.setAdapter(adapter);
    }
}
