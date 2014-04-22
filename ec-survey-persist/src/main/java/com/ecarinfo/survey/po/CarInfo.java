package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CarInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer deviceId;//设备ID
	private String carNo;//车牌号
	private Integer areaId;//区域id
	private String carModel;//车型(字符串)
	private Date createTime;
	private Date updateTime = new Date();
	private Integer orgId;//机构ID
	private Integer onbind;//是否绑定，1未绑定，0未绑定
	private Date inspectionTime;//年检到期日
	private Integer disabled;//状态，默认1为有效
	private Date enregister;//登记日期
	private Integer status;//状态：0为待命中，1为工作中，2为离线

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

    public String getCarNo () {
        return carNo;
    }

    public void setCarNo (String carNo) {
        this.carNo = carNo;
    }

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public String getCarModel () {
        return carModel;
    }

    public void setCarModel (String carModel) {
        this.carModel = carModel;
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

    public Integer getOrgId () {
        return orgId;
    }

    public void setOrgId (Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getOnbind () {
        return onbind;
    }

    public void setOnbind (Integer onbind) {
        this.onbind = onbind;
    }

    public Date getInspectionTime () {
        return inspectionTime;
    }

    public void setInspectionTime (Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public Integer getDisabled () {
        return disabled;
    }

    public void setDisabled (Integer disabled) {
        this.disabled = disabled;
    }

    public Date getEnregister () {
        return enregister;
    }

    public void setEnregister (Date enregister) {
        this.enregister = enregister;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }
}