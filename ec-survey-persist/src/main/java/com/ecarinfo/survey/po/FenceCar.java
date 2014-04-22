package com.ecarinfo.survey.po;
import java.io.Serializable;
import java.util.Date;

public class FenceCar implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Integer fenceId;//围栏id
	private Integer carId;
	private Date createTime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getFenceId () {
        return fenceId;
    }

    public void setFenceId (Integer fenceId) {
        this.fenceId = fenceId;
    }

    public Integer getCarId () {
        return carId;
    }

    public void setCarId (Integer carId) {
        this.carId = carId;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
}