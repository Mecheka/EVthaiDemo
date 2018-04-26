package com.evthai.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.evthai.R;

public class NozzleTypeVIewHolder extends RecyclerView.ViewHolder {

    public ImageView imgNozzle;

    public NozzleTypeVIewHolder(View itemView) {
        super(itemView);
        imgNozzle = itemView.findViewById(R.id.imgNozzleType);
    }
}
