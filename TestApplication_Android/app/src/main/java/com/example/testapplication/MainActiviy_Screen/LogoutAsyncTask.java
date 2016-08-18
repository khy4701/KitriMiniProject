package com.example.testapplication.MainActiviy_Screen;

import android.os.AsyncTask;
import android.util.Log;

import com.example.testapplication.MainActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 호영 on 2016-08-18.
 */
public class LogoutAsyncTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        Log.e("LogoutAsyncTask", "doInBackground()");

        try {
            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/logout.come");
            URL url  = new URL(sb.toString());

            // url 연결
            Log.e("LogoutAsyncTask","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "id="+params[0];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());
            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("LogoutAsyncTask", "연결성공");
            }
            Log.e("LogoutAsyncTask",str);

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
