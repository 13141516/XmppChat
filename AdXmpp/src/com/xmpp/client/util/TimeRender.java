package com.xmpp.client.util;

import java.text.SimpleDateFormat;

public class TimeRender {

	private static SimpleDateFormat formatBuilder = new SimpleDateFormat("hh:mm:ss");

	public static String getDate(long time) {
		return formatBuilder.format(time);
	}
	
	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
	public static String getTimeString() {
		return getDate(getCurrentTime());
	}
	
}
