package com.ecarinfo.survey.server;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ecarinfo.common.utils.BaiduUtils;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.BaiduUtils.BaiduFromGpsResult;
import com.ecarinfo.common.utils.BaiduUtils.BaiduLocation;
import com.ecarinfo.common.utils.BaiduUtils.BaiduResult;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.po.FenceAlarmInfo;
import com.ecarinfo.survey.po.FenceInfo;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.DeviceDataRM;
import com.ecarinfo.survey.rm.FenceAlarmInfoRM;
import com.ecarinfo.survey.vo.FenceVO;
import com.ecarinfo.test.ECTest;

public class Gps2BaiduTest extends ECTest {
	@Resource
	private GenericService genericService;
	private static final String AK = PropUtil.getProp("server.properties", "baidu_key");
	@Test
	public void test() {
		logger.info("DeviceDataJob.execute");
		while(true) {
			ECPage<DeviceData> page = PagingManager.list(genericService, DeviceData.class, new Criteria()
			.orderBy(DeviceDataRM.id, OrderType.ASC)
			.eq(DeviceDataRM.baiduLatitude, 0)
			.setPage(1, ECPage.DEFAULT_SIZE));
			
			logger.info("page.getList().size()="+page.getList().size());
			if(page.getList().size() == 0) {
				break;
			} 
			for(DeviceData d:page.getList()) {
				BaiduFromGpsResult result = BaiduUtils.getBaiduFromGps(AK, d.getGpsLongitude(), d.getGpsLatitude());
				if(result.getLocation() != null) {
					logger.info("error="+result.getError()+",lat,lng=("+result.getLocation().getLat()+","+result.getLocation().getLng()+")");
					d.setBaiduLatitude(result.getLocation().getLat());
					d.setBaiduLongitude(result.getLocation().getLng());
					genericService.updateWithCriteria(DeviceData.class, new Criteria()
					.update(DeviceDataRM.baiduLatitude, d.getBaiduLatitude())
					.update(DeviceDataRM.baiduLongitude, d.getBaiduLongitude())
					.eq(DeviceDataRM.id, d.getId()));
				} else {
					logger.info("error="+result.getError());
				}
			}
		}
	}
	
	@Test
	public void test2() {
		while(true) {
			//查找近20分钟内没有上传数据的
			String fromTime = DateUtils.plusMins(new Date(), -20, TimeFormatter.FORMATTER1);
			ECPage<CarReport> page = PagingManager.list(genericService, CarReport.class, new Criteria()
			.lessThen(CarReportRM.updateTime,fromTime)
			.isNull(CarReportRM.mileMeter)
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
							if(fResult.getLocation() != null) {
								BaiduResult bres = BaiduUtils.getByLocation(AK, fResult.getLocation().getLng(), fResult.getLocation().getLat());
	//							if(bres.getStatus())
								BaiduLocation bl = bres.getResult();
								if(bl != null) {
									report.setStartAddress(bl.getFormatted_address());
								}
							} 
						}
						
						if(lastData != null) {
							BaiduFromGpsResult lResult = BaiduUtils.getBaiduFromGps(AK, lastData.getGpsLongitude(), lastData.getGpsLatitude());
							if(lResult.getLocation() != null) {
								BaiduResult bres = BaiduUtils.getByLocation(AK, lResult.getLocation().getLng(), lResult.getLocation().getLat());
//								if(bres.getStatus())
								BaiduLocation bl = bres.getResult();
								if(bl != null) {
									report.setEndAddress(bl.getFormatted_address());
								}
							} 
						}
						
					}
					
					genericService.update(report);
				} 
			}
		}
	}
	
	@Test
	public void test3() {
		Float lng = 113.960523f;
		Float lat = 22.547957f;
		FenceInfo fence = genericService.findByPK(FenceInfo.class, 5);
		int type = fence.getAlarmType();
		FenceVO fvo = new FenceVO();
		fvo.init(fence);
		Polygon polygon = new Polygon();
		for(Point p:fvo.getPoints()) {
			polygon.addPoint(p.x, p.y);
		}

		//是否在报警时间段内
		if(!isAlermTime(fence)) {
			System.err.println("not in the alermTime.");
			return;
		}
		
		boolean alarm = false;
		if(type == 0) {//出围栏报警
			if(!polygon.contains((int)(lng*1000000),(int)(lat*1000000))) {
				alarm = true;
			}
		}
		if(type == 1) {//进围栏报警
			if(polygon.contains((int)(lng*1000000),(int)(lat*1000000))) {
				alarm = true;
			}
		}
		System.err.println("alarm="+alarm);
	}
	
	/**
	 * 当前是否报警时间
	 * @param fence
	 * @return
	 */
	private boolean isAlermTime(FenceInfo fence) {
		String now = DateUtils.dateToString(System.currentTimeMillis(), TimeFormatter.FORMATTER5);
		return now.compareTo(fence.getAlarmStartTime()) > 0 && now.compareTo(fence.getAlarmEndTime()) < 0;
	}
	
}
