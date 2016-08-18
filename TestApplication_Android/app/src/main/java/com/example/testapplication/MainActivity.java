
package com.example.testapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testapplication.MainActiviy_Screen.DrawerAdapter;
import com.example.testapplication.MainActiviy_Screen.FriendManagerScreen;
import com.example.testapplication.MainActiviy_Screen.LogoutAsyncTask;
import com.example.testapplication.MainActiviy_Screen.MapScreenAsyncTask;
import com.example.testapplication.MainActiviy_Screen.ParticipantFindAsyncTask;
import com.example.testapplication.MainActiviy_Screen.RoomManagerScreen;
import com.example.testapplication.MainActiviy_Screen.SendGPSAsyncTask;
import com.example.testapplication.MainActiviy_Screen.asdwqdmm;

import java.util.ArrayList;
import java.util.List;

//import com.example.testapplication.MainActiviy_Screen.RoomManagerScreen;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //public static String webServerUrl = "http://192.168.0.231:8090/12.Come"; // 231
    //public static String webServerUrl = "http://192.168.0.228:8090/12.Come"; // 231
    public static String webServerUrl = "http://192.168.0.231:8090/12.Come"; // 231

    public static User myuserInfo = null;
    public static List<Room> roomList = null;

    LocationManager manager;
    FriendManagerScreen friendManagerScreen;
    RoomManagerScreen roomManagerScreen;

    MapScreenAsyncTask mapScreen;
    static SendGPSAsyncTask serviceTask;

    @Override
    protected void onPause() {

        Log.e("MainActivity", "onPause()");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("MainActivity", "onSaveInstanceState()");
    }

    @Override
    protected void onDestroy() {
        Log.e("MainActivity", "onDestroy()");

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.e("MainActivity", "onStop()");
/*
        LogoutAsyncTask logoutAsyncTask = new LogoutAsyncTask();
        logoutAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());
        */
        super.onStop();
    }

    static ListView listView;
    static DrawerAdapter adapter;

    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("지도 보기"); // title수정

        Intent intent = getIntent();
        myuserInfo = (User)intent.getExtras().getSerializable("user");
        roomList =  new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        asdwqdmm as = new asdwqdmm(toolbar,MainActivity.this);
        as.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textHeader = (TextView)navigationView.getHeaderView(0).findViewById(R.id.textHeader);
        textHeader.setText("ID : " +myuserInfo.getId());

        //MapScreen
        mapScreen = new MapScreenAsyncTask(MainActivity.this);
        mapScreen.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // FreindManager
        friendManagerScreen = new FriendManagerScreen(MainActivity.this);

        // RoomManager
        roomManagerScreen = new RoomManagerScreen(MainActivity.this);

        ListView listView = (ListView) findViewById(R.id.drawerListView);
        listView.setFocusable(true);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("로그인 화면으로 이동");
            alertDialog.setMessage("로그인 화면으로 이동하시겠습니까?");

            // OK 를 누르게 되면 설정창으로 이동합니다.
            alertDialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                mapScreen.SetMapUsed(false);
                                mapScreen.cancel(true);
                                LogoutAsyncTask logoutAsyncTask = new LogoutAsyncTask();
                                logoutAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.myuserInfo.getId());

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                // Cancle 하면 종료 합니다.
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            // Handle the camera action
            setTitle("지도 보기");
            mapScreen.Change_Visibility(true);
            friendManagerScreen.Change_Visibility(false);
            roomManagerScreen.Change_Visibility(false);

        } else if (id == R.id.nav_add_friends) {
            setTitle("친구 찾기");
            mapScreen.Change_Visibility(false);
            friendManagerScreen.updateFriendList();
            friendManagerScreen.Change_Visibility(true);
            roomManagerScreen.Change_Visibility(false);

        } else if (id == R.id.nav_room){
            setTitle("방 관리");
            mapScreen.Change_Visibility(false);
            friendManagerScreen.Change_Visibility(false);
            roomManagerScreen.updateRoomList();
            roomManagerScreen.Change_Visibility(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void sendForUpdate_GPS(Double latitude, Double longitude)
    {
        Log.e("MainActivity", "sendForUpdate_GPS() -"+ latitude+ ","+  longitude);
        serviceTask = new SendGPSAsyncTask();
        serviceTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myuserInfo.getId(), Double.toString(latitude), Double.toString(longitude));

    }

    public static void updateRoomParticipate(String room_id, List<User> participateList)
    {
        for( Room room :roomList)
        {
            if( room.getRoomNum() == Integer.parseInt(room_id))
            {
                room.setParticipantList(participateList);
            }
        }
    }

    public static void getParticipateInfo(int room_id){
        ParticipantFindAsyncTask findAsyncTask = new ParticipantFindAsyncTask(null);
        findAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.toString(room_id));
    }
}