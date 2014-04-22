package com.ecarinfo.survey.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.po.TagInfo;
import com.ecarinfo.survey.po.TagOnlineData;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.rm.TagInfoRM;
import com.ecarinfo.survey.rm.TagOnlineDataRM;

/**
 * 更新设备的在线状态
 * @author ecxiaodx
 *
 */
@Component("deviceDataJob")
public class DeviceDataJob {
	private static final Logger logger = Logger.getLogger(DeviceDataJob.class);
	@Resource
	private GenericService genericService;
	private static final int CAR_STATUS_INTERVAL = 15*60*1000;//15分钟不提交请求，则服务器将其踢下线,更新在线状态，并从缓存中删除
	@Resource
	private OnlineManager<Channel,EcOnline> ecOnlineManager;
	
	public void execute() {
		logger.info("DeviceDataJob.execute");
		
		try {
			Map<Channel,EcOnline> onlines = ecOnlineManager.getAll();
			List<Channel> channels = new ArrayList<Channel>();
			for(Map.Entry<Channel,EcOnline> en:onlines.entrySet()) {
				EcOnline online = en.getValue();
				if(System.currentTimeMillis() - online.getDeviceDatalastUpdateTime() > CAR_STATUS_INTERVAL) {
					channels.add(online.getChannel());
					
					processDeviceData(online,true);
					
					processTagInfo(online,true);
				} else {
					//在线数据定时入库
					processDeviceData(online,false);
					processTagInfo(online,false);
				}
			}
			
			closeChannel(channels);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private void processDeviceData(EcOnline online,boolean offLine) {
		if(offLine) {
			//将设备下线
			genericService.updateWithCriteria(DeviceInfo.class, new Criteria()
			.update(DeviceInfoRM.online, false)
			.eq(DeviceInfoRM.id, online.getDeviceId()));
		}
			
		//修改行车记录最后更新时间
		genericService.updateWithCriteria(CarReport.class, new Criteria()
		.update(CarReportRM.lastDataId, online.getLastDeviceDataId())
		.update(CarReportRM.updateTime, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1))
		.update(CarReportRM.lastClientTime,DateUtils.dateToString(online.getLastClientTime(), TimeFormatter.FORMATTER1))
		.eq(CarReportRM.id, online.getCarReportId()));
	}
	
	private void processTagInfo(EcOnline online,boolean offLine) {
		if(online.getTagMap().size() > 0) {
			for(Map.Entry<String, TagOnlineData> en:online.getTagMap().entrySet()) {
				String tagId = en.getKey();
				TagOnlineData data = en.getValue();
				if(offLine) {
					//电子标签离线
					genericService.updateWithCriteria(TagInfo.class, new Criteria()
					.update(TagInfoRM.online, false)
					.eq(TagInfoRM.serialNo, tagId));
				}
				
				//修改电子标签下线时间
				genericService.updateWithCriteria(TagOnlineData.class, new Criteria()
				.update(TagOnlineDataRM.updateTime, DateUtils.dateToString(data.getUpdateTime(), TimeFormatter.FORMATTER1))
				.eq(TagOnlineDataRM.id, data.getId())
				);
			}
		}
	}
	
	private void closeChannel(List<Channel> channels) {
		for(Channel channel:channels) {
			if(channel.isConnected()) {
				channel.close();
			}
			ecOnlineManager.remove(channel);
		}
	}
}
