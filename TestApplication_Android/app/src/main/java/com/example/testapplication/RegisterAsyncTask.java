package com.example.testapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 한국정보기술 on 2016-08-11.
 */
public class RegisterAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    private ProgressDialog dialog; // 진행중 표시


    public RegisterAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("회원가입중");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/register.come");  // 컴퓨터 변경시 수정요망
            URL url = new URL(sb.toString());

            // url 연결
            Log.e("LoginAsync","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "id="+params[0] + "&pwd="+params[1] + "&name="+params[2];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("LoginAsync","연결성공");

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = br.readLine();

                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(activity, values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String params) {
        super.onPostExecute(params);
        dialog.dismiss(); // 프로그래스바 종료
        if(params == null) {
            Toast.makeText(activity, "DB연결 실패", Toast.LENGTH_SHORT).show();
        }
        else if(params.equals("fail")){
            Toast.makeText(activity, "회원가입 실패 - 아이디를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(activity, LoginActivity.class);

            Toast.makeText(activity, "회원가입 성공!.", Toast.LENGTH_SHORT).show();
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
