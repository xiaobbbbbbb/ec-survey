package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CarReportDay implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer carId;//车辆id
	private Integer surveyNum;//每日勘察次数
	private Integer trafficNum;//违章次数
	private Float totalDayMile;//日总里程
	private Integer avgSpeed;//日平均速度
	private Integer totalFenceCounts;//围栏告警纪录
	private Integer totalHypervelocity = 0;//超速纪录
	private Date clientDay;//客户端时间
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

    public Float getTotalDayMile () {
        return totalDayMile;
    }

    public void setTotalDayMile (Float totalDayMile) {
        this.totalDayMile = totalDayMile;
    }

    public Integer getAvgSpeed () {
        return avgSpeed;
    }

    public void setAvgSpeed (Integer avgSpeed) {
        this.avgSpeed = avgSpeed;
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

    public Date getClientDay () {
        return clientDay;
    }

    public void setClientDay (Date clientDay) {
        this.clientDay = clientDay;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
}