package com.ecarinfo.survey.vo;

public class BaseVO extends Model{
	private static final long serialVersionUID = -2835970297554431842L;
	public String getDescriptorForType() {
		return this.getClass().getName();
	}
}
