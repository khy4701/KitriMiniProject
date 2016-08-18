package com.example.testapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Created by 한국정보기술 on 2016-08-11.
 */
public class LoginAsyncTask extends AsyncTask<String, String, User> {
    Activity activity;
    private ProgressDialog dialog; // 진행중 표시


    public LoginAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("로그인중");
        dialog.show();
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // URL 생성
            StringBuilder sb = new StringBuilder();
            sb.append(MainActivity.webServerUrl+"/login.come");
            URL url = new URL(sb.toString());

            // url 연결
            Log.e("LoginAsync","연결시도");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept","application/json"); // 받을때 데이터타입
            conn.setRequestMethod("POST");  // 보낼때 타입

            // 전송할 데이터
            String str = "id="+params[0] + "&pwd="+params[1];
            OutputStream os = conn.getOutputStream();
            os.write(str.getBytes());

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) { // 연결에 성공했을 경우
                Log.e("LoginAsync","연결성공");

                // 데이터 수신
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;
                StringBuilder json = new StringBuilder();

                // 나머지를 한줄씩 읽어서 json에 저장
                while ((temp = br.readLine()) != null) {
                    json.append(temp);
                }
                Log.e("LoginAsync", "json="+json.toString());

                // JSONArray형태로 데이터 저장
                JSONArray array = new JSONArray(json.toString());

                // 첫 데이터는 결과정보
                JSONObject jsonObject = (JSONObject) array.get(0);
                String result = ((String) jsonObject.get("result"));

                // 로그인에 성공하면
                if(result.equals("success")) {
                    Log.e("LoginAsync","로그인 성공");
                    User user = new User();
                    // 유저 아이디 저장
                    user.setId(params[0]);

                    /*// 친구 리스트 만들기
                    List<User> list = new ArrayList<>();
                    for (int i = 1; i < array.length(); i++) {
                        jsonObject = (JSONObject) array.get(i);

                        User member = new User();
                        member.setId((String) jsonObject.get("id"));
                        member.setName((String) jsonObject.get("name"));

                        list.add(member);
                    }
                    user.setFreinds(list);*/

                    return user;
                }
                else
                    return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
    protected void onPostExecute(User params) {
        super.onPostExecute(params);
        dialog.dismiss(); // 프로그래스바 종료

        if(params == null){
            Toast.makeText(activity, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", params);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }
    }
}
