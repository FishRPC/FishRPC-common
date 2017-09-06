package com.fish.rpc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
	public static String formatMillsecond(long millsecond){
		if(millsecond<=0){
			return dateFormat.format(new Date());
		}
		return dateFormat.format(new Date(millsecond));
	}
	public static String currentDateString(){
		return formatMillsecond(System.currentTimeMillis());
	}
	
	public static void main(String[] args){
		System.out.println(currentDateString());
	}
} 
