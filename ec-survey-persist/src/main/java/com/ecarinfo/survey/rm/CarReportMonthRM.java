package com.ecarinfo.survey.rm;
public class CarReportMonthRM {
	public static final String tableName="car_report_month";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String carId="car_id";//车辆id
	public static final String surveyNum="survey_num";//查勘次数
	public static final String trafficNum="traffic_num";//违章次数
	public static final String totalMonthMile="total_month_mile";//月里程
	public static final String totalFenceCounts="total_fence_counts";//月围栏告警纪录
	public static final String totalHypervelocity="total_hypervelocity";//月超速纪录
	public static final String month="month";//月份
	public static final String createTime="create_time";//创建时间
}
