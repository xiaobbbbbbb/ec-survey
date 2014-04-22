package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class TagOnlineData implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String serialNo;//标签标识
	private Integer userId;//查勘员ID
	private Date updateTime;//更新时间
	private Date createTime;//执行时间

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
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

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
}