package com.ecarinfo.survey.vo;

import org.apache.log4j.Logger;

public class GprmcVO extends BaseVO {
	private static final long serialVersionUID = 961286248498610170L;
	private static final Logger logger = Logger.getLogger(GprmcVO.class);
	
	private String satelliteNums;//定位卫星数
	private String protoName;//协议名称
	private String time;//标准定位时间（UTC time）格式：时时分分秒秒.秒秒秒（hhmmss.sss）
	private String valid;
	private String latitude;//纬度
	private String ns;//北半球（N）或南半球（S）
	private String longitude;//经度
	private String we;//东（E）半球或西（W）半球
	private String speed;//相对位移速度
	private String direction;//相对位移方向
	private String date;//日期 格式：日日月月年年（ddmmyy） 
	private String magneticVar;//磁极变量
	private String degree;//度数
	private String checkSum;//检查位
	
	public boolean init(String value) {
		String[] values = value.split(",");
		if(value.length() < 3) {
			throw new RuntimeException("Gprmc parse Exception. value="+value);
		}
		if(!"A".equals(values[2])) {
			logger.warn("Gprmc is Invalid.");
			return false;
		}
		int idx = 0;
		this.setProtoName(values[idx++]);
		this.setTime(values[idx++]);
		this.setValid(values[idx++]);
		this.setLatitude(values[idx++]);
		this.setNs(values[idx++]);
		this.setLongitude(values[idx++]);
		this.setWe(values[idx++]);
		this.setSpeed(values[idx++]);
		this.setDirection(values[idx++]);
		this.setDate(values[idx++]);
		this.setMagneticVar(values[idx++]);
		this.setDegree(values[idx++]);
		this.setCheckSum(values[idx++]);
		return true;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getNs() {
		return ns;
	}
	public void setNs(String ns) {
		this.ns = ns;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getWe() {
		return we;
	}
	public void setWe(String we) {
		this.we = we;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMagneticVar() {
		return magneticVar;
	}
	public void setMagneticVar(String magneticVar) {
		this.magneticVar = magneticVar;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getProtoName() {
		return protoName;
	}

	public void setProtoName(String protoName) {
		this.protoName = protoName;
	}
	
}
