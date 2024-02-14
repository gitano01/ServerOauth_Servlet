package com.msc.dto;

import java.io.Serializable;

public class DataUser implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String rs_consumersecret;
	private String rs_api;
	private String rs_credencial_activa;
	
	public String getRs_consumersecret() {
		return rs_consumersecret;
	}
	public void setRs_consumersecret(String rs_consumersecret) {
		this.rs_consumersecret = rs_consumersecret;
	}
	public String getRs_api() {
		return rs_api;
	}
	public void setRs_api(String rs_api) {
		this.rs_api = rs_api;
	}
	public String getRs_credencial_activa() {
		return rs_credencial_activa;
	}
	public void setRs_credencial_activa(String rs_credencial_activa) {
		this.rs_credencial_activa = rs_credencial_activa;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	
}
