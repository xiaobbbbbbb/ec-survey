package com.ecarinfo.survey.vo;

public class GprsVO extends BaseVO {
	private static final long serialVersionUID = 8372379456342905749L;
	private String len;
	private String imei;
	private String alarmType;
	private GprmcVO gprmc;
	
	private String pdop;
	private String hdop;
	private String vdop;
	private String status;
	private String rtc;
	private String voltage;
	
	private String adc;
	private String lacci;
	private String temperature;
	private String odoMeter;
	private String serialId;
	private String rfId;
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public GprmcVO getGprmc() {
		return gprmc;
	}
	public void setGprmc(GprmcVO gprmc) {
		this.gprmc = gprmc;
	}
	public String getAdc() {
		return adc;
	}
	public void setAdc(String adc) {
		this.adc = adc;
	}
	public String getLacci() {
		return lacci;
	}
	public void setLacci(String lacci) {
		this.lacci = lacci;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getOdoMeter() {
		return odoMeter;
	}
	public void setOdoMeter(String odoMeter) {
		this.odoMeter = odoMeter;
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getRfId() {
		return rfId;
	}
	public void setRfId(String rfId) {
		this.rfId = rfId;
	}
	public String getPdop() {
		return pdop;
	}
	public void setPdop(String pdop) {
		this.pdop = pdop;
	}
	public String getHdop() {
		return hdop;
	}
	public void setHdop(String hdop) {
		this.hdop = hdop;
	}
	public String getVdop() {
		return vdop;
	}
	public void setVdop(String vdop) {
		this.vdop = vdop;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRtc() {
		return rtc;
	}
	public void setRtc(String rtc) {
		this.rtc = rtc;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	@Override
	public String getDescriptorForType() {
		return "DeviceDataCommand";
	}
	@Override
	public String toString() {
		return "GprsVO [len=" + len + ", imei=" + imei + ", alarmType="
				+ alarmType + ", gprmc=" + gprmc + ", pdop=" + pdop + ", hdop="
				+ hdop + ", vdop=" + vdop + ", status=" + status + ", rtc="
				+ rtc + ", voltage=" + voltage + ", adc=" + adc + ", lacci="
				+ lacci + ", temperature=" + temperature + ", odoMeter="
				+ odoMeter + ", serialId=" + serialId + ", rfId=" + rfId + "]";
	}
	
	
}
