package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String serialNo;
	private String code;
	private Integer orgId;//所属机构ID
	private Integer disabled;//是否有效，默认为1有效
	private Integer onbind;//设备是否已经绑定,1绑定，0未绑定
	private Boolean online;//是否在线
	private Date lastUploadTime;//最后一次上传数据时间
	private Date createTime;
	private Date updateTime = new Date();

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

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
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

    public Boolean getOnline () {
        return online;
    }

    public void setOnline (Boolean online) {
        this.online = online;
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