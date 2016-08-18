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
 * Created by 호영 on 2016-08-15.
 */
public class FriendDeleteAsyncTask extends AsyncTask<String, Void, String>{

    Activity activity;

    public FriendDeleteAsyncTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("FriendDeleteAsyncTask", "onPreExecute()");

    }

    @Override
    protected String doInBackground(String... params) {
        Log.e("FriendDeleteAsyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/friend_delete.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("FriendDeleteAsyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "id="+params[0] + "&friendId="+params[1];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            Log.e("FriendDeleteAsyncTask",str);


            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("FriendDeleteAsyncTask","연결성공");

                // 데이터 수신
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;
                StringBuilder json = new StringBuilder();

                // 나머지를 한줄씩 읽어서 json에 저장
                while ((temp = br.readLine()) != null) {
                    json.append(temp);
                }
                Log.e("FriendDeleteAsyncTask", "json="+json.toString());

                // JSONArray형태로 데이터 저장
                JSONArray array = new JSONArray(json.toString());

                // 첫 데이터는 결과정보
                JSONObject jsonObject = (JSONObject) array.get(0);
                String result = ((String) jsonObject.get("result"));
                return result;
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("FriendDeleteAsyncTask", "onPostExecute()");

        if(result != null && result.equals("success"))
            Toast.makeText(activity.getApplicationContext(), "친구 삭제 성공", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity.getApplicationContext(), "친구 삭제 실패", Toast.LENGTH_SHORT).show();

        EditText friendId = (EditText)activity.findViewById(R.id.edFriendId);
        friendId.setText("");
    }
}
