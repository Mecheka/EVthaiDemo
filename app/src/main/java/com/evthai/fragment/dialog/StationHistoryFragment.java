package com.evthai.fragment.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evthai.R;
import com.evthai.adapter.listviewadapter.MetterHistoryAdapter;
import com.evthai.model.LocationModel;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationHistoryFragment extends Fragment {

    private ListView listView;
    private LocationModel location;
    private MetterHistoryAdapter adapter;

    public StationHistoryFragment() {
        // Required empty public constructor
    }

    public static StationHistoryFragment newInstance(LocationModel location){
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

        listView = rootView.findViewById(R.id.listView);
        //adapter = new MetterHistoryAdapter(getActivity(), location.getMetterList());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
