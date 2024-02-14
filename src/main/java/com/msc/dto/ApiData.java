package com.msc.dto;

import java.io.Serializable;

public class ApiData implements Serializable{	
	private static final long serialVersionUID = 1L;
	private String api;
	private String credencialActiva;
	
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getCredencialActiva() {
		return credencialActiva;
	}
	public void setCredencialActiva(String credencialActiva) {
		this.credencialActiva = credencialActiva;
	}
	
}
