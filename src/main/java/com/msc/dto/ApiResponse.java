package com.msc.dto;

import java.io.Serializable;

public class ApiResponse extends ApiJwtResponse implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Object response;

	public ApiResponse(int codigo, String mensaje,Object response) {
		super(codigo,mensaje);
		this.response = response;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
	
	
	
	
}
