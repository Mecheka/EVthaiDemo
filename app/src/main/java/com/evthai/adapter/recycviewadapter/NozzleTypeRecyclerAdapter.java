package com.evthai.adapter.recycviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.evthai.R;
import com.evthai.adapter.holder.NozzleTypeVIewHolder;
import com.evthai.model.charger.Connector;

import java.util.ArrayList;
import java.util.List;

public class NozzleTypeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Connector> nozzleList;

    public NozzleTypeRecyclerAdapter(Context mContext, ArrayList<Connector> nozzleList) {
        this.mContext = mContext;
        this.nozzleList = nozzleList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nozzle_type, parent, false);
        return new NozzleTypeVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        NozzleTypeVIewHolder nozzleTypeVIewHolder = (NozzleTypeVIewHolder) holder;
//        if (nozzleList.get(position).getType().equals("AC T1")){
//            nozzleTypeVIewHolder.imgNozzle.setImageResource(R.drawable.ic_nozzle_t1);
//        }else {
//            nozzleTypeVIewHolder.imgNozzle.setImageResource(R.drawable.ic_nozzle_t2);
//        }

    }

    @Override
    public int getItemCount() {
        return nozzleList.size();
    }
}
