package com.example.testapplication.MainActiviy_Screen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class GpsInformation extends Service {
    private Context context;

    // 현재 GPS 사용유무
    private boolean isGPSEnabled = false;

    // 네트워크 사용유무
    private boolean isNetworkEnabled = false;

    // GPS 상태값
    private boolean isGetLocation = false;

    // GPS 상태 변화 값
    private boolean isChangedLocation = false;

    double lat; // 위도
    double lon; // 경도

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    private LocationListener locationListener;

    public GpsInformation(){}

    public GpsInformation(Context context) {
        this.context = context;
        getLocation();

    }

    public void getLocation() {

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        // GPS 정보 가져오기
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // 현재 네트워크 상태 값 알아오기
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        Log.e("GpsInformation", "isGPSEnabled="+ isGPSEnabled);
        Log.e("GpsInformation", "isNetworkEnabled="+ isNetworkEnabled);

        if(isGPSEnabled && isNetworkEnabled )
            isGetLocation = true;
        else
            showSettingsAlert();

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                isChangedLocation = true;

                Log.e("GpsInformation","latitude: "+ lat +", longitude: "+ lon);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.e("GpsInformation","onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                Log.e("GpsInformation","onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                Log.e("GpsInformation","onProviderDisabled");
            }
        };

        try{

            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            if (lastKnownLocation != null) {
                lon = lastKnownLocation.getLongitude();
                lat = lastKnownLocation.getLatitude();
                Log.e("GpsInformation", "(수동) longtitude=" + lon + ", latitude=" + lat);
            }

        }catch (SecurityException e) {

        }

    }

    /**
     * GPS 종료
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                locationManager.removeUpdates(locationListener);

                return;
            }
        }
    }

    /**
     * 위도값을 가져옵니다.
     * */
    public double getLatitude(){

        Log.e("GpsInformation", "getLatitude() : " + lat);
        return lat;
    }

    /**
     * 경도값을 가져옵니다.
     * */
    public double getLongitude(){

        Log.e("GpsInformation", "getLongitude() : " + lon);

        return lon;
    }

    /**
     * GPS 나 wife 정보가 켜져있는지 확인합니다.
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    public boolean isGetStatusChanged() { return this.isChangedLocation; }

    /**
     * GPS 정보를 가져오지 못했을때
     * 설정값으로 갈지 물어보는 alert 창
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
