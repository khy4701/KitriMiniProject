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
 * Created by Sioin on 2016-08-13.
 */
public class SendGPSAsyncTask extends AsyncTask<String, Void, Void>{

    public SendGPSAsyncTask()
    {
    }

    @Override
    protected void onPreExecute() {

        Log.e("SendGPSAsyncTask", "onPreExecute()");
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.e("SendGPSAsyncTask","doInBackground()");

        try {
        // URL 생성
        StringBuilder sb = new StringBuilder();
        sb.append(MainActivity.webServerUrl+"/gps.come");
        URL url  = new URL(sb.toString());

        // url 연결
        Log.e("SendGPSAsyncTask","연결시도");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
        conn.setRequestMethod("POST");  // 보낼때 타입

        // 전송할 데이터
        String str = "id="+params[0] + "&lat="+params[1] + "&lon="+params[2];
        OutputStream os = conn.getOutputStream();
        os.write(str.getBytes());

            Log.e("SendGPSAsyncTask",str);

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
            Log.e("SendGPSAsyncTask","연결성공");

            Log.e("SendGPSAsyncTask","GPS Send 성공");

            return null;
        }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("SendGPSAsyncTask", "onPostExecute()");


    }

}
