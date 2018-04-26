package com.evthai.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.viewpageradapter.StationPagerAdapter;
import com.evthai.model.LocationModel;
import com.rd.PageIndicatorView;

import org.parceler.Parcels;

public class MyDialogFragment extends DialogFragment {

    private TextView tvStation;
    private ViewPager viewPager;
    private PageIndicatorView indicator;
    private StationPagerAdapter adapter;
    private LocationModel location;

    public static MyDialogFragment newInstance(LocationModel locationModel){
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("station", Parcels.wrap(locationModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = Parcels.unwrap(getArguments().getParcelable("station"));
    }

    /**@NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int heigth = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, heigth);

        return dialog;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_main_station, container, false);

        tvStation = rootView.findViewById(R.id.tvStation);
        viewPager = rootView.findViewById(R.id.viewPager);
        indicator = rootView.findViewById(R.id.pageIndicatorView);
        indicator.setCount(2);
        indicator.setSelection(0);
        //tvStation.setText(location.getStationName());
        adapter = new StationPagerAdapter(getChildFragmentManager(), location);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dialog_height));
        window.setGravity(Gravity.CENTER);
    }
}
