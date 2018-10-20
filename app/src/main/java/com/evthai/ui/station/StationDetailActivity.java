package com.evthai.ui.station;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.model.stations.Station;

import org.parceler.Parcels;

public class StationDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView icBack;
    private TextView tvStationName;
    private Station station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        initInstance();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, StationDetailFragment.newInstance(station))
                    .commit();
        }
    }

    private void initInstance() {

        station = Parcels.unwrap(getIntent().getParcelableExtra("station"));

        toolbar = findViewById(R.id.toolbar);
        icBack= toolbar.findViewById(R.id.icBack);
        tvStationName = toolbar.findViewById(R.id.tvStationTitle);

        tvStationName.setText(station.getName());
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
