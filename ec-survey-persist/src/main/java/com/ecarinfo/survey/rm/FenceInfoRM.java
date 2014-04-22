package com.ecarinfo.survey.rm;
public class FenceInfoRM {
	public static final String tableName="fence_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String name="name";//
	public static final String alarmType="alarm_type";//报警类型，0为出围栏报警；1为进入报警，
	public static final String alarmStartTime="alarm_start_time";//报警开始时间
	public static final String alarmEndTime="alarm_end_time";//报警结束时间
	public static final String points="points";//电子围拦坐标点(百度经纬度)
	public static final String gpsPoints="gps_points";//gps经纬度点集
	public static final String description="description";//
	public static final String grade="grade";//当前地图的级别
	public static final String centerPoints="center_points";//当前地图的中心点
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
	public static final String orgId="org_id";//服务机构ID
}
