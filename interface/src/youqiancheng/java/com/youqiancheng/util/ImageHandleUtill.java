package com.youqiancheng.util;

import com.handongkeji.util.Constants;
import com.handongkeji.util.ImgUtil;

import java.io.File;

public class ImageHandleUtill {
	static int handle(File file,String filepath){
		String prefixtemp=filepath.substring(0,filepath.lastIndexOf("."));//不含点
		String extendtemp=filepath.substring(filepath.lastIndexOf("."),filepath.length());//包含点
		String filepathbig=prefixtemp+"_big"+extendtemp;
		String filepathmiddle=prefixtemp+"_mid"+extendtemp;
		String filepathsmall=prefixtemp+"_sma"+extendtemp;
		try {
			ImgUtil.createImage(file, filepathbig, Constants.LARGE_WIDTH, Constants.LARGE_HEIGHT);
			ImgUtil.createImage(file, filepathmiddle, Constants.MEDIUM_WIDTH,  Constants.MEDIUM_HEIGHT);
			ImgUtil.createImage(file, filepathsmall, Constants.SMALL_WIDTH,  Constants.SMALL_HEIGHT);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
}
