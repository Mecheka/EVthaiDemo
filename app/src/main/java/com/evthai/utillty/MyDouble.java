package com.evthai.utillty;

import android.support.annotation.NonNull;

public class MyDouble implements Comparable<MyDouble>{

    final double val;
    final String string;

    public MyDouble(String str){
        string = str;
        val = Double.parseDouble(str);
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public int compareTo(@NonNull MyDouble myDouble) {
        return (int)(val - myDouble.val);
    }
}
