package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CarReport implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Integer carId;
	private String surveyUid;//查勘员ID,多个时逗号分隔
	private Float mileMeter;//本次里程
	private Integer avgSpeed = 0;//平均车速
	private Long totalTime = 0l;//累计行驶时长(秒)
	private Integer hypervelocity = 0;//超速记录
	private Integer fenceCounts = 0;//围栏告警记录
	private Long firstDataId;//第一条gps记录的ID
	private Long lastDataId;//最后一条gps记录的ID
	private String startAddress;//起点位置
	private String endAddress;
	private Date updateTime;
	private Date createTime = new Date();
	private Date firstClientTime;//第一条gps记录时间
	private Date lastClientTime;//最后上传数据时间
	private Integer status = 0;//记录状态（0:未结束此行车，1：已经结束行车，2：已经修正数据）

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getCarId () {
        return carId;
    }

    public void setCarId (Integer carId) {
        this.carId = carId;
    }

    public String getSurveyUid () {
        return surveyUid;
    }

    public void setSurveyUid (String surveyUid) {
        this.surveyUid = surveyUid;
    }

    public Float getMileMeter () {
        return mileMeter;
    }

    public void setMileMeter (Float mileMeter) {
        this.mileMeter = mileMeter;
    }

    public Integer getAvgSpeed () {
        return avgSpeed;
    }

    public void setAvgSpeed (Integer avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Long getTotalTime () {
        return totalTime;
    }

    public void setTotalTime (Long totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getHypervelocity () {
        return hypervelocity;
    }

    public void setHypervelocity (Integer hypervelocity) {
        this.hypervelocity = hypervelocity;
    }

    public Integer getFenceCounts () {
        return fenceCounts;
    }

    public void setFenceCounts (Integer fenceCounts) {
        this.fenceCounts = fenceCounts;
    }

    public Long getFirstDataId () {
        return firstDataId;
    }

    public void setFirstDataId (Long firstDataId) {
        this.firstDataId = firstDataId;
    }

    public Long getLastDataId () {
        return lastDataId;
    }

    public void setLastDataId (Long lastDataId) {
        this.lastDataId = lastDataId;
    }

    public String getStartAddress () {
        return startAddress;
    }

    public void setStartAddress (String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress () {
        return endAddress;
    }

    public void setEndAddress (String endAddress) {
        this.endAddress = endAddress;
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

    public Date getFirstClientTime () {
        return firstClientTime;
    }

    public void setFirstClientTime (Date firstClientTime) {
        this.firstClientTime = firstClientTime;
    }

    public Date getLastClientTime () {
        return lastClientTime;
    }

    public void setLastClientTime (Date lastClientTime) {
        this.lastClientTime = lastClientTime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }
}