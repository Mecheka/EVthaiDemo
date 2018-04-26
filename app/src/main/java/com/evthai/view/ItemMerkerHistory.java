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


public class ItemMerkerHistory extends FrameLayout {

    private TextView tvNozzle;
    private TextView tvCardNo;
    private TextView tvStartChart;
    private TextView tvEndChart;
    private TextView tvEndMetter;

    public ItemMerkerHistory(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public ItemMerkerHistory(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public ItemMerkerHistory(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemMerkerHistory(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.item_dialog_history_marker, this);
    }

    private void initInstance() {

        tvNozzle = findViewById(R.id.tvNozzle);
        tvCardNo = findViewById(R.id.tvCardNo);
        tvStartChart = findViewById(R.id.tvStartChart);
        tvEndChart = findViewById(R.id.tvEndChart);
        tvEndMetter = findViewById(R.id.tvEndMetter);

    }

    public void setTvNozzle(String text) {
        tvNozzle.setText(text);
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

}
