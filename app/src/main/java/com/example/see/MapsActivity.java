package com.example.see;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        PlacesListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener{

    //sql
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    //구글 맵 객체
    private GoogleMap mMap;

    //커스텀 마크를 위한 객체
    LinearLayout markerView;
    TextView tvMarker;
    TextView tvLabel;

    //식당 정보창을 위한 객체
    LinearLayout restInfo;
    TextView restName;
    TextView restAddress;
    TextView restGroup;

    //지역검색 결과를 담는 리스트
    List<Place> mPlaces;

    //위치 탐색을 위한 상수
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    //boolean needRequest = false;

    // 위치 접근 권한
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    //현재 위치
    Location mCurrentLocation;
    LatLng mCurrentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    //주변 식당 찾기 버튼 클릭 상태를 확인하기 위한 변수
    private int movedCameraState = 0;

    //식당 마커 리스트
    List<Marker> markerList = null;

    //전체 레이아웃
    private View mLayout;

    //참여버튼
    private Button button_join;


    String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        helper = new MySQLiteOpenHelper(MapsActivity.this, "restaurant.db", null, 1);

        //일단 임의로 설정
        insert("ChIJFwvgnfqpfDUR5IlhmJ088WU", 4, 0);
        insert("ChIJWQdpaqCofDURWLT-6DDWo7I", 8, 0);
        insert("ChIJWQdpaqCofDURWbMIMdaOjJg", 5, 0);
        insert("ChIJWQdpaqCofDURKBe0tsY2JKM", 4, 0);
        insert("ChIJWQdpaqCofDURkTJGlQxLJVI", 2, 0);
        insert("ChIJWQdpaqCofDURirNpdHuTZpM", 4, 0);
        insert("ChIJWQdpaqCofDURuu0w6oph7rg", 2, 0);
        insert("ChIJWQdpaqCofDURX1YmFfS3J-s", 5, 0);
        insert("ChIJuwcSjZ-ofDURDoYru6lZR9U", 5, 0);
        insert("ChIJuwcSjZ-ofDURyUP6NiiCfwU", 5, 0);
        insert("ChIJxfSbuompfDURCne8wMu-cRs", 5, 0);
        insert("ChIJAy6v7J-ofDURgrgl8SMgGNc", 5, 0);

        insert("ChIJN76tFKCofDURCCmCS4Ki5WA", 4, 0);
        insert("ChIJc0QgPKCofDURtlM6UdX9nEc", 6, 0);
        insert("ChIJ-bZtIqCofDURxE6RGlIxtLo", 5, 0);
        insert("ChIJ9VGVG5CpfDURuKlOERqL04E", 4, 0);
        insert("ChIJnWG00BmpfDURu1tHNLj1F4o", 2, 0);
        insert("ChIJz_vSJKCofDUR1Oins8LRBXE", 4, 0);
        insert("ChIJYfnSJKCofDURTbp3jC8SS0M", 2, 0);
        insert("ChIJEWQwAHypfDURHZjw1EgBeqQ", 5, 0);
        insert("ChIJtYEkPKCofDURUhY63EKzdl4", 5, 0);
        insert("ChIJze3nIKCofDUR8nw9zokc_Bk", 4, 0);
        insert("ChIJl4hvIKCofDURxKnfzWqX6ok", 5, 0);
        insert("ChIJl4hvIKCofDURcFjhejLgQR4", 5, 0);

        insert("ChIJl4hvIKCofDURqyxaLzUCE8w", 4, 0);
        insert("ChIJGdaK7TGpfDUReYOg5WFl1ZM", 5, 0);
        insert("ChIJ3_DLP6CofDURJnuZw7Kfwg4", 5, 0);
        insert("ChIJVfn_MKCofDURuWUhXbWTEGE", 3, 0);
        insert("ChIJUVNrFaCofDUR6XhTr_b-Qso", 5, 0);
        insert("ChIJUVNrFaCofDURXzK0-E6Qraw", 5, 0);
        insert("ChIJUVNrFaCofDURiRPHd12sUyM", 5, 0);
        insert("ChIJN6NHFaCofDURnNxIn68IEik", 5, 0);

        button_join = (Button)findViewById(R.id.join);

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//정원 추가
                update(placeId, getCapacityGroupPeople(placeId), getNowGroupPeople(placeId) + 1);
            }


        });


        //타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        restInfo = (LinearLayout)findViewById(R.id.rest_info);
        restInfo.setVisibility(View.GONE);
        restName = (TextView)findViewById(R.id.rest_name);
        restAddress = (TextView)findViewById(R.id.rest_address);
        restGroup = (TextView)findViewById(R.id.rest_group);

        markerList = new ArrayList<Marker>();

        mLayout = findViewById(R.id.layout_maps);

        //위치 값 받아오는 설정 객체 : 정확도, 주기, 최소주기
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        //설정 set
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        //FusedLocationProviderClient 객체 생성
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //xml fragment 연결
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //onMapReady 호출
        mapFragment.getMapAsync(this);

        Button btnReservTime= (Button) findViewById(R.id.reserveTime);
        btnReservTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), reservation.class);
                startActivity(intent);
            }
        });
    }


    //맵 초기화
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        //초기위치로 이동 : 가천대
        setDefaultLocation();

        //줌 버튼 표시
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //커스텀 마커 추가
        setCustomMarkerView();

        //마커 클릭 리스너 설정
        mMap.setOnMarkerClickListener(this);

        //지도클릭 리스너 설정
        mMap.setOnMapClickListener(this);
    }


    //지도 초기 위치 설정
    public void setDefaultLocation() {
        //가천대 IT 대학 초기 위치로 설정
        LatLng DEFAULT_LOCATION = new LatLng(37.451055, 127.127145);
        //초기 위치, 줌레벨 18로 카메라 이동
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 18));
    }


    //현재 위치 탐색 후 실행 되는 콜백
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);

                mCurrentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());

                //주변 식당 찾기 버튼을 누를 시에만 실행
                if ( movedCameraState == 0 ) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition, 19.0f));
                    //현재 위치 주변 음식점 검색
                    showPlaceInformation(mCurrentPosition);
                    movedCameraState = 1;
                }

                mCurrentLocation = location;
            }


        }

    };



    //위치 업데이트 시작
    private void startLocationUpdates() {

        //위치 서비스가 꺼져있으면, 활성화 메시지를 띄운다.
        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();

        } else {

            //권한 체크 : 필수적 코드라 중복됨
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //위치 업데이트
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            //위치 레이어 활성화
            mMap.setMyLocationEnabled(true);

        }

    }


    //gps 또는 네트워크가 위치를 제공 가능한지 확인
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    //권한 체크
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }
        return false;
    }


    //GPS가 비활성화 일때 설정 메시지를 띄운다.
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("GPS 비활설화");
        builder.setMessage("앱을 사용하기 위해서는 GPS 활성이 필요합니다.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    public void onClickButtonMaps(View view){
        movedCameraState = 0;
        if (mPlaces != null)
            mPlaces.clear();
        //위치 접근 권한 처리
        //1. 위치 접근 권한이 있다면,
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

            startLocationUpdates();
        //2. 권한이 없으면, 권한 요청
        }else {
            ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE);
        }
    }


    //여기서부터는 지역검색 관련 함수입니다.
    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    //지역 검색이 성공하면 호출됨.
    @Override
    public void onPlacesSuccess(final List<Place> places) {
        if ( mPlaces == null )
            mPlaces = places;
        else
            mPlaces.addAll(places);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {

                    LatLng latLng
                            = new LatLng(place.getLatitude()
                            , place.getLongitude());

                    //현재 그룹 인원 마커에 표시
                    tvMarker.setText( Integer.toString( getNowGroupPeople(place.getPlaceId()) ) +
                            "/" +  Integer.toString( getCapacityGroupPeople(place.getPlaceId()) ));
                    //식당이름 표시
                    tvLabel.setText( place.getName() );

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap
                            (generateBitmap(markerView)));

                    Marker item = mMap.addMarker(markerOptions);
                    //마커 고유의 속성을 placeid로 설정
                    item.setTag( place.getPlaceId() );

                    markerList.add(item);

                }

                    HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(markerList);
                markerList.clear();
                markerList.addAll(hashSet);

            }
        });
    }

    @Override
    public void onPlacesFinished() {

    }


    //지역검색, 검색이 완료되면 onPlacesSuccess() 콜백이 호출됩니다.
    public void showPlaceInformation(LatLng location)
    {
        mMap.clear();

        if (markerList != null)
            markerList.clear();

        new NRPlaces.Builder()
                .listener(MapsActivity.this)
                .key("AIzaSyCG0mHE7HUhX13qrvTNMeJVpFV_DuTbY9o")  //API 키
                .latlng(location.latitude, location.longitude)  //현재위치
                .radius(150) //검색범위 : 150미터
                .type(PlaceType.RESTAURANT) //카테고리 : 음식점
                .build()
                .execute();
    }


    //커스텀 마커 추가
    public void setCustomMarkerView() {
        markerView = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.marker, null);
        tvMarker = (TextView) markerView.findViewById(R.id.tv_marker);
        tvLabel = (TextView) markerView.findViewById(R.id.tv_label);
    }


    //view 를 bitmap으로 변환
    private Bitmap generateBitmap(LinearLayout view)
    {
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        return bitmap;
    }


    //각 식당 별 그룹인원 반환 : String place_id로 식당 구분
    public int getNowGroupPeople(String place_id){
        String id;
        int capacity;
        int now = 0;

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id, capacity,now FROM RESTAURANT WHERE id = " + "'" +place_id+ "'");

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sb.toString(),null);

        while(cursor.moveToNext()){
            id = cursor.getString(0);
            capacity = cursor.getInt(1);
            now = cursor.getInt(2);
        }

        return now;
    }

    public int getCapacityGroupPeople(String place_id){
        String id;
        int capacity = 0;
        int now = 0;

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id, capacity,now FROM RESTAURANT WHERE id = " + "'" +place_id+ "'");

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sb.toString(),null);

        while(cursor.moveToNext()){
            id = cursor.getString(0);
            capacity = cursor.getInt(1);
            now = cursor.getInt(2);
        }

        return capacity;
    }

    //마커를 클릭하면 식당별 정보가 나옵니다.
    @Override
    public boolean onMarkerClick(@NonNull final Marker marker) {
        //마커를 중심으로 카메라 이동
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

        //마커의 placeid를 찾아서, 정보 입력
        placeId = (String) marker.getTag();
        for (noman.googleplaces.Place place : mPlaces){
            if ( placeId == place.getPlaceId() ){
                restName.setText( place.getName() );
                restAddress.setText( place.getVicinity() );
                restGroup.setText( "현재 그룹 인원 : " + getNowGroupPeople( placeId ) + "명");
            }
        }

        //정보창 띄우기
        restInfo.setVisibility(View.VISIBLE);
        return false;
    }


    //지도를 클릭하면 식당 정보창을 숨깁니다.
    @Override
    public void onMapClick(@NonNull  LatLng latLng) {
        restInfo.setVisibility(View.GONE);
    }


    public void insert(String id, int capacity, int now) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("capacity", capacity);
        values.put("now", now);
        db.insert("restaurant", null, values);
    }
    public void update (String id, int capacity, int now) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("capacity", capacity);
        values.put("now", now);

        db.update("restaurant", values, "id=?", new String[]{id});
    }
    public void delete (String id) {
        db = helper.getWritableDatabase();
        db.delete("restaurant", "id=?", new String[]{id});
        Log.i("db1", id + "정상적으로 삭제 되었습니다.");
    }
    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("restaurant", null, null, null, null, null, null);
        /* query (String table, String[] columns, String selection, String[]
         * selectionArgs, String groupBy, String having, String orderBy)
         */
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            int capacity = c.getInt(c.getColumnIndex("capacity"));
            int now = c.getInt(c.getColumnIndex("now"));
        }
    }
}
