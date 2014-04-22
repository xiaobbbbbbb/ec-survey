package com.ecarinfo.survey.rm;
public class DeviceInfoRM {
	public static final String tableName="device_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String serialNo="serial_no";//
	public static final String code="code";//
	public static final String orgId="org_id";//所属机构ID
	public static final String disabled="disabled";//是否有效，默认为1有效
	public static final String onbind="onbind";//设备是否已经绑定,1绑定，0未绑定
	public static final String online="online";//是否在线
	public static final String lastUploadTime="last_upload_time";//最后一次上传数据时间
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
}
