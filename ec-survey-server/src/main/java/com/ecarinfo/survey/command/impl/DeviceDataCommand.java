package com.ecarinfo.survey.command.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.CRC16;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.vo.BaseVO;
import com.ecarinfo.survey.vo.GprsVO;

@Component("DeviceDataCommand")
public class DeviceDataCommand extends AbstractCommand {
	private static final Logger logger = Logger.getLogger(DeviceDataCommand.class);
	
	@Resource
	private OnlineManager<Channel,EcOnline> ecOnlineManager;
	
	
//	@Resource
//	private RedisCache redisCache;
	/**
	 * $$(2 Bytes) + Len(2 Bytes) + IMEI(15 Bytes) + | + AlarmType(2 Bytes) + GPRMC + | 
            * + PDOP + | + HDOP + | + VDOP + | + Status(12 Bytes) + | + RTC(14 Bytes) + | + Voltage(8 Bytes) + | 
            * + ADC(8 Bytes) + | + LACCI(8 Bytes) + | + Temperature(4 Bytes) | +Mile-meter+| +Serial(4 Bytes) + | 
            * +RFID(10Bytes)+|+ Checksum (4 Byte) + \r\n(2 Bytes)
	 */
	@Override
	public Object execute(ChannelHandlerContext ctx, BaseVO requestMessage) {
		System.err.println("DeviceDataCommand invoke.");
		GprsVO vo = (GprsVO)requestMessage;
		
		EcOnline online = getEcOnline(ecOnlineManager,ctx.getChannel(), vo.getImei(),vo.getAlarmType());
		if(online == null) {
			logger.error("no device found imei = "+vo.getImei());
			return null;
		}
		
		DeviceData data = getDeviceData(vo,null,online.getDeviceId());
		
		if(online.getCarId() != null) {
			data.setCarReportId(online.getCarReportId());
		}
		genericService.save(data);
		if(online.getFirstDeviceDataId() == null) {
			online.setFirstDeviceDataId(data.getId());
			genericService.updateWithCriteria(CarReport.class, new Criteria()
			.update(CarReportRM.firstDataId, data.getId())
			.update(CarReportRM.updateTime, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1))
			.update(CarReportRM.firstClientTime, DateUtils.dateToString(data.getClientTime(), TimeFormatter.FORMATTER1))
			.update(CarReportRM.lastClientTime, DateUtils.dateToString(data.getClientTime(), TimeFormatter.FORMATTER1))
			.eq(CarReportRM.id, online.getCarReportId()));
		} else {
			online.setLastDeviceDataId(data.getId());
			online.setLastClientTime(data.getClientTime().getTime());
		}
		if(ACC_ON.equals(vo.getAlarmType())) {
			return ACC_ON_RESPONSE;
		}
		return null;
	}
	
	private DeviceData getDeviceData(GprsVO vo,DeviceData data,Integer deviceId) {
		if(data == null) {
			data = new DeviceData();
		}
		data.setImei(vo.getImei());
		
		if(deviceId != null) {
			data.setDeviceId(deviceId);
		} else {
			logger.error("no device found.imei="+vo.getImei());
		}
		data.setAngleDirection(vo.getGprmc().getWe());
		if(vo.getGprmc().getDirection() != null && vo.getGprmc().getDirection().trim().length() > 0) {
			data.setAngleValue(fixDouble(Double.parseDouble(vo.getGprmc().getDirection()),3));
		} else {
			data.setAngleValue(0.0);
		}
		String lacc = vo.getLacci();
		data.setAreaNo(lacc.substring(0,5));
		data.setGridNo(lacc.substring(5));
		data.setBaiduLatitude(0d);//TODO
		data.setBaiduLongitude(0d);//TODO
		
		data.setClientTime(DateUtils.plusHours(DateUtils.stringToDate(vo.getRtc(), DateUtils.TimeFormatter.FORMATTER11),8));
		
		data.setGeohash(data.getGpsLongitude()+" "+data.getGpsLatitude());//TODO
		data.setGpsLatitude(fixDouble(CRC16.getLatitude(vo.getGprmc().getLatitude(), vo.getGprmc().getNs()),6));
		data.setGpsLongitude(fixDouble(CRC16.getLongitude(vo.getGprmc().getLongitude(), vo.getGprmc().getWe()),6));
		
		data.setGpsTime(getDate(vo.getGprmc().getDate()+vo.getGprmc().getTime()));
		
		data.setIsChargeVoltage(vo.getVoltage().startsWith("1")?1l:0l);
		data.setMileMeter((int)(1000*Double.parseDouble(vo.getOdoMeter())));
		data.setSpeed(fixDouble(Double.parseDouble(vo.getGprmc().getSpeed()),2));
		data.setTemperature(fixDouble(Double.parseDouble(vo.getTemperature()),2));
		data.setUpdateTime(new Date());
		data.setVoltageOuter(fixDouble(Double.parseDouble(vo.getVoltage().substring(4))/100,2));
		data.setVoltageInner(fixDouble(Double.parseDouble(vo.getVoltage().substring(1, 4))/100,2));
		return data;
	}
	
	private static final Date getDate(String s ) {
		return DateUtils.plusHours(DateUtils.stringToDate(s, TimeFormatter.FORMATTER12), 8);
	}
	
}
