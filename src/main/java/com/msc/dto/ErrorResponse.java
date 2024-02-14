package com.msc.dto;

import java.io.Serializable;

public class ErrorResponse  extends ApiJwtResponse implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String detalles;

	public ErrorResponse(int codigo, String mensaje,String detalles) {
		super(codigo,mensaje);
		this.detalles = detalles;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	
	
}
