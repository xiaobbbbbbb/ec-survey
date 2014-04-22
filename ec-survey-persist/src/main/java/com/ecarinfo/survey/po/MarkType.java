package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class MarkType implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//主键
	private String name;//标注分类名称
	private String img;//图标地址
	private Integer orgId;//服务机构ID
	private Integer disabled = 1;//状态，默认1为有效
	private Date createTime;//创建时间
	private Date updateTime;

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

    public String getImg () {
        return img;
    }

    public void setImg (String img) {
        this.img = img;
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