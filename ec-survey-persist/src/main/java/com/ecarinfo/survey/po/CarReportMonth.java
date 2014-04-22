package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CarReportMonth implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer carId;//车辆id
	private Integer surveyNum;//查勘次数
	private Integer trafficNum;//违章次数
	private Float totalMonthMile;//月里程
	private Integer totalFenceCounts;//月围栏告警纪录
	private Integer totalHypervelocity;//月超速纪录
	private String month;//月份
	private Date createTime;//创建时间

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getCarId () {
        return carId;
    }

    public void setCarId (Integer carId) {
        this.carId = carId;
    }

    public Integer getSurveyNum () {
        return surveyNum;
    }

    public void setSurveyNum (Integer surveyNum) {
        this.surveyNum = surveyNum;
    }

    public Integer getTrafficNum () {
        return trafficNum;
    }

    public void setTrafficNum (Integer trafficNum) {
        this.trafficNum = trafficNum;
    }

    public Float getTotalMonthMile () {
        return totalMonthMile;
    }

    public void setTotalMonthMile (Float totalMonthMile) {
        this.totalMonthMile = totalMonthMile;
    }

    public Integer getTotalFenceCounts () {
        return totalFenceCounts;
    }

    public void setTotalFenceCounts (Integer totalFenceCounts) {
        this.totalFenceCounts = totalFenceCounts;
    }

    public Integer getTotalHypervelocity () {
        return totalHypervelocity;
    }

    public void setTotalHypervelocity (Integer totalHypervelocity) {
        this.totalHypervelocity = totalHypervelocity;
    }

    public String getMonth () {
        return month;
    }

    public void setMonth (String month) {
        this.month = month;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
}