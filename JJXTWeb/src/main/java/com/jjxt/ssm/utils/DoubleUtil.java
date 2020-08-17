package com.jjxt.ssm.utils;

public class DoubleUtil {
	
	public static Double formateDouble(Double d) {
		return (double)Math.round(d*100)/100;
	}
}
