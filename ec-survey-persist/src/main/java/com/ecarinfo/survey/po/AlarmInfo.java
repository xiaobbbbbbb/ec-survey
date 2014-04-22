package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class AlarmInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer deviceId;
	private Date createTime;
	private Date updateTime = new Date();
	private Integer type;
	private String description;
	private Date clientTime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getDeviceId () {
        return deviceId;
    }

    public void setDeviceId (Integer deviceId) {
        this.deviceId = deviceId;
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

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Date getClientTime () {
        return clientTime;
    }

    public void setClientTime (Date clientTime) {
        this.clientTime = clientTime;
    }
}