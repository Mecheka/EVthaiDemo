package com.evthai.adapter.listviewadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.evthai.model.MetterModel;
import com.evthai.view.ItemMerkerHistory;

import java.util.ArrayList;


public class MetterHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<MetterModel> metterList;

    public MetterHistoryAdapter(Context mContext, ArrayList<MetterModel> metterList) {
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
        ItemMerkerHistory item;
        if (view != null){
            item = (ItemMerkerHistory) view;
        }else {
            item = new ItemMerkerHistory(mContext);
        }
        MetterModel metter = (MetterModel) getItem(i);
        item.setTvNozzle(metter.getNozzle());
        item.setTvCardNo(metter.getCardNo());
        item.setTvStartChart(metter.getStartChart());
        item.setTvEndChart(metter.getEndChart());
        item.setTvEndMetter(metter.getEndMetter());
        return item;
    }
}
