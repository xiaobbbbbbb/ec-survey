package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class FenceInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private Integer alarmType;//报警类型，0为出围栏报警；1为进入报警，
	private String alarmStartTime;//报警开始时间
	private String alarmEndTime;//报警结束时间
	private String points;//电子围拦坐标点(百度经纬度)
	private Integer gpsPoints;//gps经纬度点集
	private String description;
	private Integer grade;//当前地图的级别
	private String centerPoints;//当前地图的中心点
	private Date createTime;
	private Date updateTime = new Date();
	private Integer orgId;//服务机构ID

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

    public Integer getAlarmType () {
        return alarmType;
    }

    public void setAlarmType (Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmStartTime () {
        return alarmStartTime;
    }

    public void setAlarmStartTime (String alarmStartTime) {
        this.alarmStartTime = alarmStartTime;
    }

    public String getAlarmEndTime () {
        return alarmEndTime;
    }

    public void setAlarmEndTime (String alarmEndTime) {
        this.alarmEndTime = alarmEndTime;
    }

    public String getPoints () {
        return points;
    }

    public void setPoints (String points) {
        this.points = points;
    }

    public Integer getGpsPoints () {
        return gpsPoints;
    }

    public void setGpsPoints (Integer gpsPoints) {
        this.gpsPoints = gpsPoints;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Integer getGrade () {
        return grade;
    }

    public void setGrade (Integer grade) {
        this.grade = grade;
    }

    public String getCenterPoints () {
        return centerPoints;
    }

    public void setCenterPoints (String centerPoints) {
        this.centerPoints = centerPoints;
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
}