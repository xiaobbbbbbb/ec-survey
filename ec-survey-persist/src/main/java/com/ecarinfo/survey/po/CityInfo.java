package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class CityInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private Double longitude;//经度
	private Double latitude;//纬度
	private Integer grade;
	private Date createTime;
	private Date updateTime = new Date();
	private String pinyin;

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

    public Double getLongitude () {
        return longitude;
    }

    public void setLongitude (Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude () {
        return latitude;
    }

    public void setLatitude (Double latitude) {
        this.latitude = latitude;
    }

    public Integer getGrade () {
        return grade;
    }

    public void setGrade (Integer grade) {
        this.grade = grade;
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

    public String getPinyin () {
        return pinyin;
    }

    public void setPinyin (String pinyin) {
        this.pinyin = pinyin;
    }
}