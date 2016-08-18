package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;

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

/**
 * Created by 한국정보기술 on 2016-08-17.
 */
public class RoomAddAsyncTask extends AsyncTask<String, Void,String>{

    Activity activity;

    public RoomAddAsyncTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("RoomAddAsyncTask", "onPreExecute()");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e("RoomAddAsyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/room_add.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("RoomAddAsyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "user_id="+params[0] + "&room_name="+params[1];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            Log.e("RoomAddAsyncTask",str);


            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("RoomAddAsyncTask","연결성공");

                // 데이터 수신
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;

                temp = br.readLine();

                return temp;
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("RoomAddAsyncTask", "onPostExecute()");

        if(result != null && result.equals("success"))
            Toast.makeText(activity.getApplicationContext(), "방 추가 성공", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity.getApplicationContext(), "방 추가 실패", Toast.LENGTH_SHORT).show();

        EditText edRoomName = (EditText)activity.findViewById(R.id.edRoomName);
        edRoomName.setText("");
        RoomManagerScreen.updateRoomList();
    }
}
