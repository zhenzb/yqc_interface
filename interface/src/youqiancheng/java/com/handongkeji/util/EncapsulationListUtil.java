package com.handongkeji.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class EncapsulationListUtil {

	protected static final Log logger = LogFactory.getLog(EncapsulationListUtil.class);

	// 更改为string类型
	public static List<String> changeStringType(String text) {
		String[] array = null;
		if (StringUtil.notNullOrEmpty(text)) {
			array = text.split(",");
		} 
		List<String> list = new ArrayList<String>();
		if (array != null && array.length!=0) {
			for (int i = 0; i < array.length; i++) {
				if (StringUtil.notNullOrEmpty(array[i])) {
					list.add(array[i]);
				}
			}
		}
		return list;
	}

	// 更改为int类型
	public static List<Integer> changeIntegerType(String text) {
		String[] array = null;
		if (StringUtil.notNullOrEmpty(text)) {
			array = text.split(",");
		}
		List<Integer> list = new ArrayList<Integer>();
		if (array != null && array.length!=0) {
			for (int i = 0; i < array.length; i++) {
				if (StringUtil.notNullOrEmpty(array[i])) {
					list.add(Integer.parseInt(array[i]));
				}
			}
		}
		return list;
	}

	// 更改为Long 类型 集合
	public static List<Long> changeLongType(String text) {
		String[] array = null;
		if (StringUtil.notNullOrEmpty(text)) {
			array = text.split(",");
		}
		List<Long> list = new ArrayList<Long>();
		if (array != null && array.length!=0) {
			for (int i = 0; i < array.length; i++) {
				if (StringUtil.notNullOrEmpty(array[i])) {
					list.add(Long.parseLong(array[i]));
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		String sl = "12312,1212,112";
		List<Long> list = EncapsulationListUtil.changeLongType(sl);
		System.out.println(list);
	}

}
