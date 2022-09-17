package com.handongkeji.util;
/**
 *
 *  @author victor
 *  Date 2015-7-14 下午12:23:24 
 */

public class DistanceUtil {
	//地球半径
	private static double R= (double) 6370996.81;
	
	private static double pi=(double) 3.1415926535898;
	
	//经度：Longitude  |纬度： latitude
	//计算两点之间的距离
	public static double getDistande(
			double lng1,double lat1,double  lng2,double lat2){	
		double dst=R*Math.acos(Math.cos(lat1*pi/180 )*Math.cos(lat2*pi/180)*Math.cos(lng1*pi/180 -lng2*pi/180)+
				Math.sin(lat1*pi/180 )*Math.sin(lat2*pi/180));
		return dst;
	}
	
	public static void main(String[] args) {
	double dst=	getDistande(116.323089, 39.990352, 116.310962, 39.990352);
		System.out.println("计算的距离为："+dst);
	}
}


