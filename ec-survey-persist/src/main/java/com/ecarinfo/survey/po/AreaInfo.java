package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class AreaInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//is_leaf
	private String name;//区域名称
	private Integer cityId;
	private Integer pid;//父类ID
	private Integer orderNo = 0;
	private Boolean isLeaf;//是否是叶子,0为叶子，1为根
	private Integer disabled;// 状态，默认1为有效
	private Integer orgId;//服务机构ID
	private Date createTime;
	private Date updateTime = new Date();

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getCityId () {
        return cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getPid () {
        return pid;
    }

    public void setPid (Integer pid) {
        this.pid = pid;
    }

    public Integer getOrderNo () {
        return orderNo;
    }

    public void setOrderNo (Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getIsLeaf () {
        return isLeaf;
    }

    public void setIsLeaf (Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getDisabled () {
        return disabled;
    }

    public void setDisabled (Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
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