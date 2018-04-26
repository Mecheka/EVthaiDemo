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

public class ItemMarker extends FrameLayout {

    private TextView tvMenu;

    public ItemMarker(@NonNull Context context) {
        super(context);
        initInflate();
        intiInstance();
    }

    public ItemMarker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        intiInstance();
    }

    public ItemMarker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        intiInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemMarker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        intiInstance();
    }

    private void initInflate(){

        inflate(getContext(), R.layout.item_maker, this);

    }

    private void intiInstance(){

        tvMenu = findViewById(R.id.tvMenu);

    }

    public void setTvMenu(String text){

        tvMenu.setText(text);

    }

}
