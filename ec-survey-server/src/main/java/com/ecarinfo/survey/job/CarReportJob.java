package com.ecarinfo.survey.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.BaiduUtils;
import com.ecarinfo.common.utils.BaiduUtils.BaiduFromGpsResult;
import com.ecarinfo.common.utils.BaiduUtils.BaiduLocation;
import com.ecarinfo.common.utils.BaiduUtils.BaiduResult;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.po.FenceAlarmInfo;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.DeviceDataRM;
import com.ecarinfo.survey.rm.FenceAlarmInfoRM;

@Component("carReportJob")
public class CarReportJob {
	private static final Logger logger = Logger.getLogger(CarReportJob.class);
	@Resource
	private GenericService genericService;
	private static final String AK = PropUtil.getProp("server.properties", "baidu_key");
	public void execute() {
		logger.info("CarReportJob.execute");
		while(true) {
			//查找近20分钟内没有上传数据的
//			String fromTime = DateUtils.plusMins(new Date(), -20, TimeFormatter.FORMATTER1);
			ECPage<CarReport> page = PagingManager.list(genericService, CarReport.class, new Criteria()
//			.lessThen(CarReportRM.updateTime,fromTime)
			.eq(CarReportRM.status, 1)
			.setPage(1, ECPage.DEFAULT_SIZE));
			
			if(page.getList().size() == 0) {
				break;
			}
			for(CarReport report:page.getList()) {
				if(report != null) {
					//数据汇总
					List<DeviceData> datas = genericService.findList(DeviceData.class, new Criteria()
					.eq(DeviceDataRM.carReportId, report.getId())
					.orderBy(DeviceDataRM.clientTime, OrderType.ASC)
					);
					Float mileMeter = 0f;
					long totalTime = 0l;
					if(datas.size() > 0) {
						totalTime += datas.get(datas.size() - 1).getClientTime().getTime()/1000 -  datas.get(0).getClientTime().getTime()/1000;
					}
					Long fenceAlarmCount = genericService.count(FenceAlarmInfo.class, new Criteria()
					.eq(FenceAlarmInfoRM.carReportId,report.getId()));//围栏告警记录
					
					int hypervelocity = 0;//超速记录
					for(DeviceData dd:datas) {
						mileMeter += dd.getMileMeter();
						if(dd.getSpeed() > 120) {
							hypervelocity ++;
						}
					}
					report.setTotalTime(totalTime);
					report.setFenceCounts(fenceAlarmCount.intValue());
					report.setHypervelocity(hypervelocity);
					report.setMileMeter(mileMeter);
					
					if(report.getFirstDataId() != null && report.getLastDataId() != null) {
						DeviceData firstData = genericService.findByPK(DeviceData.class, report.getFirstDataId());
						DeviceData lastData = genericService.findByPK(DeviceData.class, report.getLastDataId());
						
						if(firstData != null) {
							BaiduFromGpsResult fResult = BaiduUtils.getBaiduFromGps(AK, firstData.getGpsLongitude(), firstData.getGpsLatitude());
							if(fResult != null && fResult.getLocation() != null) {
								BaiduResult bres = BaiduUtils.getByLocation(AK, fResult.getLocation().getLng(), fResult.getLocation().getLat());
								if(bres != null && bres.getResult() != null) {
									BaiduLocation bl = bres.getResult();
									report.setStartAddress(bl.getFormatted_address());
									report.setStatus(2);
								}
								
							} 
						}
						
						if(lastData != null) {
							BaiduFromGpsResult lResult = BaiduUtils.getBaiduFromGps(AK, lastData.getGpsLongitude(), lastData.getGpsLatitude());
							if(lResult != null && lResult.getLocation() != null) {
								BaiduResult bres = BaiduUtils.getByLocation(AK, lResult.getLocation().getLng(), lResult.getLocation().getLat());
								if(bres != null && bres.getResult() != null) {
									BaiduLocation bl = bres.getResult();
									report.setEndAddress(bl.getFormatted_address());
									report.setStatus(2);
								}
								
							} 
						}
						
					}
					report.setStatus(1);
					genericService.update(report);
				} 
			}
		}
	}
}
