package com.ecarinfo.survey.rm;
public class CarReportDayRM {
	public static final String tableName="car_report_day";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String carId="car_id";//车辆id
	public static final String surveyNum="survey_num";//每日勘察次数
	public static final String trafficNum="traffic_num";//违章次数
	public static final String totalDayMile="total_day_mile";//日总里程
	public static final String avgSpeed="avg_speed";//日平均速度
	public static final String totalFenceCounts="total_fence_counts";//围栏告警纪录
	public static final String totalHypervelocity="total_hypervelocity";//超速纪录
	public static final String clientDay="client_day";//客户端时间
	public static final String createTime="create_time";//创建时间
}
