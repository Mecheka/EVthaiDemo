package com.evthai.fragment.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.listviewadapter.MetterAdapter;
import com.evthai.adapter.recycviewadapter.NozzleTypeRecyclerAdapter;
import com.evthai.model.LocationModel;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment extends Fragment {

    private LocationModel location;

    public StationFragment() {
        // Required empty public constructor
    }

    public static StationFragment newInstance(LocationModel location){
        StationFragment fragment = new StationFragment();
        Bundle args = new Bundle();
        args.putParcelable("station", Parcels.wrap(location));
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
        TextView tvDialogAddress = dialogView.findViewById(R.id.tvAddress);
        TextView tvDialogBrand = dialogView.findViewById(R.id.tvBrand);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        ListView dialogListView = dialogView.findViewById(R.id.listView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //tvDialogId.setText(location.getStationName());
        //tvDialogAddress.setText(location.getAddress());
        //tvDialogBrand.setText(location.getBrand());

        // RecycLerViewAdapter
        /**NozzleTypeRecyclerAdapter adapter = new NozzleTypeRecyclerAdapter(getActivity(), location.getNozaleList());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        // ListViewAdapter
       /** MetterAdapter listViewAdapter = new MetterAdapter(getActivity(), location.getMetterList());
        dialogListView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();*/
    }

}
