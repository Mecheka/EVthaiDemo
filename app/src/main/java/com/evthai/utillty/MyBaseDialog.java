package com.evthai.utillty;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.adapter.listviewadapter.MetterAdapter;
import com.evthai.adapter.recycviewadapter.NozzleTypeRecyclerAdapter;
import com.evthai.model.LocationModel;

public class MyBaseDialog {

    public static void metterDialog(Context mContext, LocationModel location) {

       /** AlertDialog.Builder builderDialog = new AlertDialog.Builder(mContext);
        View dialogView = View.inflate(mContext, R.layout.dialog_station, null);
        builderDialog.setView(dialogView);

        TextView tvDialogId = dialogView.findViewById(R.id.tvStation);
        TextView tvDialogAddress = dialogView.findViewById(R.id.tvAddress);
        TextView tvDialogBrand = dialogView.findViewById(R.id.tvBrand);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ListView dialogListView = dialogView.findViewById(R.id.listView);

        AlertDialog dialog = builderDialog.create();
        dialog.show();

        if (location != null) {
            //tvDialogId.setText(location.getStationName());
            tvDialogAddress.setText(location.getAddress());
            tvDialogBrand.setText(location.getBrand());

            // RecycLerViewAdapter
            NozzleTypeRecyclerAdapter adapter = new NozzleTypeRecyclerAdapter(mContext, location.getNozaleList());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            // ListViewAdapter
            MetterAdapter listViewAdapter = new MetterAdapter(mContext, location.getMetterList());
            dialogListView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();
        }*/
    }

}
