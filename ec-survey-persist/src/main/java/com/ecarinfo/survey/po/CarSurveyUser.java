package com.ecarinfo.survey.po;
import java.io.Serializable;

public class CarSurveyUser implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//主键
	private Integer carId;//车ID
	private Integer surveyUserId;//查勘员ID

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

    public Integer getSurveyUserId () {
        return surveyUserId;
    }

    public void setSurveyUserId (Integer surveyUserId) {
        this.surveyUserId = surveyUserId;
    }
}