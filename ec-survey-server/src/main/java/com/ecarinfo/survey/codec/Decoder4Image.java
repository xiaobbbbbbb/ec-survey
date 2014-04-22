package com.ecarinfo.survey.codec;

import com.ecarinfo.survey.vo.ImageVO;


public class Decoder4Image {
	
	public static final ImageVO decode(byte [] bytes) {
		ImageVO vo = new ImageVO();
		vo.setImei(new String(bytes,2,15));
		vo.setPictureSerialNumber(new String(bytes,17,5));
		vo.setPkgSize(new String(bytes,22,3));
		vo.setPkgOrder(new String(bytes,25,3));
		System.err.println("------------第"+vo.getPkgOrder()+"个数据包。");
		if((Integer.parseInt(vo.getPkgOrder()) == 1)) {
			vo.setShotTime(new String(bytes,28,12));
			vo.setLocation(new String(bytes,40,19));
			byte [] imageData = new byte[bytes.length - 60];
			System.arraycopy(bytes, 59, imageData, 0, imageData.length);
			vo.setImageData(imageData);
		} else {
			byte [] imageData = new byte[bytes.length - 29];
			System.arraycopy(bytes, 28, imageData, 0, imageData.length);
			vo.setImageData(imageData);
		}
		return vo;
	}
	
	public static void main(String[] args) {
		
	}
}
