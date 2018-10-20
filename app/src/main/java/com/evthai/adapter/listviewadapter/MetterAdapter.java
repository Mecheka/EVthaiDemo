package com.evthai.adapter.listviewadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.evthai.view.ItemMetterDialog;

import java.util.ArrayList;

public class MetterAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<MetterModel> metterList;

    public MetterAdapter(Context mContext, ArrayList<MetterModel> metterList) {
        this.mContext = mContext;
        this.metterList = metterList;
    }

    @Override
    public int getCount() {
        return metterList.size();
    }

    @Override
    public Object getItem(int i) {
        return metterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemMetterDialog item;
        if (view != null){
            item = (ItemMetterDialog) view;
        }else {
            item = new ItemMetterDialog(mContext);
        }
        MetterModel metter = (MetterModel) getItem(i);
//        item.setTvNozzle(metter.getNozzle());
//        item.setTvStatus(metter.isMetterStatus());
//        item.setTvCardNo(metter.getCardNo());
//        item.setTvStartChart(metter.getStartChart());
//        item.setTvEndChart(metter.getEndChart());
//        item.setTvEndMetter(metter.getEndMetter());
//        item.setTvUpdate(metter.getEndChart());
        return item;
    }
}
