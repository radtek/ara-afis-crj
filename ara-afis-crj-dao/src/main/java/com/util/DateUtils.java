package com.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * 日期处理
 * 
 * @author NavyWang
 * @email sunlightcs@gmail.com
 * @time 2017-8-2 10:57 am
 * @modify NavyWang
 */
public class DateUtils {


	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy\\MM\\dd) */
    public final static String DATE_PATTERN_File = "yyyy" + File.separator + "MM" + File.separator + "dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式(HH.mm.ss yyyy-MM-dd ) */
    public final static String TIME_PATTERN = "HH.mm.ss yyyy-MM-dd ";


    public static String format(LocalDateTime date, String pattern) {
        if(date != null){
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            return df.format(date);
        }
        return null;
    }
    
    /**
     * 现在是否超过了指定时间
     * @param date
     * @param minute
     * @return
     */
    public static boolean checkOverTime(LocalDateTime date,int minute){
        return Instant.now().toEpochMilli() > date
                .withMinute(date.getMinute()+minute)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    //    用于将日期格式化为字符串
    public static String formatDate(LocalDateTime date, String datePattern){
        if(StringUtils.isEmpty(datePattern)){
            datePattern = DATE_TIME_PATTERN;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDateTime.class.cast(date).format(formatter);
    }

    public static String formatDateTime(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return LocalDateTime.class.cast(date).format(formatter);

    }

    public static LocalDateTime parse(String date, String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
