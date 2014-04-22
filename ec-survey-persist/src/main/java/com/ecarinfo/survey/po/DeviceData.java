package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class DeviceData implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Integer deviceId;
	private String imei;//imei
	private Long carReportId;//行车id(从accon-accoff间只产生一个id)
	private Date gpsTime;//gps 时间
	private Double gpsLongitude;//经度
	private Double gpsLatitude;//纬度
	private Double baiduLongitude = 0.000000d;//经度
	private Double baiduLatitude = 0.000000d;
	private String geohash;//geohash
	private String areaNo;//基站区域代码
	private String gridNo;//基站栅格号
	private Integer mileMeter;//两点之间里程
	private Double speed;
	private Double temperature;//温度
	private Long isChargeVoltage = 0l;//是否使用外部电源
	private Double voltageInner;//锂电电压
	private Double voltageOuter;//车截电池电压
	private Double angleValue;//磁偏角
	private String angleDirection;//磁偏角方向
	private Date clientTime;//数据存储时间
	private Date updateTime = new Date();
	private Integer status = 0;//状态（0：未修正经纬度；1：已经修正经纬度）

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getDeviceId () {
        return deviceId;
    }

    public void setDeviceId (Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei () {
        return imei;
    }

    public void setImei (String imei) {
        this.imei = imei;
    }

    public Long getCarReportId () {
        return carReportId;
    }

    public void setCarReportId (Long carReportId) {
        this.carReportId = carReportId;
    }

    public Date getGpsTime () {
        return gpsTime;
    }

    public void setGpsTime (Date gpsTime) {
        this.gpsTime = gpsTime;
    }

    public Double getGpsLongitude () {
        return gpsLongitude;
    }

    public void setGpsLongitude (Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public Double getGpsLatitude () {
        return gpsLatitude;
    }

    public void setGpsLatitude (Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getBaiduLongitude () {
        return baiduLongitude;
    }

    public void setBaiduLongitude (Double baiduLongitude) {
        this.baiduLongitude = baiduLongitude;
    }

    public Double getBaiduLatitude () {
        return baiduLatitude;
    }

    public void setBaiduLatitude (Double baiduLatitude) {
        this.baiduLatitude = baiduLatitude;
    }

    public String getGeohash () {
        return geohash;
    }

    public void setGeohash (String geohash) {
        this.geohash = geohash;
    }

    public String getAreaNo () {
        return areaNo;
    }

    public void setAreaNo (String areaNo) {
        this.areaNo = areaNo;
    }

    public String getGridNo () {
        return gridNo;
    }

    public void setGridNo (String gridNo) {
        this.gridNo = gridNo;
    }

    public Integer getMileMeter () {
        return mileMeter;
    }

    public void setMileMeter (Integer mileMeter) {
        this.mileMeter = mileMeter;
    }

    public Double getSpeed () {
        return speed;
    }

    public void setSpeed (Double speed) {
        this.speed = speed;
    }

    public Double getTemperature () {
        return temperature;
    }

    public void setTemperature (Double temperature) {
        this.temperature = temperature;
    }

    public Long getIsChargeVoltage () {
        return isChargeVoltage;
    }

    public void setIsChargeVoltage (Long isChargeVoltage) {
        this.isChargeVoltage = isChargeVoltage;
    }

    public Double getVoltageInner () {
        return voltageInner;
    }

    public void setVoltageInner (Double voltageInner) {
        this.voltageInner = voltageInner;
    }

    public Double getVoltageOuter () {
        return voltageOuter;
    }

    public void setVoltageOuter (Double voltageOuter) {
        this.voltageOuter = voltageOuter;
    }

    public Double getAngleValue () {
        return angleValue;
    }

    public void setAngleValue (Double angleValue) {
        this.angleValue = angleValue;
    }

    public String getAngleDirection () {
        return angleDirection;
    }

    public void setAngleDirection (String angleDirection) {
        this.angleDirection = angleDirection;
    }

    public Date getClientTime () {
        return clientTime;
    }

    public void setClientTime (Date clientTime) {
        this.clientTime = clientTime;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }
}