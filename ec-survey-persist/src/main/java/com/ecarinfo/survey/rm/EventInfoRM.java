package com.ecarinfo.survey.rm;
public class EventInfoRM {
	public static final String tableName="event_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String no="no";//报案号
	public static final String name="name";//
	public static final String carNo="car_no";//车牌号
	public static final String phoneNo="phone_no";//手机号
	public static final String longitude="longitude";//经度
	public static final String latitude="latitude";//纬度
	public static final String geohash="geohash";//GEOHASH
	public static final String areaId="area_id";//区域ID
	public static final String opUid="op_uid";//录单员
	public static final String surveyUid="survey_uid";//查勘员，逗号分隔
	public static final String orgId="org_id";//所属机构ID
	public static final String surveyCarId="survey_car_id";//查勘车id
	public static final String address="address";//详细地址
	public static final String status="status";//案件状态（0待调度；1已调度；2已完成；3手动终结；4超时终结。）
	public static final String eventDesc="event_desc";//案件描述
	public static final String processTime="process_time";//案件调度时间
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
	public static final String cancelpeople="cancelpeople";//取消人
	public static final String cancelreason="cancelreason";//取消原因
	public static final String carLatitude="car_latitude";// 调度时车辆纬度
	public static final String carLongitude="car_longitude";//调度时车辆经度
}
