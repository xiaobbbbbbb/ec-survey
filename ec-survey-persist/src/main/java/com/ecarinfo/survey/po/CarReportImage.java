package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CarReportImage implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String url;//图片地址
	private Integer serialNo;//图片序号
	private Long carReportId;//行车数据ID
	private Date createTime;//创建时间

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getSerialNo () {
        return serialNo;
    }

    public void setSerialNo (Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Long getCarReportId () {
        return carReportId;
    }

    public void setCarReportId (Long carReportId) {
        this.carReportId = carReportId;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
}