package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReportDay;
import com.ecarinfo.survey.rm.CarReportDayRM;
import com.ecarinfo.survey.service.CarReportService;
import com.ecarinfo.survey.service.EventInfoService;
import com.ecarinfo.survey.view.CarReportToDayView;

public class CarReportDayJobTest extends SimpleTest {

	@Resource
	private CarReportService carReportService;

	@Resource
	private GenericService genericService;
	
	@Resource
	private EventInfoService eventInfoService;

	 
	@org.junit.Test
	public void testJob() {
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
			carReportDay.setSurveyNum(eventInfoService.findSurveyNumCount(2, preDayStr, preDayStr, carReportDay.getCarId()));
			if(carReportDay.getId()!= null && carReportDay.getId()>0 ){ //  修改
				genericService.update(carReportDay);
			}else{ //保存
				genericService.save(carReportDay);
			}
		}
		 
	}
	
	/**
	 * 查勘次数
	 */
	@org.junit.Test
	public void testSurveyNum(){
		List<CarReportDay> list=genericService.findAll(CarReportDay.class);
		Date preDay = DateUtils.getDateByDay(-1);
		//String preDayStr = DateUtils.dateToString(preDay, TimeFormatter.YYYY_MM_DD);  //日期为昨天的
		for (CarReportDay carReportDay : list) {
			
//			String preDayStr=DateUtils.dateToString(carReportDay.getClientDay(), TimeFormatter.YYYY_MM_DD);
//			String preDayStr="2013-08-30";
//			System.out.println("---eventInfoService.findSurveyNumCount(1, preDayStr, preDayStr, carReportDay.getCarId())-"+eventInfoService.findSurveyNumCount(1, preDayStr, preDayStr, carReportDay.getCarId()));
//			carReportDay.setSurveyNum(eventInfoService.findSurveyNumCount(2, preDayStr, preDayStr, carReportDay.getCarId()));
//			genericService.update(carReportDay);
		}
		
	}
}
