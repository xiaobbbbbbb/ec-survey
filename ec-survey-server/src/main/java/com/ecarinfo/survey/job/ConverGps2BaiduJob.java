package com.ecarinfo.survey.job;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.BaiduUtils;
import com.ecarinfo.common.utils.BaiduUtils.BaiduFromGpsResult;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.dao.FenceInfoDao;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.po.FenceAlarmInfo;
import com.ecarinfo.survey.po.FenceInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.DeviceDataRM;
import com.ecarinfo.survey.rm.FenceAlarmInfoRM;
import com.ecarinfo.survey.vo.FenceVO;

@Component("converGps2BaiduJob")
public class ConverGps2BaiduJob {
	private static final Logger logger = Logger.getLogger(ConverGps2BaiduJob.class);
	@Resource
	private GenericService genericService;
	@Resource
	private FenceInfoDao fenceInfoDao;
	
	private static final String AK = PropUtil.getProp("server.properties", "baidu_key");
	private Lock lock = new ReentrantLock();
	public void execute() {
		if(lock.tryLock()) {
			try{
				logger.info("ConverGps2BaiduJob.execute");
				while(true) {
					ECPage<DeviceData> page = PagingManager.list(genericService, DeviceData.class, new Criteria()
					.orderBy(DeviceDataRM.id, OrderType.ASC)
					.eq(DeviceDataRM.status, 0)
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
							.update(DeviceDataRM.status, 1)
							.eq(DeviceDataRM.id, d.getId()));
						} else {
							logger.info("error="+result.getError());
						}
						CarInfo carInfo = genericService.findOneByAttr(CarInfo.class, CarInfoRM.deviceId, d.getDeviceId());
						processFenceAlarm(carInfo,d.getCarReportId(),d);
					}
				}
			}finally {
				lock.unlock();
			}
		}
	}
	
	/**
	 * 电子围栏报警相关
	 */
	private void processFenceAlarm(CarInfo carInfo,Long reportId,DeviceData data) {
		List<FenceInfo> fences = fenceInfoDao.findFenceByCarId(carInfo.getId());
		Date now = new Date();
		for(FenceInfo fence:fences) {
			int type = fence.getAlarmType();
			FenceVO fvo = new FenceVO();
			fvo.init(fence);
			
			Polygon polygon = new Polygon();
			for(Point p:fvo.getPoints()) {
				polygon.addPoint(p.x, p.y);
			}
			
			//是否在报警时间段内
			if(!isAlermTime(fence)) {
				continue;
			}
			
			boolean alarm = false;
			if(type == 0) {//出围栏报警
				if(!polygon.contains((int)(data.getBaiduLongitude()*1000000),(int)(data.getBaiduLatitude()*1000000))) {
					alarm = true;
				}
			}
			if(type == 1) {//进围栏报警
				if(polygon.contains((int)(data.getBaiduLongitude()*1000000),(int)(data.getBaiduLatitude()*1000000))) {
					alarm = true;
				}
			}
			// 过滤重复报警（一次行车只报一次）
			FenceAlarmInfo fai = genericService.findOne(FenceAlarmInfo.class, new Criteria()
			.eq(FenceAlarmInfoRM.carId, carInfo.getId())
			.eq(FenceAlarmInfoRM.type, type)
			.eq(FenceAlarmInfoRM.carReportId, reportId)
			);
			if(alarm && fai == null) {
				fai = new FenceAlarmInfo();
				fai.setCarId(carInfo.getId());
				fai.setDeviceId(data.getDeviceId());
				fai.setType(type);
				fai.setBaiduLatitude(data.getBaiduLatitude());
				fai.setBaiduLongitude(data.getBaiduLongitude());
				fai.setUpdateTime(now);
				fai.setCreateTime(now);
				fai.setOrgId(carInfo.getOrgId());
				fai.setCarReportId(data.getCarReportId());
				fai.setFenceId(fence.getId());
				genericService.save(fai);
			}
		}
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
