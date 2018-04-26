package com.evthai.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.evthai.R;

public class ItemMetterDialog extends FrameLayout {

    private TextView tvNozzle;
    private TextView tvStatus;
    private TextView tvCardNo;
    private TextView tvStartChart;
    private TextView tvEndChart;
    private TextView tvEndMetter;
    private TextView tvUpdate;

    public ItemMetterDialog(@NonNull Context context) {
        super(context);
        initInflate();
        intiInstance();
    }

    public ItemMetterDialog(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        intiInstance();
    }

    public ItemMetterDialog(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        intiInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemMetterDialog(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        intiInstance();
    }

    private void initInflate() {

        inflate(getContext(), R.layout.item_dialog_marker, this);

    }

    private void intiInstance() {

        tvNozzle = findViewById(R.id.tvNozzle);
        tvStatus = findViewById(R.id.tvStatus);
        tvCardNo = findViewById(R.id.tvCardNo);
        tvStartChart = findViewById(R.id.tvStartChart);
        tvEndChart = findViewById(R.id.tvEndChart);
        tvEndMetter = findViewById(R.id.tvEndMetter);
        tvUpdate = findViewById(R.id.tvUpdate);

    }

    public void setTvNozzle(String text) {
        tvNozzle.setText(text);
    }

    public void setTvStatus(boolean metterStatus) {
        if (metterStatus) {
            tvStatus.setText(getResources().getString(R.string.thConnect));
        }else {
            tvStatus.setText(getResources().getString(R.string.thDisConnect));
        }

    }

    public void setTvCardNo(String text) {
        tvCardNo.setText(text);
    }

    public void setTvStartChart(String text) {
        tvStartChart.setText(text);
    }

    public void setTvEndChart(String text) {
        tvEndChart.setText(text);
    }

    public void setTvEndMetter(String text) {
        tvEndMetter.setText(text);
    }

    public void setTvUpdate(String text) {
        tvUpdate.setText(text);
    }
}
