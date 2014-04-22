package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class MarkInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;//标注信息
	private String name;//标注名称
	private Integer type;//标注类型，参考mark_type表
	private String address;//标记点详细地址
	private Double baiduLongitude;//经度
	private Double baiduLatitude;//纬度
	private Integer status;//状态0为标注,1为已标定，-1为无效点
	private Integer orgId;//服务机构ID
	private Date createTime;//创建时间
	private Date updateTime = new Date();//更新时间

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public Double getBaiduLongitude () {
        return baiduLongitude;
    }

    public void setBaiduLongitude (Double baiduLongitude) {
        this.baiduLongitude = baiduLongitude;
    }

    public Double getBaiduLatitude () {
        return baiduLatitude;
    }

    public void setBaiduLatitude (Double baiduLatitude) {
        this.baiduLatitude = baiduLatitude;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
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