package com.msc.utils;

import javax.servlet.http.HttpServletRequest;

import com.msc.dto.ApiJwtResponse;
import com.msc.dto.ErrorResponse;

public class AuthValidator {

	public ApiJwtResponse validate(HttpServletRequest request) throws Exception{
		ApiJwtResponse jwtResponse = null;		
		try {
			//checar si las headers de Basic no estan nulos
			
			if(request.getHeader("Authorization").isEmpty() || request.getHeader("Authorization") == null) {
				jwtResponse = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST,ConstantesJwt.ApiResponses.FAILURE, 
						ConstantesJwt.ParamsError.USER_PASS_ERROR);
				return jwtResponse;
				}		
			
			// Verificar si los parametros grant_type y api existen en la peticion
			//Verificar param grant_type
			if(request.getParameterMap().containsKey(ConstantesJwt.Params.GRANT_TYPE)) {
				if(request.getParameter(ConstantesJwt.Params.GRANT_TYPE).isEmpty() || !request.getParameter(ConstantesJwt.Params.GRANT_TYPE).equals(ConstantesJwt.Params.CLIENT_CREDENTIALS)){					
					jwtResponse = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST,ConstantesJwt.ApiResponses.FAILURE, ConstantesJwt.ParamsError.GRANT_TYPE_ERROR );
					return jwtResponse;
				}
			}else {
				jwtResponse = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST,ConstantesJwt.ApiResponses.FAILURE, ConstantesJwt.ParamsError.GRANT_TYPE_ERROR );
				return jwtResponse;
			}
			//Verificar param api
			if(request.getParameterMap().containsKey(ConstantesJwt.Params.API)) {
				if(request.getParameter(ConstantesJwt.Params.API).isEmpty() || request.getParameter(ConstantesJwt.Params.GRANT_TYPE).equals("")){					
					jwtResponse = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST,ConstantesJwt.ApiResponses.FAILURE, ConstantesJwt.ParamsError.API_FAIL );
					return jwtResponse;
				}
			}else {
				jwtResponse = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST,ConstantesJwt.ApiResponses.FAILURE, ConstantesJwt.ParamsError.API_FAIL );
				return jwtResponse;
			}
		}catch(Exception e) {
			jwtResponse = new ErrorResponse(ConstantesJwt.Codes.INTERNAL_ERROR,ConstantesJwt.ApiResponses.FAILURE, e.getCause().toString());
			return jwtResponse;
		}
		return jwtResponse;
	}
	
}
