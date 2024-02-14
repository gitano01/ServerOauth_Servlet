package com.msc.dto;

import java.io.Serializable;

public abstract class ApiJwtResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private int  codigo;
	private String mensaje;
	protected ApiJwtResponse(int codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
	
	
	}
