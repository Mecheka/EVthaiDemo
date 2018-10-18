package com.evthai.ui.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.listviewadapter.MetterHistoryAdapter;
import com.evthai.view.ItemMerkerHistory;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationHistoryFragment extends Fragment {

    private TextView tvStation;
    private ListView listView;
    private InfoStationModel location;
    private MetterHistoryAdapter adapter;
    private ItemMerkerHistory itemMerkerHistory;

    public StationHistoryFragment() {
        // Required empty public constructor
    }

    public static StationHistoryFragment newInstance(InfoStationModel location){
        StationHistoryFragment fragment = new StationHistoryFragment();
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
        View rootView = inflater.inflate(R.layout.diolog_histiory_station, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        tvStation = rootView.findViewById(R.id.tvStation);
        /**listView = rootView.findViewById(R.id.listView);
        adapter = new MetterHistoryAdapter(getActivity(), location.getMetterList());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        tvStation.setText(location.getDetail().getName());
        itemMerkerHistory = rootView.findViewById(R.id.itemMarkerHis);
        itemMerkerHistory.setTvNozzle("-");
        itemMerkerHistory.setTvCardNo("-");
        itemMerkerHistory.setTvStartChart("-");
        itemMerkerHistory.setTvEndChart("-");
        itemMerkerHistory.setTvEndMetter("-");

    }

}
