package com.handongkeji.util;

import java.util.List;
import java.util.Random;

public class RandamUtils {
	private static Random random;

	//双重校验锁获取一个Random单例
	public static Random getRandom() {
		if (random == null) {
			synchronized (RandamUtils.class) {
				if (random == null) {
					random = new Random();
				}
			}
		}
		return random;
	}

	/**
	 * 获得一个[0,max)之间的整数。
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int max) {
		return Math.abs(getRandom().nextInt()) % max;
	}

	/**
	 * 从list中随机取得一个元素
	 * @param <E>
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <e, E> E getRandomElement(List<e> list) {
		return (E) list.get(getRandomInt(list.size()));
	}

	/**
	 * 生成一个带字母和数字的随机字符串
	 * @author JSen
	 * @param x	字母个数
	 * @param y	数字个数
	 * @return
	 */
	public static String random(Integer x, Integer y) {
		String str = "";
		if (x == null || y == null || (x == 0 && y == 0)) {
			str = null;
		} else {
			if (x < 0)
				x = -x;
			if (y < 0)
				y = -y;
			for (int i = 0; i < x; i++) {
				str = str + (char) (Math.random() * 26 + 'A');
			}
			int a[] = new int[y];
			for (int i = 0; i < a.length; i++) {
				a[i] = (int) (10 * (Math.random()));
				str = str + a[i];
			}
		}
		return str;
	}
}
