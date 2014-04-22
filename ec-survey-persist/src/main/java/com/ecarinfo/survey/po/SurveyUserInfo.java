package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class SurveyUserInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer areaId;//所属片区ID
	private String name;//姓名
	private String phoneNo;//联系电话
	private Integer status = 0;//工作状态
	private Date createTime;
	private Date updateTime = new Date();
	private String driveNo;//驾驶证号
	private Date driveToTime;//驾驶证到期日期
	private Integer disabled = 1;//账号是否启用，默认1为启用
	private Integer onbind;//查勘员是否与电子标签绑定，绑定为1
	private Integer orgId;//服务机构ID

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPhoneNo () {
        return phoneNo;
    }

    public void setPhoneNo (String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDriveNo () {
        return driveNo;
    }

    public void setDriveNo (String driveNo) {
        this.driveNo = driveNo;
    }

    public Date getDriveToTime () {
        return driveToTime;
    }

    public void setDriveToTime (Date driveToTime) {
        this.driveToTime = driveToTime;
    }

    public Integer getDisabled () {
        return disabled;
    }

    public void setDisabled (Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getOnbind () {
        return onbind;
    }

    public void setOnbind (Integer onbind) {
        this.onbind = onbind;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
    }
}