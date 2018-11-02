package com.evthai.ui.dialog;


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
import android.widget.ListView;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.recycviewadapter.NozzleTypeRecyclerAdapter;
import com.evthai.model.charger.Charger;
import com.evthai.view.ItemMetterDialog;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment extends Fragment implements View.OnClickListener {

    private TextView tvStation;
    private TextView tvTitle;
    private TextView tvLastConnect;
    private Charger location;
    private ItemMetterDialog itemMetterDialog;
    private ImageView imgDirection;

    public StationFragment() {
        // Required empty public constructor
    }

    public static StationFragment newInstance(Charger charger) {
        StationFragment fragment = new StationFragment();
        Bundle args = new Bundle();
        args.putParcelable("station", Parcels.wrap(charger));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = Parcels.unwrap(getArguments().getParcelable("station"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_station, container, false);

        initInstance(dialogView);

        return dialogView;
    }

    private void initInstance(View dialogView) {

        tvStation = dialogView.findViewById(R.id.tvStation);
        tvTitle = dialogView.findViewById(R.id.tvTitle);
        TextView tvDialogAddress = dialogView.findViewById(R.id.tvAddress);
        TextView tvDialogBrand = dialogView.findViewById(R.id.tvBrand);
        tvLastConnect = dialogView.findViewById(R.id.tvLastConnect);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
//        ListView dialogListView = dialogView.findViewById(R.id.listView);
        itemMetterDialog = dialogView.findViewById(R.id.itemMetter);
        imgDirection = dialogView.findViewById(R.id.imgDirections);

        imgDirection.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        tvStation.setText(location.getDetail().getName());
        tvTitle.setText(location.getDetail().getName());
        //tvDialogId.setText(location.getStationName());
        tvDialogAddress.setText(location.getLocation().getStation());
        tvDialogBrand.setText(location.getDetail().getBrand() + " - " + location.getDetail().getModel());
        tvLastConnect.setText(location.getLastUpdate());

        setFakeData();

        // RecycLerViewAdapter
        NozzleTypeRecyclerAdapter adapter = new NozzleTypeRecyclerAdapter(getActivity(), location.getDetail().getConnector());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // ListViewAdapter
        /** MetterAdapter listViewAdapter = new MetterAdapter(getActivity(), location.getMetterList());
         dialogListView.setAdapter(listViewAdapter);
         listViewAdapter.notifyDataSetChanged();*/
    }

    private void setFakeData() {
        itemMetterDialog.setTvNozzle("-");
//        if (location.getStatus().equals("Active")) {
//            itemMetterDialog.setTvStatus(true);
//        } else {
//            itemMetterDialog.setTvStatus(false);
//        }
//        itemMetterDialog.setTvCardNo("-");
//        itemMetterDialog.setTvStartChart("-");
//        itemMetterDialog.setTvEndChart("-");
//        itemMetterDialog.setTvEndMetter("-");
//        itemMetterDialog.setTvUpdate(location.getLastUpdate());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgDirections:

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location.getLocation().getLat() + "," + location.getLocation().getLong());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

                break;
        }

    }
}
