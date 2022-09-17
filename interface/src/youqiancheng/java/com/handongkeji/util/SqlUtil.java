package com.handongkeji.util;

public class SqlUtil {
	
	/* 
	 * @Author: victor
	 * @Description :   过滤req中的关键字
	 * @param str
	 * @return
	 */
	public static boolean sql_inj_dist(String str){
		
		String inj_str = "'@or@and@exec@insert@select@delete@update@count@*@chr@mid@master@truncate@char@declare@;@-@+@,";
		
		String inj_stra[] = inj_str.split("@");
		for (int i = 0; i < inj_stra.length; i++)
		{
			
			if (str.indexOf(inj_stra[i]) >= 0)
				
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	public static boolean sql_inj(String str){

		String inj_str = "exec@insert@select@delete@update@count@*@chr@mid@master@truncate@char@declare@;@-@+";

		String inj_stra[] = inj_str.split("@");
		for (int i = 0; i < inj_stra.length; i++)
		{

			if (str.indexOf(inj_stra[i]) >= 0)

			{

				return true;

			}

		}

		return false;

	}
	
	public static boolean sql_inj_orderby(String str){
		
		// String inj_str ="'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_str = "'@or@and@exec@insert@select@delete@update@count@*@chr@mid@master@truncate@char@declare@;@-@+@";
		
		String inj_stra[] = inj_str.split("@");
		for (int i = 0; i < inj_stra.length; i++)
		{
			
			if (str.indexOf(inj_stra[i]) >= 0)
				
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
