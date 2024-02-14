package com.msc.utils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

public class Utils {
	public String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
//	    for (String header : ConstantesJwt.DataIP.HEADERS_TO_TRY) {
//	        String ip = request.getHeader(header);
//	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
//	            return ip;
//	        }
//	    }
//	    return request.getRemoteAddr();
	}
	
		
	public String decryptB64(String pass) throws Exception {
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(pass);
            return new String(decodedBytes, "UTF-8"); // Puedes especificar la codificaci�n seg�n tus necesidades
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
	
}
