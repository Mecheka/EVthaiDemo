package com.evthai.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.activity.LoginAndRegisterActivity;
import com.evthai.fragment.dialog.MyDialogFragment;
import com.evthai.manager.http.HttpManager;
import com.evthai.model.InfoStationModel;
import com.evthai.model.StationColaction;
import com.evthai.view.ItemMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
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

    private RelativeLayout mapLayout;
    private ItemMarker itemHome;
    private ItemMarker itemProfile;
    private ItemMarker itemLogout;
    private GoogleMap mMap;
    private StationColaction stationDao;
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

    private void initInstance(View rootView, Bundle savedInstanceState) {

        // Find View
        mapLayout = rootView.findViewById(R.id.mapLayout);
        itemHome = rootView.findViewById(R.id.itemHome);
        itemProfile = rootView.findViewById(R.id.itemProfile);
        itemLogout = rootView.findViewById(R.id.itemLogout);

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

        buildMarkerRatrofit(MOD_DEFULT);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(13.8513962850, 100.6877681210)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(6));


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
                            buildMarkerRatrofit(MOD_ZOOM_IN);
                            break;
                        }
                        while (zoom < 10) {
                            Log.d("Zoom value in :", String.valueOf(zoom));
                            buildMarkerRatrofit(MOD_ZOOM_OUT);
                            break;
                        }
                    }
                };

                Thread myThread = new Thread(myRun);
                myThread.start();
            }
        });

    }

    private void buildMarkerRatrofit(final int mod) {

        Call<StationColaction> call = HttpManager.getInstance().getService().loadStation();
        call.enqueue(new Callback<StationColaction>() {
            @Override
            public void onResponse(Call<StationColaction> call, Response<StationColaction> response) {
                if (response.isSuccessful()) {
                    try {
                        mMap.clear();
                        stationDao = response.body();
                        int markerIndex = 0;
                        for (InfoStationModel item : stationDao.getIntosList()) {
                            String strLat = item.getLocation().getLat();
                            String strLng = item.getLocation().getLng();
                            double lat = Double.parseDouble(strLat);
                            double lng = Double.parseDouble(strLng);
                            String stationName = item.getDetail().getName();

                            /**BitmapDrawable bitmapDraw = (BitmapDrawable) getResources().getDrawable(checkStatusId(item.getStatusId()));
                             Bitmap bitmap = bitmapDraw.getBitmap();
                             Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);

                             marker = mMap.addMarker(new MarkerOptions()
                             .position(new LatLng(lat, lng))
                             .title(stationName)
                             .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));*/

                            if (mod == MOD_DEFULT) {
                                marker = mMap.addMarker(createMarkerSmallMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
                            } else if (mod == MOD_ZOOM_IN) {
                                marker = mMap.addMarker(createMarkerLargeMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
                            } else {
                                marker = mMap.addMarker(createMarkerSmallMarker(getActivity(), new LatLng(lat, lng), stationName, item.getStatusId(), mod));
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
            public void onFailure(Call<StationColaction> call, Throwable t) {

            }
        });

    }

    public static MarkerOptions createMarkerSmallMarker(Context context, LatLng point, String stationName, int statusId, int mod) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(point);

        int pxHeight = context.getResources().getDimensionPixelSize(R.dimen.marker_dimen100);
        int pxWidth = context.getResources().getDimensionPixelSize(R.dimen.merker_dimenwifth);
        View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_small, null);
        markerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        markerView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        markerView.buildDrawingCache();
        TextView bedNumberTextView = markerView.findViewById(R.id.tvStation);
        ImageView imgMarkerSmall = markerView.findViewById(R.id.imgMarkerSmall);
        Bitmap mDotMarkerBitmap = Bitmap.createBitmap(pxWidth, pxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDotMarkerBitmap);
        bedNumberTextView.setText(stationName);
        imgMarkerSmall.setImageResource(checkStatusIdSmallMarker(statusId, mod));
        markerView.layout(0, 0, pxWidth, pxWidth);
        markerView.draw(canvas);
        marker.icon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap));

        return marker;
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

    private static int checkStatusIdSmallMarker(int stustId, int mod) {

        int resId = 0;

        if (stustId == 0) {
            resId = R.drawable.ic_marker_gray_60dp;
        } else if (stustId == 1) {
            resId = R.drawable.ic_marker_green60dp;
        } else if (stustId == 2) {
            resId = R.drawable.ic_marker_yellow_60dp;
        } else if (stustId == 3) {
            resId = R.drawable.ic_marker_blue_60dp;
        } else if (stustId == 4) {
            resId = R.drawable.ic_marker_orange_60dp;
        } else if (stustId == 5) {
            resId = R.drawable.ic_marker_oocupled_60dp;
        } else if (stustId == 6) {
            resId = R.drawable.ic_marker_black_60dp;
        } else {
            resId = R.drawable.ic_marker_red60dp;
        }

        return resId;
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

        Integer index = markerOrderNumbers.get(marker);
        Log.d("Maeker Index : ", index + "");
        DialogFragment dialogFragment = MyDialogFragment.newInstance(stationDao.getIntosList().get(index));
        dialogFragment.show(getChildFragmentManager(), "dialog");
        return true;
    }
}
