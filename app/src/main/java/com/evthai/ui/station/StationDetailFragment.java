package com.evthai.ui.station;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evthai.R;
import com.evthai.model.stations.Station;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationDetailFragment extends Fragment {

    private Station stationDao;

    public StationDetailFragment() {
        // Required empty public constructor
    }

    public static StationDetailFragment newInstance(Station station){
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


    }

}
