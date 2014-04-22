package com.ecarinfo.survey.dto;

public class ResultDto {
	private int status;
	private String message;
	private Object result;
	private Object extendsObject;
	
	public ResultDto(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public ResultDto(int status, String message, Object extendsObject) {
		super();
		this.status = status;
		this.message = message;
		this.extendsObject = extendsObject;
	}
	
	public ResultDto() {
		super();
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getExtendsObject() {
		return extendsObject;
	}

	public void setExtendsObject(Object extendsObject) {
		this.extendsObject = extendsObject;
	}

}
