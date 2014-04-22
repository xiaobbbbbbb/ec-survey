package com.ecarinfo.survey.rm;
public class DeviceDataRM {
	public static final String tableName="device_data";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String deviceId="device_id";//
	public static final String imei="imei";//imei
	public static final String carReportId="car_report_id";//行车id(从accon-accoff间只产生一个id)
	public static final String gpsTime="gps_time";//gps 时间
	public static final String gpsLongitude="gps_longitude";//经度
	public static final String gpsLatitude="gps_latitude";//纬度
	public static final String baiduLongitude="baidu_longitude";//经度
	public static final String baiduLatitude="baidu_latitude";//
	public static final String geohash="geohash";//geohash
	public static final String areaNo="area_no";//基站区域代码
	public static final String gridNo="grid_no";//基站栅格号
	public static final String mileMeter="mile_meter";//两点之间里程
	public static final String speed="speed";//
	public static final String temperature="temperature";//温度
	public static final String isChargeVoltage="is_charge_voltage";//是否使用外部电源
	public static final String voltageInner="voltage_inner";//锂电电压
	public static final String voltageOuter="voltage_outer";//车截电池电压
	public static final String angleValue="angle_value";//磁偏角
	public static final String angleDirection="angle_direction";//磁偏角方向
	public static final String clientTime="client_time";//数据存储时间
	public static final String updateTime="update_time";//
	public static final String status="status";//状态（0：未修正经纬度；1：已经修正经纬度）
}
