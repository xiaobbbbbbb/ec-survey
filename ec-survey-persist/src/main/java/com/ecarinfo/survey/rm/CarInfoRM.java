package com.ecarinfo.survey.rm;
public class CarInfoRM {
	public static final String tableName="car_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String deviceId="device_id";//设备ID
	public static final String carNo="car_no";//车牌号
	public static final String areaId="area_id";//区域id
	public static final String carModel="car_model";//车型(字符串)
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
	public static final String orgId="org_id";//机构ID
	public static final String onbind="onbind";//是否绑定，1未绑定，0未绑定
	public static final String inspectionTime="inspection_time";//年检到期日
	public static final String disabled="disabled";//状态，默认1为有效
	public static final String enregister="enregister";//登记日期
	public static final String status="status";//状态：0为待命中，1为工作中，2为离线
}
