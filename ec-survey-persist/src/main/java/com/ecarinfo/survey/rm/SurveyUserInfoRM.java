package com.ecarinfo.survey.rm;
public class SurveyUserInfoRM {
	public static final String tableName="survey_user_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String areaId="area_id";//所属片区ID
	public static final String name="name";//姓名
	public static final String phoneNo="phone_no";//联系电话
	public static final String status="status";//工作状态
	public static final String createTime="create_time";//
	public static final String updateTime="update_time";//
	public static final String driveNo="drive_no";//驾驶证号
	public static final String driveToTime="drive_to_time";//驾驶证到期日期
	public static final String disabled="disabled";//账号是否启用，默认1为启用
	public static final String onbind="onbind";//查勘员是否与电子标签绑定，绑定为1
	public static final String orgId="org_id";//服务机构ID
}
