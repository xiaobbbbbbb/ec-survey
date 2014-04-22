package com.ecarinfo.survey.rm;
public class CarReportRM {
	public static final String tableName="car_report";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String carId="car_id";//
	public static final String surveyUid="survey_uid";//查勘员ID,多个时逗号分隔
	public static final String mileMeter="mile_meter";//本次里程
	public static final String avgSpeed="avg_speed";//平均车速
	public static final String totalTime="total_time";//累计行驶时长(秒)
	public static final String hypervelocity="hypervelocity";//超速记录
	public static final String fenceCounts="fence_counts";//围栏告警记录
	public static final String firstDataId="first_data_id";//第一条gps记录的ID
	public static final String lastDataId="last_data_id";//最后一条gps记录的ID
	public static final String startAddress="start_address";//起点位置
	public static final String endAddress="end_address";//
	public static final String updateTime="update_time";//
	public static final String createTime="create_time";//
	public static final String firstClientTime="first_client_time";//第一条gps记录时间
	public static final String lastClientTime="last_client_time";//最后上传数据时间
	public static final String status="status";//记录状态（0:未结束此行车，1：已经结束行车，2：已经修正数据）
}
