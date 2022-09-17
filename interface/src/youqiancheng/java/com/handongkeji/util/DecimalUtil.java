package com.handongkeji.util;

import java.math.BigDecimal;

public class DecimalUtil {

	private static int ROUND_CEILING; // 向正无穷方向舍入
	private static int ROUND_DOWN; // 向零方向舍入
	private static int ROUND_FLOOR; // 向负无穷方向舍入
	private static int ROUND_HALF_DOWN; // 向（距离）最近的�?边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留�?位小数结果为1.5
	private static int ROUND_HALF_EVEN; // 向（距离）最近的�?边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP ，如果是偶数，使用ROUND_HALF_DOWN
	private static int ROUND_HALF_UP; // 向（距离）最近的�?边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留�?位小数结果为1.6
	private static int ROUND_UNNECESSARY; // 计算结果是精确的，不�?要舍入模�?
	private static int ROUND_UP; // 向远�?0的方向舍�?

	private static final int DEF_DIV_SCALE = 10;



	public static double deciMal(String value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		return bd.doubleValue();
	}

	/**
	 * @param value
	 * @param scale
	 * @param roundingmode
	 * @return
	 * @author liuyang
	 * 2018年4月26日 下午1:29:01
	 */
	public static BigDecimal BigToPay(BigDecimal value, Integer scale, Integer roundingmode) {
		if (roundingmode == null) {
			roundingmode = BigDecimal.ROUND_HALF_UP;
		}
		return value.setScale(scale, roundingmode);
	}

	public static double deciMal(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	public static BigDecimal deciBigMal(String value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		return bd;
	}

	public static void main(String[] args) {

		/*System.out.println(DecimalUtil.diviBigMal("2000", "3", 2, BigDecimal.ROUND_HALF_UP));

		System.out.println(new BigDecimal("3333").compareTo(new BigDecimal("2112")));

		System.out.println(DecimalUtil.judgeStrDecil("1111", "2222.00"));

		System.out.println(DecimalUtil.BigToPay(new BigDecimal(2.00),2,null).toString());*/
		BigDecimal bd1 = new BigDecimal(300.08);
		BigDecimal bd2 = new BigDecimal(0);
		System.out.println(DecimalUtil.BigMalMultBigMal(bd1,bd2,2,BigDecimal.ROUND_HALF_UP));
		System.out.println(DecimalUtil.BigMalMultBigMal(bd1,bd2,2,BigDecimal.ROUND_HALF_UP));
		System.out.println(DecimalUtil.BigMalMultBigMal(bd1,bd2,2,BigDecimal.ROUND_HALF_UP));
		System.out.println(DecimalUtil.addBigMall(bd1,DecimalUtil.BigMalMultBigMal(bd1,bd2,2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP));
		/*System.out.println(DecimalUtil.addBigMal("20","0.8",2,BigDecimal.ROUND_HALF_UP));
		System.out.println(DecimalUtil.multiplicationBigMal("100.00","0.80",2,BigDecimal.ROUND_HALF_UP));*/

	}

	// 比较大小  // 大于 1 小于 -1 等于 0
	public static Integer judgeStrDecil (String start, String end) {
		Integer flag = new BigDecimal(start).compareTo(new BigDecimal(end));
		return flag;
	}

	// BigDecimal 比较大小  // 大于 1 小于 -1 等于 0
	public static Integer judgeBigDecil (BigDecimal start, BigDecimal end) {
		Integer flag = start.compareTo(end);
		return flag;
	}

	// 除法
	public static BigDecimal diviBigMal(String value1, String value2, int scale, int roundingMode) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		BigDecimal bd = bd1.divide(bd2, scale, roundingMode);
		return bd;
	}

	public static double deciMals(BigDecimal value, int scale, int roundingMode) {
		value = value.setScale(scale, roundingMode);
		double d = value.doubleValue();
		value = null;
		return d;
	}

	// 加法
	public static BigDecimal addBigMal(String value1, String value2, int scale, int roundingMode) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		return bd1.add(bd2);
	}

	public static BigDecimal addBigMall(BigDecimal value1, BigDecimal value2, Integer scale, Integer roundingmode) {
		BigDecimal bd2 = value1.add(value2);
		if (roundingmode==null) {
			roundingmode = BigDecimal.ROUND_HALF_UP;
		}
		bd2 = bd2.setScale(scale, roundingmode);
		return bd2;
	}

	// 减法
	public static BigDecimal subtractionBigMal(String value1, String value2, int scale, int roundingMode) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		return bd1.subtract(bd2);
	}

	public static BigDecimal subTionBigMal(BigDecimal value1, BigDecimal value2, Integer scale, Integer roundingmode) {
		if (roundingmode==null) {
			roundingmode = BigDecimal.ROUND_HALF_UP;
		}
		return value1.subtract(value2).setScale(scale, roundingmode);
	}

	// 乘法
	public static BigDecimal multiplicationBigMal(String value1, String value2, int scale, Integer roundingMode) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		if (roundingMode==null)
			roundingMode = BigDecimal.ROUND_HALF_UP;
		return bd1.multiply(bd2).setScale(scale,roundingMode);
	}

	// big乘法转换
	public static BigDecimal BigMalMultBigMal(BigDecimal value1, BigDecimal value2, Integer scale, Integer roundingmode) {
		if (roundingmode==null)
			roundingmode = BigDecimal.ROUND_HALF_UP;
		return value1.multiply(value2).setScale(scale,roundingmode);
	}

	// 除法
	public static BigDecimal divisionBigMal(String value1, String value2, int scale, Integer roundingMode) {
		BigDecimal bd1 = new BigDecimal(value1);
		BigDecimal bd2 = new BigDecimal(value2);
		if (roundingMode==null)
			roundingMode = BigDecimal.ROUND_HALF_UP;
		return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP);
	}

	// 除法 BigDecimal 传参
	public static BigDecimal divisionBigMal(BigDecimal value1, BigDecimal value2, int scale, Integer roundingMode) {
		if (roundingMode==null)
			roundingMode = BigDecimal.ROUND_HALF_UP;
		return value1.divide(value2).setScale(scale,roundingMode);
	}



}






