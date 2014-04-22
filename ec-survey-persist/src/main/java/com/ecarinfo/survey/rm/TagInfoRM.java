package com.ecarinfo.survey.rm;
public class TagInfoRM {
	public static final String tableName="tag_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String serialNo="serial_no";//标签号
	public static final String userId="user_id";//查勘员ID
	public static final String carId="car_id";//车辆ID
	public static final String disabled="disabled";//是否有效，默认为1有效
	public static final String online="online";//是否在线
	public static final String orgId="org_id";//服务机构ID
	public static final String lastUploadTime="last_upload_time";//最后一次上传数据时间
	public static final String createTime="create_time";//创建时间
	public static final String updateTime="update_time";//更新时间
}
