package com.suravi.springdata.miniproject.errorhandling;

public class ResponseObject {
	
	Object data;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	String error;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ResponseObject(Object data, String error) {
		super();
		this.data = data;
		this.error = error;
	}
}
