package com.fish.rpc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	static DateFormat dateFormat = new SimpleDateFormat("mm:ss:SSS");
	public static String formatMillsecond(long millsecond){
		if(millsecond<=0){
			return dateFormat.format(new Date());
		}
		return dateFormat.format(new Date(millsecond));
	}
	public static String currentDateString(){
		return formatMillsecond(0);
	}
}
