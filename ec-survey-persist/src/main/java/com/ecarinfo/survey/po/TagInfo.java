package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class TagInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String serialNo;//标签号
	private Integer userId;//查勘员ID
	private Integer carId;//车辆ID
	private Integer disabled = 1;//是否有效，默认为1有效
	private Boolean online;//是否在线
	private Integer orgId;//服务机构ID
	private Date lastUploadTime;//最后一次上传数据时间
	private Date createTime;//创建时间
	private Date updateTime = new Date();//更新时间

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getSerialNo () {
        return serialNo;
    }

    public void setSerialNo (String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getUserId () {
        return userId;
    }

    public void setUserId (Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId () {
        return carId;
    }

    public void setCarId (Integer carId) {
        this.carId = carId;
    }

    public Integer getDisabled () {
        return disabled;
    }

    public void setDisabled (Integer disabled) {
        this.disabled = disabled;
    }

    public Boolean getOnline () {
        return online;
    }

    public void setOnline (Boolean online) {
        this.online = online;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
    }

    public Date getLastUploadTime () {
        return lastUploadTime;
    }

    public void setLastUploadTime (Date lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
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
}