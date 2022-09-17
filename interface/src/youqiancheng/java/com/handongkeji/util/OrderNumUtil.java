package com.handongkeji.util;

import java.text.DecimalFormat;
import java.util.Date;

public class OrderNumUtil {
	private static long i = 0;

	public static synchronized String getObject(){
		Long date = new Date().getTime();
		String sequ = new DecimalFormat("0000").format(i++);
		if(i >= 9999){
			i = 0;
		}
		return date.toString() + sequ;
	}

	public static void main(String[] args) {
		getObject();
	}

}
