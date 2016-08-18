package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testapplication.MainActivity;
import com.example.testapplication.R;
import com.example.testapplication.Room;
import com.example.testapplication.User;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;


public class MapScreenAsyncTask extends AsyncTask<Void,Void,Void> implements MapView.POIItemEventListener {
    // 맵 업데이트시, 맵의 초점은 딱 한번만 변화하도록
    static boolean isFirstFocus = false;

    Activity activity;
    MapView mapView;
    RelativeLayout map_layout = null;
    boolean isMapUsed = false;
    boolean isUpdateBtnPressed =false;
    GpsInformation gps;
    FloatingActionButton fab; // gps update Button


    private double latitude;
    private double longitude;

    public MapScreenAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        gps = new GpsInformation(activity);

        // MapView 선언
        mapView = new MapView(activity);
        mapView.setDaumMapApiKey("84a2a5d87f2f9951ba9705f1a4c58d3c");

        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        map_layout = (RelativeLayout) activity.findViewById(R.id.map_layout);
        map_layout.addView(mapView);
        isMapUsed = true;

        mapView.setPOIItemEventListener(this);

        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isUpdateBtnPressed = true;
                Snackbar.make(view, "Updated GPS", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        mapView.removeAllPOIItems();

        List<Room> room_list = MainActivity.roomList;

        // Marker 생성 -- 본인
        MapPOIItem myMarker = new MapPOIItem();
        myMarker.setItemName("hello");
        myMarker.setTag(0);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);


        myMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        myMarker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.

        synchronized (this)
        {

        // Marker 생성 -- 친구
        List<MapPOIItem> markerList= new ArrayList<MapPOIItem>();

            for(Room room : room_list)
            {
                if(room.isChecked())
                {
                    List<User> parti_list = room.getParticipantList();
                    for(User user : parti_list)
                    {
                        if(user.getId().equals( MainActivity.myuserInfo.getId()))
                            continue;
                        MapPOIItem marker2 = new MapPOIItem();
                        marker2.setItemName(user.getId());
                        marker2.setTag(0);
                        marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(user.getGps_latitude(), user.getGps_longitude()));
                        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        markerList.add(marker2);
                    }
                }
            }

        mapView.addPOIItem(myMarker);
        for( MapPOIItem marker : markerList)
            mapView.addPOIItem(marker);
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {

        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            if(gps.isGetStatusChanged())
                Log.e("MapScreenAsyncTask","isGetStatusChanged");
            else
                Log.e("MapScreenAsyncTask","isGetStatusUnChanged");

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            MainActivity.myuserInfo.setGps_latitude(latitude);
            MainActivity.myuserInfo.setGps_longitude(longitude);

            Log.e("MapScreenAsyncTask",latitude+"");
            Log.e("MapScreenAsyncTask",longitude+"");
        } else {
            // GPS 를 사용할수 없으므로
            //gps.
        }

        while(true)
        {
            if( isMapUsed)
            {
                if(gps.isGetStatusChanged() && isUpdateBtnPressed)
                {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Log.e("MapScreenAsyncTask","isGetStatusUnChanged");

                    MainActivity.sendForUpdate_GPS(latitude,longitude);

                    publishProgress();
                    isUpdateBtnPressed= false;
                }
            }
            try {
                // 5초 간격으로 현재 정보 가져옴.
                Log.e("MapScreenAsyncTask", " Map 살아있음");

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isCancelled())
                break;
        }
        Log.e("MapScreenAsyncTask", " Map 종료");

        return null;
    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        switch (mapPOIItem.getItemName())
        {
            case "hi":
                Log.e("MapScreenAsyncTask", "onPOIItemSelected : hi");
                break;

            case "hello":
                Log.e("MapScreenAsyncTask", "onPOIItemSelected : hello");
                break;
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        List<User> friendList = MainActivity.myuserInfo.getFreinds();

        for(User friend : friendList)
        {
            if(mapPOIItem.getItemName().equals(friend.getId()))
            {
                // start(lat,lon)
                double myLat = MainActivity.myuserInfo.getGps_latitude();
                double myLon = MainActivity.myuserInfo.getGps_longitude();

                // dest(lat,lon)
                double friendLat = friend.getGps_latitude();
                double friendLon = friend.getGps_longitude();

                findRouteBydaumMap(myLat, myLon, friendLat, friendLon);
            }
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.e("MapScreenAsyncTask", "onCalloutBalloonOfPOIItemTouched");
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.e("MapScreenAsyncTask", "onDraggablePOIItemMoved");
    }

    public void findRouteBydaumMap(double myLat, double myLon, double friendLat, double friendLon)
    {
        Log.e("MapScreenAsyncTask","findRouteBydaumMap() : ");

        PackageManager pm = activity.getPackageManager();
        PackageInfo pi;

        try {
            String strAppPackage = "net.daum.android.map";
            pi = pm.getPackageInfo(strAppPackage,  PackageManager.GET_ACTIVITIES);

            String url = "daummaps://route?sp="+myLat+","+myLon+"&ep="+friendLat+","+friendLon+"&by=FOOT";
            Log.e("MapScreenAsyncTask", url);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        }
        catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(activity.getApplicationContext(), "Daum Map이 설치되지 않았습니다.", Toast.LENGTH_SHORT).show();

            String install_url = "market://details?id=net.daum.android.map";
            Intent install_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(install_url));
            activity.startActivity(install_intent);
        }
    }

    public void Change_Visibility(boolean status)
    {
        if( status ) {
            SetMapUsed(true);
            map_layout.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }
        else {
            SetMapUsed(false);
            fab.setVisibility(View.GONE);
            map_layout.setVisibility(View.GONE);
        }
    }

    public void SetMapUsed(boolean status)
    {
        if( status ) {
            isMapUsed = true;
        }else
            isMapUsed = false;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
