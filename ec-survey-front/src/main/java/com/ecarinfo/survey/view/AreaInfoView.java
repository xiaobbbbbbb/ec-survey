package com.ecarinfo.survey.view;

import java.util.HashSet;
import java.util.Set;

import com.ecarinfo.survey.po.AreaInfo;

public class AreaInfoView extends AreaInfo{

	private static final long serialVersionUID = 1L;

	private String parentName;  //父类名称
	
	
	private Set<String> carSet = new HashSet();

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Set<String> getCarSet() {
		return carSet;
	}

	public void setCarSet(Set<String> carSet) {
		this.carSet = carSet;
	}
}
