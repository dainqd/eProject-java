package com.example.eproject.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class Utils {
    public static LocalDateTime converLocalDateTime(String dateTime) {
        ZoneId zoneId = ZoneId.of("Europe/London");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");
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
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = df.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (java.sql.Date) date;
    }

    public static String convertString(Date datetime) {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(datetime);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }

    public static String formatNumber(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###############.########");
        return myFormatter.format(value);
    }

    public static boolean isValidEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static String generatorVerifyCode(int length) {
        String key = "";
        do {
            key = generatorRandomStringNumber(length);
        } while (key.indexOf("0") == 0);
        return key;
    }

    public static String generatorRandomToken(int length) {
        String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public static String generatorRandomStringNumber(int length) {
        String CHARS = "1234567890";
        Random random = new Random();
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public static String decimalToHex(long decimal) {
        return Long.toHexString(decimal);
    }

    public static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static String theDay(int day) {
        String strDays = "";
        if (day > 31 || day < 0) {
            strDays = "31th";
        } else if (day == 1) {
            strDays = "1st";
        } else if (day == 2) {
            strDays = "2nd";
        } else if (day == 3) {
            strDays = "3rd";
        } else if (day == 21) {
            strDays = "21st";
        } else if (day == 22) {
            strDays = "22nd";
        } else if (day == 23) {
            strDays = "23rd";
        } else {
            String payDay = Integer.toString(day);
            strDays = payDay + "th";
        }
        return strDays;
    }

    public static String convertToString(String date) {
//        String pattern = "dd/MM/yyyy";
//        DateFormat df = new SimpleDateFormat(pattern);
//        String todayAsString = df.format(date);
//        String[] parts = todayAsString.split("-");
        String[] parts = date.split("/");
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        String doneDay = parts[0];
        String doneMonth = parts[1];
        int intDay = Integer.parseInt(doneDay);
        String dayCuoi = Utils.theDay(intDay);
        int intMonth = Integer.parseInt(doneMonth);
        String monthCuoi = Utils.theMonth(intMonth - 1);
        String s = dayCuoi + ", " + monthCuoi + ", " + parts[2];
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        String a = "27/07/2003 10:10:10";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(a);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        String c = convertToString(date);
//        System.out.println(c);
    }
}
