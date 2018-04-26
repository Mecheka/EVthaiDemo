package com.evthai.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.evthai.R;
import com.evthai.manager.http.HttpManager;
import com.evthai.model.InfoStationModel;
import com.evthai.model.LocationModel;
import com.evthai.model.StationColaction;
import com.evthai.view.ItemMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapLocationFragment extends Fragment implements View.OnClickListener,
        OnMapReadyCallback {

    private RelativeLayout mapLayout;
    private ItemMarker itemHome;
    private ItemMarker itemProfile;
    private GoogleMap mMap;
    private StationColaction stationDao;
    private ArrayList<LocationModel> locationList = new ArrayList<>();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    MarkerOptions markerOptions;

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

        // Set Text
        itemHome.setTvMenu("HOME");
        itemProfile.setTvMenu("Profile");

        // Set OnClick
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);

        initMap(rootView, savedInstanceState);

    }

    private void initData() {

        Call<StationColaction> call = HttpManager.getInstance().getService().loadStation();
        call.enqueue(new Callback<StationColaction>() {
            @Override
            public void onResponse(Call<StationColaction> call, Response<StationColaction> response) {
                if (response.isSuccessful()) {
                    stationDao = response.body();
                    locationList.clear();
                    for (InfoStationModel infos : stationDao.getIntosList()) {
                        LocationModel locationModel = infos.getLocation();
                        locationList.add(locationModel);
                        Toast.makeText(getActivity(), "lat " + locationModel.getLat() + " lng " + locationModel.getLng(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StationColaction> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

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

        buildMarkerRatrofit();

    }

    private void buildMarkerRatrofit() {

        Call<StationColaction> call = HttpManager.getInstance().getService().loadStation();
        call.enqueue(new Callback<StationColaction>() {
            @Override
            public void onResponse(Call<StationColaction> call, Response<StationColaction> response) {
                if (response.isSuccessful()) {
                    try {

                        mMap.clear();
                        stationDao = response.body();
                        for (InfoStationModel item : stationDao.getIntosList()) {
                            double lat = Double.parseDouble(item.getLocation().getLat());
                            double lng = Double.parseDouble(item.getLocation().getLng());
                            String stationName = item.getLocation().getStation();
                            latLngs.add(new LatLng(lat, lng));
                        }
                        Marker[] allMarker = new Marker[latLngs.size()];
                        for (int i = 0; i < latLngs.size(); i++) {
                            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLngs.get(i).latitude, latLngs.get(i).longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maker_ev)));
                        }

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(13.8513962850, 100.6877681210)));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itemHome:
                break;
            case R.id.itemProfile:
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.contentContainer, new UpdateProfileFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

}
