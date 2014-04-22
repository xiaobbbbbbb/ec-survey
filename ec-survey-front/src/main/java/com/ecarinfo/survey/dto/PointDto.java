package com.ecarinfo.survey.dto;

public class PointDto {
	private double lng;
	private double lat;
	private String html;
	public PointDto() {
	}
	
	public PointDto(double lng, double lat) {
		super();
		this.lng = lng;
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
