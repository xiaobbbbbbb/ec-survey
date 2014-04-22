package com.ecarinfo.survey.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReportMonth;
import com.ecarinfo.survey.rm.CarReportMonthRM;
import com.ecarinfo.survey.service.CarReportDayService;
import com.ecarinfo.survey.view.CarReportDayToMonthView;

public class CarReportMonthJobTest extends SimpleTest {

	@Resource
	private CarReportDayService carReportDayService;

	@Resource
	private GenericService genericService;

	 
	@org.junit.Test
	public void testJob() {
//		//获取前月的第一天
//        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
//        cal_1.add(Calendar.MONTH, -1);
//        cal_1.set(Calendar.DAY_OF_MONTH,1);//开始日期都是每个月的1月1号
//        String startTime = DateUtils.dateToString(cal_1.getTime(), TimeFormatter.YYYY_MM_DD);
//        //获取前月的最后一天
//        Calendar cale = Calendar.getInstance();   
//        cale.set(Calendar.DAY_OF_MONTH,0);//本月第一天减去1就是上个月的最后一天
//        String endTime = DateUtils.dateToString(cale.getTime(), TimeFormatter.YYYY_MM_DD);
        
		
		 //获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String startTime= DateUtils.dateToString(c.getTime(), TimeFormatter.YYYY_MM_DD);
        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String endTime =DateUtils.dateToString(ca.getTime(), TimeFormatter.YYYY_MM_DD);
        
        
		String preMonthStr = DateUtils.dateToString(c.getTime(), TimeFormatter.FORMATTER7);
		List<CarReportDayToMonthView> list = carReportDayService.findCarReportDayToMonthCriteria(preMonthStr,startTime,endTime);
		CarReportMonth carReportMonth = null;
		for (CarReportDayToMonthView crv : list) {
			//创建上个月各个车的月行车记录
			Integer carId=crv.getCarIdM();
			carReportMonth = genericService.findOne(CarReportMonth.class, new Criteria().eq(CarReportMonthRM.carId, carId).like(CarReportMonthRM.month, preMonthStr));
			if(carReportMonth == null){
				carReportMonth = new CarReportMonth();
			}
			carReportMonth.setCarId(carId);
			carReportMonth.setCreateTime(new Date());
			carReportMonth.setMonth(preMonthStr);
			carReportMonth.setSurveyNum(crv.getSurveyNumM());
			carReportMonth.setTotalFenceCounts(crv.getTotalFenceCountsM());
			carReportMonth.setTotalHypervelocity(crv.getTotalHypervelocityM());
			carReportMonth.setTrafficNum(crv.getTrafficNumM());
			carReportMonth.setTotalMonthMile(crv.getTotalDayMileM());
			if(carReportMonth.getId()!= null && carReportMonth.getId()>0 ){ //  修改
				genericService.update(carReportMonth);
			}else{ //保存
				genericService.save(carReportMonth);
			}
		}
	}
}
