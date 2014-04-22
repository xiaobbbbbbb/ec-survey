package com.ecarinfo.survey.rm;
public class FenceAlarmInfoRM {
	public static final String tableName="fence_alarm_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String type="type";//报警类型，0为出围栏报警；1为进入报警，2为进出报警
	public static final String carId="car_id";//查勘车id
	public static final String carReportId="car_report_id";//行车记录ID
	public static final String deviceId="device_id";//gps设备id
	public static final String baiduLatitude="baidu_latitude";//
	public static final String baiduLongitude="baidu_longitude";//
	public static final String address="address";//报警地点
	public static final String surveyTel="survey_tel";//查勘员电话
	public static final String surveyName="survey_name";//查勘员名字
	public static final String updateTime="update_time";//
	public static final String createTime="create_time";//
	public static final String orgId="org_id";//服务机构ID
	public static final String fenceId="fence_id";//围栏ID
}
