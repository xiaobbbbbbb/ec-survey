package com.ecarinfo.survey.vo;

public class ImageVO extends BaseVO {
	private static final long serialVersionUID = -2189117457140936624L;
	private String imei;
	private String pictureSerialNumber;
	private String pkgSize;//total pkg
	private String pkgOrder;//current pkg
	private String shotTime;//拍照时间
	private String location;//位置信息
	private byte[] imageData;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPictureSerialNumber() {
		return pictureSerialNumber;
	}

	public void setPictureSerialNumber(String pictureSerialNumber) {
		this.pictureSerialNumber = pictureSerialNumber;
	}

	public String getPkgSize() {
		return pkgSize;
	}

	public void setPkgSize(String pkgSize) {
		this.pkgSize = pkgSize;
	}

	public String getPkgOrder() {
		return pkgOrder;
	}

	public void setPkgOrder(String pkgOrder) {
		this.pkgOrder = pkgOrder;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getShotTime() {
		return shotTime;
	}

	public void setShotTime(String shotTime) {
		this.shotTime = shotTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getDescriptorForType() {
		return "ImageCommand";
	}

}
