package org.banana.api.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;

public class TestFunctionOutDto {
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse("2026-02-01");

        Calendar calendar = calendar(parse);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String quarter = "";
        if (month == 1) {
            year = year - 1;
            quarter = "4";
        } else if (month <= 4) {
            quarter = "1";
        } else if (month <= 7) {
            quarter = "2";
        } else if (month <= 10) {
            quarter = "3";
        } else if (month <= 12) {
            quarter = "4";
        }
        System.out.println(StrUtil.builder().append(year).append("-Q").append(quarter).toString());
    }
    public static Calendar calendar(Date date) {
        return date instanceof DateTime ? ((DateTime) date).toCalendar() : calendar(date.getTime());
    }
    public static Calendar calendar(long millis) {
        return calendar(millis, TimeZone.getDefault());
    }

    public static Calendar calendar(long millis, TimeZone timeZone) {
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTimeInMillis(millis);
        return cal;
    }
}
