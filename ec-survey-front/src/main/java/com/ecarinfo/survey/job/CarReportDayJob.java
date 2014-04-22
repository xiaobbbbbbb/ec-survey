package com.ecarinfo.survey.job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReportDay;
import com.ecarinfo.survey.rm.CarReportDayRM;
import com.ecarinfo.survey.service.CarReportService;
import com.ecarinfo.survey.service.EventInfoService;
import com.ecarinfo.survey.view.CarReportToDayView;

@Component("carReportDayJob")
public class CarReportDayJob {
	private static final Logger logger = Logger.getLogger(DeviceDataJob.class);
	@Resource
	private GenericService genericService;
	 
	@Resource
	private CarReportService carReportService;
	
	@Resource
	private EventInfoService eventInfoService;
	private Lock lock = new ReentrantLock();

	public void execute() {
		logger.info("carReportDayJob.execute");
		if (lock.tryLock()) {
			try {
				Date preDay = DateUtils.getDateByDay(-1);
				String preDayStr = DateUtils.dateToString(preDay, TimeFormatter.YYYY_MM_DD);  //日期为昨天的
				List<CarReportToDayView> list = carReportService.findCarReportToDayByCriteria(preDayStr,preDayStr);
				CarReportDay carReportDay = null;
				for (CarReportToDayView ctv : list) {
					Integer carId=ctv.getCarIdD();
					carReportDay = genericService.findOne(CarReportDay.class, new Criteria().eq(CarReportDayRM.carId, carId).eq(CarReportDayRM.clientDay, preDayStr));
					if(carReportDay == null) {
						carReportDay=new CarReportDay();
					}
					carReportDay.setCarId(carId);
					carReportDay.setClientDay(DateUtils.stringToDate(preDayStr, "yyyy-MM-dd"));
					carReportDay.setCreateTime(new Date());
					carReportDay.setTotalDayMile(ctv.getMileMeterD());
					carReportDay.setTotalFenceCounts(ctv.getFenceCountsD());
					carReportDay.setTotalHypervelocity(ctv.getHypervelocityD());
					carReportDay.setSurveyNum(eventInfoService.findSurveyNumCount(2, preDayStr, preDayStr, carId));  //查勘次数
					if(carReportDay.getId()!= null && carReportDay.getId()>0 ){ //  修改
						genericService.update(carReportDay);
					}else{ //保存
						genericService.save(carReportDay);
					}
				}
			} catch (Exception e) {
				logger.error("行车日报告统计失败", e);
			}
		}
	}
}
