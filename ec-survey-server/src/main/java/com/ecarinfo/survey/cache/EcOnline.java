package com.ecarinfo.survey.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.ecarinfo.survey.po.TagOnlineData;

/**
 * 在线数据(本地缓存)
 * @author ecxiaodx
 *
 */
public class EcOnline implements Serializable {

	private static final long serialVersionUID = 2206831880467423980L;

	protected static Logger logger = Logger.getLogger(EcOnline.class);

	private Channel channel;
	private Integer deviceId;
	private String deviceCode;
	private Long carReportId;//行车记录ID
	private Long firstDeviceDataId;//第一条gps数据
	private Long lastDeviceDataId;//最后一条gps数据
	private long firstClientTime;//第一条deviceData时间
	private long lastClientTime;//最后一条deviceData时间
	private Integer carId;
	private long lastUpdateTime;//缓存最后更新时间
	private long deviceDatalastUpdateTime;
	private long createTime;
	private Date logoutTime;
	
	//key=tagNo,value=TagOnlineData
	private Map<String,TagOnlineData> tagMap = new HashMap<String, TagOnlineData>();
	
//	private Double lastLat;
//	private Double lastLng;
	
	private Lock lock;
	
	public EcOnline(Channel channel, Integer deviceId,
			Integer carId, String deviceCode, long lastUpdateTime,long createTime,Lock lock) {
		this.channel = channel;
		this.deviceId = deviceId;
		this.carId = carId;
		this.deviceCode = deviceCode;
		this.lastUpdateTime = lastUpdateTime;
		this.createTime = createTime;
		this.lock = lock;
	}

	public Long getFirstDeviceDataId() {
		return firstDeviceDataId;
	}

	public void setFirstDeviceDataId(Long firstDeviceDataId) {
		this.firstDeviceDataId = firstDeviceDataId;
	}

	public long getLastClientTime() {
		return lastClientTime;
	}

	public long getFirstClientTime() {
		return firstClientTime;
	}

	public void setFirstClientTime(long firstClientTime) {
		this.firstClientTime = firstClientTime;
	}

	public void setLastClientTime(long lastClientTime) {
		this.lastClientTime = lastClientTime;
	}

	public Long getLastDeviceDataId() {
		return lastDeviceDataId;
	}

	public void setLastDeviceDataId(Long lastDeviceDataId) {
		this.lastDeviceDataId = lastDeviceDataId;
	}

	public Long getCarReportId() {
		return carReportId;
	}

	public void setCarReportId(Long carReportId) {
		this.carReportId = carReportId;
	}


	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public long getDeviceDatalastUpdateTime() {
		return deviceDatalastUpdateTime;
	}


	public void setDeviceDatalastUpdateTime(long deviceDatalastUpdateTime) {
		this.deviceDatalastUpdateTime = deviceDatalastUpdateTime;
	}


	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Map<String, TagOnlineData> getTagMap() {
		return tagMap;
	}

	public void setTagMap(Map<String, TagOnlineData> tagMap) {
		this.tagMap = tagMap;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	
}
