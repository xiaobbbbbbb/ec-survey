package com.ecarinfo.survey.command;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ecarinfo.common.state.ECState;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.vo.BaseVO;

public abstract class AbstractCommand {
	private static final Logger logger = Logger.getLogger(AbstractCommand.class);
	
	@Resource
	protected OnlineManager<Channel,EcOnline> ecOnlineManager;
	
	@Resource
	protected GenericService genericService;
	
	@Resource
	protected ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Resource
	protected ThreadPoolTaskExecutor fiveServiceThreadPool;
	
	public static final String ACC_ON = "60";
	public static final String ACC_ON_RESPONSE = "$$"+ACC_ON+"#";
	
//	protected EcOnline online;
	
	public abstract Object execute(ChannelHandlerContext ctx,BaseVO requestMessage);	
	
	public Object execute(ChannelHandlerContext ctx,BaseVO requestMessage, boolean isNotice) {	
		return null;
	}	
	
//	public void setOnline(EcOnline online) {
//		this.online = online;
//	}

	/**
	 * 判断给定值是否要更新
	 * @param dest
	 * @param source
	 * @return
	 */
	public static <T> boolean needUpdate(T dest,T source) {
		boolean isTheSame = isTheSame(dest,source);
		return !isTheSame;
	}
	
	
	public static <T> boolean isTheSame(T dest,T source) {
		
		if(source == null && dest == null) {
			return true;
		}
		
		if(source != null && dest != null && dest.equals(source)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取客户端手机信息
	 * @param mobileInfo
	 * @return
	 */
	public static Map<String,String> getMobileInfo(String mobileInfo){
		HashMap<String, String> data = new HashMap<String,String>();		
		if(mobileInfo.length()>0 && (mobileInfo.indexOf(":")>0 && mobileInfo.indexOf("|")>0)){
			if(mobileInfo.indexOf("|")>0){
				for (String tmpInfo : mobileInfo.split("\\|")) {			
					if(tmpInfo.toLowerCase().startsWith("ver")){
						String[] infos = tmpInfo.split(":");						
						data.put("mobileVersion", infos[1]);
					} else if(tmpInfo.toLowerCase().startsWith("os_name")){
						String[] infos = tmpInfo.split(":");					
						data.put("mobileName", infos[1]);
					}
				}
			} else if(mobileInfo.indexOf(":")>0){
				String[] infos = mobileInfo.split(":");
				if(infos[0].toLowerCase().startsWith("ver")){			
					data.put("mobileVersion", infos[1]);
				}
			}
		}
		return data;
	}	
	
	protected static int getStateCode(boolean existsEmail,boolean existsCarNo) {
		if(existsEmail && existsCarNo) {
			return ECState.MULT_EMAIL_CARNO;
		}
		if(existsEmail) {
			return ECState.MULT_EMAIL;
		}
		if(existsCarNo) {
			return ECState.MULT_CARNO;
		}
		return ECState.OK;
	}
	
	protected static double fixDouble(double value,int bits) {
		BigDecimal bg = new BigDecimal(value);
		double f1 = bg.setScale(bits, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
	
	protected EcOnline getEcOnline(OnlineManager<Channel,EcOnline> ecOnlineManager,Channel channel,String imei,String alarmType) {
		EcOnline online = ecOnlineManager.get(channel);
		if(online == null) {
			Channel delete = null;
			for(Map.Entry<Channel, EcOnline> en:ecOnlineManager.getAll().entrySet()) {
				EcOnline ol = en.getValue();
				if(ol != null && ol.getDeviceCode().equals(imei)) {
					online = ol;
					delete = en.getKey();
					ecOnlineManager.add(channel, online);
					break;
				}
			}
			if(delete != null) {
				if(delete.isConnected()) {
					delete.close();
				}
				ecOnlineManager.remove(delete);
			}
		}
		boolean newReport = false;
		if(online == null) {
			DeviceInfo device = genericService.findOneByAttr(DeviceInfo.class, DeviceInfoRM.code, imei);
			if(device != null) {
				//更新数据上传时间
				genericService.updateWithCriteria(DeviceInfo.class, new Criteria()
				.update(DeviceInfoRM.online, true)
				.eq(DeviceInfoRM.id, device.getId()));
				CarInfo carInfo = genericService.findOneByAttr(CarInfo.class, CarInfoRM.deviceId, device.getId());
				if(carInfo != null) {
					//查找上一条行车记录，以使行车记录完整
					CarReport report = genericService.findOne(CarReport.class, new Criteria()
					.eq(CarReportRM.carId, carInfo.getId())
					.orderBy(CarReportRM.lastClientTime, OrderType.DESC));
					if(report == null || report.getStatus() == 1) {
						report = createNewReport(carInfo.getId());
						newReport = true;
					}
					online = createEcOnline(channel,report,device.getId(),carInfo.getId(),device.getCode());
					ecOnlineManager.add(channel, online);
				}
			}
		} else {
			online.setDeviceDatalastUpdateTime(System.currentTimeMillis());
		}
		
		if(alarmType != null && ACC_ON.equals(alarmType)) {//ACC ON
			logger.info("acc on.imei="+online.getDeviceCode());
			//新建一条行车记录
			if(!newReport) {
				//结束上次行车
				genericService.updateWithCriteria(CarReport.class,  new Criteria()
				.update(CarReportRM.status, 1)
				.update(CarReportRM.lastClientTime, DateUtils.dateToString(online.getLastClientTime(), TimeFormatter.FORMATTER1))
				.eq(CarReportRM.id,online.getCarReportId()));
				CarReport report = createNewReport(online.getCarId());
				online = createEcOnline(channel,report,online.getDeviceId(),online.getCarId(),online.getDeviceCode());
				ecOnlineManager.add(channel, online);
			}
		}
		
		return online;
	}
	
	protected EcOnline createEcOnline(Channel channel,CarReport report,Integer deviceId,Integer carId,String imei) {
		EcOnline online = new EcOnline(channel, deviceId, carId, imei, System.currentTimeMillis(), System.currentTimeMillis(), null);
		online.setCarReportId(report.getId());
		online.setFirstDeviceDataId(report.getFirstDataId());
		online.setLastDeviceDataId(report.getLastDataId());
		if(report.getLastClientTime() != null) {
			online.setLastUpdateTime(report.getLastClientTime().getTime());
		}
		if(report.getFirstClientTime() != null) {
			online.setFirstClientTime(report.getFirstClientTime().getTime());
		}
		return online;
	}
	
	protected CarReport createNewReport(Integer carId) {
		CarReport report = new CarReport();
		report.setCarId(carId);
		Date now = new Date();
		report.setUpdateTime(now);
		report.setCreateTime(now);
		genericService.save(report);
		return report;
	}
}
