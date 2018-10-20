package com.evthai.ui.station;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evthai.R;
import com.evthai.model.charger.Charger;

import java.util.ArrayList;

public class ChargerAdapter extends RecyclerView.Adapter<ChargerAdapter.ChargerViewHolder> {

    private ArrayList<Charger> chargerList;
    private OnItemClick onItemClick;

    public ChargerAdapter(ArrayList<Charger> chargerList) {
        this.chargerList = chargerList;
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ChargerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_charger_adapter, viewGroup,
                false);
        return new ChargerViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ChargerViewHolder chargerViewHolder, int i) {
        chargerViewHolder.setTvChargerTitle(chargerList.get(i).getDetail().getName());
    }

    @Override
    public int getItemCount() {
        if (chargerList == null) {
            return 0;
        } else if (chargerList.size() <= 0) {
            return 0;
        } else {
            return chargerList.size();
        }
    }

    class ChargerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChargerTitle;

        public ChargerViewHolder(@NonNull View itemView, final OnItemClick onItemClick) {
            super(itemView);

            tvChargerTitle = itemView.findViewById(R.id.tvChargerTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (onItemClick != null){
                        onItemClick.onItemClick(position);
                    }
                }
            });
        }

        public void setTvChargerTitle(String text) {
            tvChargerTitle.setText(text);
        }
    }

    interface OnItemClick{
        void onItemClick(int postion);
    }
}
