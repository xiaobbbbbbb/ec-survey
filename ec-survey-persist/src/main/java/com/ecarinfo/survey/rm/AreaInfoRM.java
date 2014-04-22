package com.ecarinfo.survey.rm;
public class AreaInfoRM {
	public static final String tableName="area_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,is_leaf
	public static final String name="name";//区域名称
	public static final String cityId="city_id";//
	public static final String pid="pid";//父类ID
	public static final String orderNo="order_no";//
	public static final String isLeaf="is_leaf";//是否是叶子,0为叶子，1为根
	public static final String disabled="disabled";// 状态，默认1为有效
	public static final String orgId="org_id";//服务机构ID
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
}
