package com.evthai.adapter.viewpageradapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.evthai.fragment.dialog.StationFragment;
import com.evthai.fragment.dialog.StationHistoryFragment;
import com.evthai.model.LocationModel;

public class StationPagerAdapter extends FragmentPagerAdapter {

    private LocationModel location;

    public StationPagerAdapter(FragmentManager fm, LocationModel locationModel) {
        super(fm);
        this.location = locationModel;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StationFragment.newInstance(location);
            case 1:
                return StationHistoryFragment.newInstance(location);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Information";
            case 1:
                return "History";
        }
        return null;
    }
}
