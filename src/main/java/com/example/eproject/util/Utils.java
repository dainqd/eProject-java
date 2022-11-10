package com.example.eproject.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class Utils {
    public static LocalDateTime converLocalDateTime(String dateTime) {
        ZoneId zoneId = ZoneId.of("Europe/London");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = null;
        try {
            Date parsedDate = dateFormat.parse(dateTime);
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(parsedDate.getTime()), zoneId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    public static java.sql.Date convertDate(String datetime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (java.sql.Date) date;
    }

    public static String convertString(Date datetime){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(datetime);
    }

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static String formatDate(LocalDateTime localDateTime){
		return formatter.format(localDateTime);
	}

    public static String formatNumber(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###############.########");
        return myFormatter.format(value);
    }
}
