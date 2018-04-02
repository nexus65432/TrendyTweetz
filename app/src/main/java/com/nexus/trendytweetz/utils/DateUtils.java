package com.nexus.trendytweetz.utils;


import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getDurationString(long seconds) {
        Format formatter = new SimpleDateFormat( "hh:mm:ss" );
        return formatter.format(new Date(seconds));
    }

    public static String getCurrentTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
