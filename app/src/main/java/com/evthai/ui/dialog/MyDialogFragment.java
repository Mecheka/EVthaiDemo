package com.evthai.ui.dialog;

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
import android.view.WindowManager;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.viewpageradapter.StationPagerAdapter;
import com.evthai.model.charger.Charger;
import com.evthai.model.stations.Station;
import com.rd.PageIndicatorView;

import org.parceler.Parcels;

public class MyDialogFragment extends DialogFragment {

    private ViewPager viewPager;
    private TextView tvStation;

    private PageIndicatorView indicator;
    private StationPagerAdapter adapter;
    private Charger stationDao;

    public static MyDialogFragment newInstance(Charger charger){
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("station", Parcels.wrap(charger));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationDao = Parcels.unwrap(getArguments().getParcelable("station"));
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

        initInstance(rootView);

        return rootView;
    }

    private void initInstance(View rootView) {

        viewPager = rootView.findViewById(R.id.viewPager);
        indicator = rootView.findViewById(R.id.pageIndicatorView);

        indicator.setCount(2);
        indicator.setSelection(0);
        adapter = new StationPagerAdapter(getChildFragmentManager(), stationDao);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.y = 100;
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dialog_height));
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(R.drawable.dialog_bg_inset);
        getDialog().getWindow().setAttributes(params);

    }
}
