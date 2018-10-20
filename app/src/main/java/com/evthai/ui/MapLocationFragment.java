package com.evthai.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evthai.R;
import com.evthai.activity.LoginAndRegisterActivity;
import com.evthai.manager.http.HttpManager;
import com.evthai.model.stations.Station;
import com.evthai.model.stations.StationResponce;
import com.evthai.ui.station.StationDetailActivity;
import com.evthai.view.ItemMarker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapLocationFragment extends Fragment implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int MOD_ZOOM_IN = 101;
    private static final int MOD_ZOOM_OUT = 102;
    private static final int MOD_DEFULT = 100;

    private static final String SPG_TOKEN = "tokenperferences";
    private static final String TOKEN = "token";
    private static final int REQEUST_LOCATION = 701;

    private RelativeLayout mapLayout;
    private ItemMarker itemHome;
    private ItemMarker itemProfile;
    private ItemMarker itemLogout;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLoction;
    private LocationCallback mLocationCallBack;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private StationResponce stationDao;
    private Marker marker;
    private Map<Marker, Integer> markerOrderNumbers = new HashMap<>();

    public MapLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map_location, container, false);
        initInstance(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkPermission()) {
            requestPermission();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            try {
                mFusedLoction.removeLocationUpdates(mLocationCallBack);

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQEUST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private void initInstance(View rootView, Bundle savedInstanceState) {

        // Find View
        mapLayout = rootView.findViewById(R.id.mapLayout);
        itemHome = rootView.findViewById(R.id.itemHome);
        itemProfile = rootView.findViewById(R.id.itemProfile);
        itemLogout = rootView.findViewById(R.id.itemLogout);

        try {
            mFusedLoction = LocationServices.getFusedLocationProviderClient(getActivity());
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(5000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(getActivity())) {
                Log.e("keshav","Gps already enabled");
                Toast.makeText(getActivity(),"Gps not enabled",Toast.LENGTH_SHORT).show();
                enableLoc();
            }else{
                Log.e("keshav","Gps already enabled");
                Toast.makeText(getActivity(),"Gps already enabled",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set Text
        itemHome.setImgIcon(R.drawable.ic_home);
        itemHome.setTvMenu("Home");

        itemProfile.setImgIcon(R.drawable.ic_profile);
        itemProfile.setTvMenu("Profile");

        itemLogout.setImgIcon(R.drawable.ic_logout);
        itemLogout.setTvMenu("Logout");

        // Set OnClick
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        initMap(rootView, savedInstanceState);

    }

    private void initMap(View rootView, Bundle savedInstanceState) {

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        buildStationMarkerRetrofit(MOD_DEFULT);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(13.8513962850, 100.6877681210)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(6));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                final float zoom = mMap.getCameraPosition().zoom;
                Runnable myRun = new Runnable() {
                    @Override
                    public void run() {
                        while (zoom >= 10) {
                            Log.d("Zoom value in :", String.valueOf(zoom));
                            buildStationMarkerRetrofit(MOD_ZOOM_IN);
                            break;
                        }
                        while (zoom < 10) {
                            Log.d("Zoom value in :", String.valueOf(zoom));
                            buildStationMarkerRetrofit(MOD_ZOOM_OUT);
                            break;
                        }
                    }
                };

                Thread myThread = new Thread(myRun);
                myThread.start();
            }
        });


    }

//    private void buildMarkerRatrofit(final int mod) {
//
//        Call<ChargerResponce> call = HttpManager.getInstance().getService().loadCharger();
//        call.enqueue(new Callback<ChargerResponce>() {
//            @Override
//            public void onResponse(Call<ChargerResponce> call, Response<ChargerResponce> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        mMap.clear();
//                        stationDao = response.body();
//                        int markerIndex = 0;
//                        for (InfoStationModel item : stationDao.getIntosList()) {
//                            String strLat = item.getLocation().getLat();
//                            String strLng = item.getLocation().getLng();
//                            double lat = Double.parseDouble(strLat);
//                            double lng = Double.parseDouble(strLng);
//                            String stationName = item.getDetail().getName();
//
//                            /**BitmapDrawable bitmapDraw = (BitmapDrawable) getResources().getDrawable(checkStatusId(item.getStatusId()));
//                             Bitmap bitmap = bitmapDraw.getBitmap();
//                             Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
//
//                             marker = mMap.addMarker(new MarkerOptions()
//                             .position(new LatLng(lat, lng))
//                             .title(stationName)
//                             .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));*/
//
//                            if (mod == MOD_DEFULT) {
//                                marker = mMap.addMarker(createMarkerSmallMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
//                            } else if (mod == MOD_ZOOM_IN) {
//                                marker = mMap.addMarker(createMarkerLargeMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
//                            } else {
//                                marker = mMap.addMarker(createMarkerSmallMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
//                            }
//                            markerOrderNumbers.put(marker, markerIndex);
//                            markerIndex++;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ChargerResponce> call, Throwable t) {
//
//            }
//        });
//
//    }

    private void buildStationMarkerRetrofit(final int mod) {

        Call<StationResponce> callStation = HttpManager.getInstance().getService().loadStation();
        callStation.enqueue(new Callback<StationResponce>() {
            @Override
            public void onResponse(Call<StationResponce> call, Response<StationResponce> response) {

                if (response.isSuccessful()) {
                    try {
                        mMap.clear();
                        stationDao = response.body();
                        int markerIndex = 0;
                        for (Station item : stationDao.getStations()) {
                            String strLat = item.getLat();
                            String strLng = item.getLon();
                            double lat = Double.parseDouble(strLat);
                            double lng = Double.parseDouble(strLng);
                            String stationName = item.getName();

                            /**BitmapDrawable bitmapDraw = (BitmapDrawable) getResources().getDrawable(checkStatusId(item.getStatusId()));
                             Bitmap bitmap = bitmapDraw.getBitmap();
                             Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);

                             marker = mMap.addMarker(new MarkerOptions()
                             .position(new LatLng(lat, lng))
                             .title(stationName)
                             .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));*/

                            if (mod == MOD_DEFULT) {
                                marker = mMap.addMarker(createMarkerLargeMarker(getActivity(), new LatLng(lat, lng),
                                        stationName, Integer.parseInt(item.getStatus()), mod));
                            } else if (mod == MOD_ZOOM_IN) {
                                marker = mMap.addMarker(createMarkerLargeMarker(getActivity(), new LatLng(lat, lng), stationName,
                                        Integer.parseInt(item.getStatus()), mod));
                            } else {
                                marker = mMap.addMarker(createMarkerLargeMarker(getActivity(), new LatLng(lat, lng), stationName,
                                        Integer.parseInt(item.getStatus()), mod));
                            }
                            markerOrderNumbers.put(marker, markerIndex);
                            markerIndex++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StationResponce> call, Throwable t) {

            }
        });
    }

    public static MarkerOptions createMarkerLargeMarker(Context context, LatLng point, String stationName, int statusId, int mod) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(point);

        int pxHeight = context.getResources().getDimensionPixelSize(R.dimen.marker_dimen100);
        int pxWidth = context.getResources().getDimensionPixelSize(R.dimen.merker_dimenwifth);
        View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_large, null);
        markerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        markerView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        markerView.buildDrawingCache();
        TextView bedNumberTextView = markerView.findViewById(R.id.tvStation);
        ImageView imgMarkerSmall = markerView.findViewById(R.id.imgMarkerSmall);
        Bitmap mDotMarkerBitmap = Bitmap.createBitmap(pxWidth, pxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDotMarkerBitmap);
        bedNumberTextView.setText(stationName);
        imgMarkerSmall.setImageResource(checkStatusIdLargeMarker(statusId, mod));
        markerView.layout(0, 0, pxWidth, pxWidth);
        markerView.draw(canvas);
        marker.icon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap));

        return marker;
    }

    private static int checkStatusIdLargeMarker(int statusId, int mod) {

        int resId = 0;

        if (statusId == 0) {
            resId = R.drawable.ic_marker_gray_80dp;
        } else if (statusId == 1) {
            resId = R.drawable.ic_marker_green80dp;
        } else if (statusId == 2) {
            resId = R.drawable.ic_marker_yellow_80dp;
        } else if (statusId == 3) {
            resId = R.drawable.ic_marker_blue_80dp;
        } else if (statusId == 4) {
            resId = R.drawable.ic_marker_orange_80dp;
        } else if (statusId == 5) {
            resId = R.drawable.ic_marker_oocupled_80dp;
        } else if (statusId == 6) {
            resId = R.drawable.ic_marker_black_80dp;
        } else {
            resId = R.drawable.ic_marker_red80dp;
        }

        return resId;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itemHome:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(13.8513962850, 100.6877681210)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(6));
                break;
            case R.id.itemProfile:
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.contentContainer, new UpdateProfileFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.itemLogout:
                SharedPreferences tokenPreferences = getActivity().getSharedPreferences(SPG_TOKEN, Context.MODE_PRIVATE);
                tokenPreferences.edit().clear().apply();
                startActivity(new Intent(getActivity(), LoginAndRegisterActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        final Integer index = markerOrderNumbers.get(marker);
        Log.e("Maeker Index : ", index + "");
        if (!checkPermission()) {
            mFusedLoction.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    Station station = stationDao.getStations().get(index);

                    double kelomate = CalculationByDistance(new LatLng(location.getLatitude(), location.getLongitude()),
                            new LatLng(Double.parseDouble(station.getLat()),Double.parseDouble(station.getLon())));

                    Intent intent = new Intent(getActivity(), StationDetailActivity.class);
                    intent.putExtra("station", Parcels.wrap(station));
                    intent.putExtra("kelomate", String.valueOf(kelomate));
                    startActivity(intent);
                }
            });
        }
        return true;
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQEUST_LOCATION);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQEUST_LOCATION);
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            mGoogleApiClient.connect();

            if (mLocationRequest != null) {

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest);

                builder.setAlwaysShow(true);

                PendingResult<LocationSettingsResult> result =
                        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    status.startResolutionForResult(getActivity(), REQEUST_LOCATION);

                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                                break;
                        }
                    }
                });
            }
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
