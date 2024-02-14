package com.msc.dto;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String tokenType;		
	private String accessToken;			
	private Date expiresIn;		
	private Date issuedAt;	
	
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Date getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	
}
