package com.ecarinfo.survey.rm;
public class MarkInfoRM {
	public static final String tableName="mark_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,标注信息
	public static final String name="name";//标注名称
	public static final String type="type";//标注类型，参考mark_type表
	public static final String address="address";//标记点详细地址
	public static final String baiduLongitude="baidu_longitude";//经度
	public static final String baiduLatitude="baidu_latitude";//纬度
	public static final String status="status";//状态0为标注,1为已标定，-1为无效点
	public static final String orgId="org_id";//服务机构ID
	public static final String createTime="create_time";//创建时间
	public static final String updateTime="update_time";//更新时间
}
