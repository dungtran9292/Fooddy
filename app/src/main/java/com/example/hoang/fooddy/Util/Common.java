package com.example.hoang.fooddy.Util;

/**
 * Created by VT-99 on 09/11/2017.
 */

public class Common {

    public static String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }

        return String.format("%4.3f%s", distance, unit);
    }
}
