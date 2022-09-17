package com.handongkeji.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@SuppressWarnings("restriction")
public class ImgUtil {

	/**
	 * 生成图像(不强制按目标宽高生成图片)
	 *
	 * @param fileSrc
	 *            源文件
	 * @param pathDest
	 *            生成图片的路径
	 * @param widthDest
	 *            目标图片宽(如果为-1，表示可以任意宽度)
	 * @param heightDest
	 *            目标图片高(如果为-1，表示可以任意高度)
	 * @return
	 */
	public static boolean createImage(File fileSrc, String pathDest, int widthDest, int heightDest) {
		return createImage(false, fileSrc, pathDest, widthDest, heightDest);

	}

	/**
	 * 生成图像
	 *
	 * @param isForceWidthHeightToDest
	 *            是否按设定目标的宽高强制生成图片
	 * @param fileSrc
	 *            源文件
	 * @param pathDest
	 *            生成图片的路径
	 * @param widthDest
	 *            目标图片宽(如果为-1，表示按原图比率设定宽度)
	 * @param heightDest
	 *            目标图片高(如果为-1，表示按原图比率设定高度)
	 * @return
	 */
	public static boolean createImage(boolean isForceWidthHeightToDest, File fileSrc, String pathDest, int widthDest,
			int heightDest) {
		boolean flag = false;
		File out = null;
		String extenName = null;
		BufferedImage bi2 = null;
		try {
			if (widthDest <= 0 && heightDest <= 0)// 如果目标宽高均<=0，则无法生成图片
				return false;
			else if (widthDest <= 0 || heightDest <= 0)
				isForceWidthHeightToDest = false;
			int w = 1;
			int h = 1;
			Image imgSrc = ImageIO.read(fileSrc);// 源图片
			if (isForceWidthHeightToDest) {// 如果强制，则将图片宽高设定为目标宽高
				w = widthDest;
				h = heightDest;
			} else {
				int width = imgSrc.getWidth(null);
				int height = imgSrc.getHeight(null);
				if(widthDest>width||heightDest>height){
					
					heightDest=height;
					widthDest=width;
				}
				double imgRatio = width * 1.0 / height;
				if (widthDest > 0 && heightDest > 0) {
					w = widthDest;
					h = (int) (widthDest / imgRatio);
					if (h > heightDest) {
						w = (int) (heightDest * imgRatio);
						h = heightDest;
					}
				} else if (widthDest <= 0) {// 宽无限制
					w = (int) (heightDest * imgRatio);
					h = heightDest;
				} else {// 高无限制
					w = widthDest;
					h = (int) (widthDest / imgRatio);
				}
			}

			extenName = pathDest.substring(pathDest.lastIndexOf(".") + 1, pathDest.length());
			if (!"png".equalsIgnoreCase(extenName)) {
				BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(imgSrc, 0, 0, w, h, null);
				out = new File(pathDest);
				flag = writeImageFile(out, tag, 80);
			} else {
				try {
					bi2 = ImageIO.read(fileSrc);
					if(widthDest>bi2.getWidth()||heightDest>bi2.getHeight()){
						
						heightDest=bi2.getHeight();
						widthDest=bi2.getWidth();
					}
				} catch (IOException e) {
					flag = false;
					e.printStackTrace();
					return flag;
				}
				BufferedImage to = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = to.createGraphics();
				to = g2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
				g2d.dispose();
				g2d = to.createGraphics();
				Image from = bi2.getScaledInstance(w, h, BufferedImage.SCALE_AREA_AVERAGING);
				g2d.drawImage(from, 0, 0, null);
				g2d.dispose();
				File toFile = new File(pathDest);
				ImageIO.write(to, "png", toFile);

			}

		} catch (IOException ex) {
			flag = false;
			ex.printStackTrace();
		} finally {
			try {
				if (out != null)
					out = null;
			} catch (Exception e1) {

			}
		}
		return flag;
	}

	/**
	 * 将 BufferedImage 编码输出成硬盘上的图像文件
	 *
	 * @param quality
	 *            编码压缩的百分比
	 * @return 返回编码输出成功与否
	 */
	private static boolean writeImageFile(File fileDest, BufferedImage imageSrc, int quality) {
		try {
			Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("jpg");
			if (it.hasNext()) {
				FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(fileDest);
				ImageWriter iw = (ImageWriter) it.next();
				ImageWriteParam iwp = iw.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(quality / 100.0f);
				iw.setOutput(fileImageOutputStream);
				iw.write(null, new javax.imageio.IIOImage(imageSrc, null, null), iwp);
				iw.dispose();
				fileImageOutputStream.flush();
				fileImageOutputStream.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param filesrc
	 * @param logosrc
	 * @param outsrc
	 * @param x
	 *            位置
	 * @param y
	 *            位置
	 */
	@SuppressWarnings("restriction")
	public void composePic(String filesrc, String logosrc, String outsrc, int x, int y) {
		try {
			File bgfile = new File(filesrc);
			Image bg_src = ImageIO.read(bgfile);

			File logofile = new File(logosrc);
			Image logo_src = ImageIO.read(logofile);

			int bg_width = bg_src.getWidth(null);
			int bg_height = bg_src.getHeight(null);
			int logo_width = logo_src.getWidth(null);
			;
			int logo_height = logo_src.getHeight(null);

			BufferedImage tag = new BufferedImage(bg_width, bg_height, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = tag.createGraphics();
			g2d.drawImage(bg_src, 0, 0, bg_width, bg_height, null);

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f)); // 透明度设置开始
			g2d.drawImage(logo_src, x, y, logo_width, logo_height, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); // 透明度设置
																					// 结束

			FileOutputStream out = new FileOutputStream(outsrc);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag);
			ImageIO.write(tag, "jpg", out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
