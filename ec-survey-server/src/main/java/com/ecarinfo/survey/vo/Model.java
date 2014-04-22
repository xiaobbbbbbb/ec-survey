package com.ecarinfo.survey.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Model implements Serializable {

	private static final long serialVersionUID = -8242922689897153199L;
	
	@Override
	public String toString() {
		Method[] ms = this.getClass().getMethods();
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append(":[");
		for(Method m : ms){
			String name = m.getName();
			if(!name.startsWith("getClass")&&(name.startsWith("get")|| name.startsWith("is"))){
				try {
					sb.append(name.substring(3)).append(":").append(m.invoke(this)).append(", ");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		sb.deleteCharAt(sb.length()-2);
		sb.append("]");
		return sb.toString();
	}
}