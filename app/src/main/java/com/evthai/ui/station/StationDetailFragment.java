package com.evthai.ui.station;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.evthai.R;
import com.evthai.manager.http.HttpManager;
import com.evthai.model.charger.Charger;
import com.evthai.model.charger.ChargerRequests;
import com.evthai.model.charger.ChargerResponce;
import com.evthai.model.stations.Station;
import com.evthai.utill.Constance;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationDetailFragment extends Fragment {

    private ImageView imageView;
    private TextView tvOwner, tvType, tvStatus;
    private ImageView icGoogleMap;
    private TextView tvDistance;
    private RecyclerView recyclerView;
    private ChargerAdapter adapter;

    private Station stationDao;
    private String kelomate;
    private ChargerResponce chargerDao;

    public StationDetailFragment() {
        // Required empty public constructor
    }

    public static StationDetailFragment newInstance(Station station) {
        StationDetailFragment fragment = new StationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("station", Parcels.wrap(station));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stationDao = Parcels.unwrap(getArguments().getParcelable("station"));
        kelomate = getActivity().getIntent().getStringExtra("kelomate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_station_detail, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        imageView = rootView.findViewById(R.id.imgView);
        tvOwner = rootView.findViewById(R.id.tvOwner);
        tvType = rootView.findViewById(R.id.tvType);
        tvStatus = rootView.findViewById(R.id.tvStatus);
        icGoogleMap = rootView.findViewById(R.id.googleMapIcon);
        tvDistance = rootView.findViewById(R.id.tvDistance);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        setImageView(stationDao.getImages().get(0).getUrl());
        tvOwner.setText(stationDao.getOwner());
        tvType.setText(stationDao.getType());
        tvStatus.setText(getStatus(Integer.parseInt(stationDao.getStatus())));
        icGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGoogleMap();
            }
        });
        tvDistance.setText(getKolomateText(kelomate));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        callChargerApi();
    }

    /****************
     * function zone *
     * **************/
    private void setImageView(String url) {

        Glide.with(this)
                .load(Constance.BASE_URL + url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    private String getStatus(int status) {

        String strStatus;

        if (status == 0) {
            strStatus = "ยังไม่ติดตั้ง";

        } else if (status == 1) {
            strStatus = "พร้อมใช้งาน";

        } else if (status == 2) {
            strStatus = "ถูกจอง";

        } else if (status == 3) {
            strStatus = "กำลังอัดประจุ";

        } else if (status == 4) {
            strStatus = "อัดประจุเสร็จ";

        } else if (status == 5) {
            strStatus = "อัดประจุเสร็จนานแล้ว";

        } else if (status == 6) {
            strStatus = "มีรูปประแจ บำรุงรักษา";

        } else {
            strStatus = "ขาดการติดต่อ";

        }

        return strStatus;
    }

    private void navigateToGoogleMap() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + stationDao.getLat() + "," + stationDao.getLon());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private String getKolomateText(String text) {
        String strKelomate;
        int startIndex = text.lastIndexOf(".");
        strKelomate = text.substring(0, startIndex);
        return strKelomate;
    }

    private void callChargerApi(){
        Call<ChargerResponce> callCharger = HttpManager.getInstance().getService().loadCharger(new ChargerRequests(
                String.valueOf(stationDao.getStationId())));
        callCharger.enqueue(new Callback<ChargerResponce>() {
            @Override
            public void onResponse(Call<ChargerResponce> call, Response<ChargerResponce> response) {
                if (response.isSuccessful()){

                    chargerDao = response.body();

                    if (chargerDao != null) {
                        setUpRecyclerView(chargerDao.getChargers());
                    }
                }else {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChargerResponce> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(ArrayList<Charger> chargers){

        adapter = new ChargerAdapter(chargers);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClick(new ChargerAdapter.OnItemClick() {
            @Override
            public void onItemClick(int postion) {

            }
        });
    }
}
