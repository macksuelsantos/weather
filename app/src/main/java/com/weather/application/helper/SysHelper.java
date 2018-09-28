package com.weather.application.helper;

import java.util.Calendar;
import java.util.Date;

public class SysHelper {

    public static String convertHourMinutes(long value) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(value * 1000));

        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        String hours, minutes;

        if (h < 10) {
            hours = "0" + h;
        } else {
            hours = String.valueOf(h);
        }

        if (m < 10) {
            minutes = "0" + m;
        } else {
            minutes = String.valueOf(m);
        }

        return hours + " : " + minutes;
    }
}
