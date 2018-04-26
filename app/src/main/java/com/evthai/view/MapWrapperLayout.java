package com.evthai.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapWrapperLayout extends RelativeLayout {

    private GoogleMap map;
    private int bootomOffsetPixels;
    private Marker marker;
    private View infoWindow;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MapWrapperLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(GoogleMap map, int bottomOffsetPixels) {
        this.map = map;
        this.bootomOffsetPixels = bottomOffsetPixels;
    }

    public void setMarkerWithInfoWindow(Marker marker, View infoWindow) {
        this.marker = marker;
        this.infoWindow = infoWindow;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = false;
        if (marker != null && marker.isInfoWindowShown() && map != null && infoWindow != null) {
            Point point = map.getProjection().toScreenLocation(marker.getPosition());
            MotionEvent copyEv = MotionEvent.obtain(ev);
            copyEv.offsetLocation(
                    -point.x + (infoWindow.getWidth() / 2),
                    -point.y + infoWindow.getHeight() + bootomOffsetPixels);
            ret = infoWindow.dispatchTouchEvent(copyEv);
        }
        return ret || super.dispatchTouchEvent(ev);
    }
}
