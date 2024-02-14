package com.msc.dao.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import org.apache.log4j.Logger;

import com.msc.dto.ApiJwtResponse;
import com.msc.dto.ApiResponse;
import com.msc.dto.ErrorResponse;
import com.msc.dto.JwtResponse;
import com.msc.utils.ConexionDB;
import com.msc.utils.ConstantesJwt;
import com.msc.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtAuthService {

	private Logger log = Logger.getLogger(JwtAuthService.class);
	private Utils util = new Utils();
	ConexionDB conexion = null;
	ApiJwtResponse response = null;

	public ApiJwtResponse login(HttpServletRequest request) throws Exception {

		JwtResponse jwt = null;
		ApiJwtResponse response = null;
		HttpSession session = request.getSession();
		String error = "";
		try {
			log.info(ConstantesJwt.Oauth.log.PROCESS_BEGIN + "[" + JwtAuthService.class.getName() + "] | ipConsumidor:"
					+ util.getClientIpAddress(request) + " |");
			String extract = util.decryptB64(request.getHeader("Authorization").replace("Basic ", "")).trim();
			String username = extract.substring(0, extract.indexOf(":"));
			String password = extract.substring(extract.indexOf(":") + 1, extract.length());
			log.info(ConstantesJwt.Oauth.log.PROCESS_END + "[" + JwtAuthService.class.getName()
					+ " -- Termina el desencriptado de credenciales --");
			String apinombre = "";
			// Mapear los parametros para el consumo
			Map<String, String[]> mapParameter = request.getParameterMap();

			if (mapParameter.containsKey(ConstantesJwt.Params.API)) {
				if (request.getParameter("api") != null || !request.getParameter("api").equals("")) {
					apinombre = request.getParameter("api");
				}
			} else {
				response = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.ParamsError.API_FAIL);
				// apiResponse = new ResponseEntity <>(response,HttpStatus.BAD_REQUEST);
				error = ConstantesJwt.ApiResponses.ERROR;
				session.setAttribute(error, ConstantesJwt.ParamsError.API_FAIL);
				return response;
			}

			// ir al abase de datos para deteminar el rol de usuario quien solicita el jwt
			log.info(ConstantesJwt.Oauth.log.PROCESS_BEGIN + "[" + JwtAuthService.class.getName()
					+ " -- Ir a base para determinar el rol ligado a la credencial--");
			conexion = new ConexionDB();
			JsonObject valida = conexion.validaCredencial(username, password, apinombre);

			if (valida != null) {

				log.info(ConstantesJwt.Oauth.log.PROCESS_BEGIN + "[" + JwtAuthService.class.getName()
						+ " -- Inicia la generacion de llave jwtToken --");
				valida.addProperty("username", username);

				jwt = generateJwtToken(valida);

				if (jwt != null) {
					response = new ApiResponse(ConstantesJwt.Codes.OK, ConstantesJwt.ApiResponses.OK, jwt);
//			apiResponse  = new ResponseEntity<>(response,HttpStatus.OK);

					log.info(ConstantesJwt.Oauth.log.PROCESS_BEGIN + "[" + JwtAuthService.class.getName()
							+ " -- Token generado correctamente --");
					log.info(ConstantesJwt.Oauth.log.PROCESS_END + " [" + JwtAuthService.class.getName()
							+ "] | ipConsumidor:" + util.getClientIpAddress(request) + " |");
					conexion.guardaBitacora(new Gson().toJson(response), util.getClientIpAddress(request));
				} else {
					response = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST, ConstantesJwt.ApiResponses.FAILURE,
							" Error en la peticion no se pudo generar el token");
//				apiResponse  =  new ResponseEntity <>(response,HttpStatus.BAD_REQUEST);	
					session.setAttribute(error, " Error en la peticion no se pudo generar el token ");
				}
			} else {
				log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase ["
						+ JwtAuthService.class.getName() + "] | -- Ocurrio un detalle con las credenciales --");
			}

		} catch (Exception e) {
			String valueError = e.getMessage();
			error = ConstantesJwt.ApiResponses.ERROR;
			int op = 0;

			// Error USUARIOS
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.USER_NO_EXIST)) {
				op = 1;
			}
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.PASSWORD_INCORRECT)) {
				op = 2;
			}
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.USER_NO_ACTIVE)) {
				op = 3;
			}
			// Error CREDENCIALES
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.USER_NOT_CREDENTIALS)) {
				op = 4;
			}
//			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.CREDENTIALS_API_ACTIVE_ERROR)) {
//				op = 4;
//			}
//			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.CREDENTIAL_NOT_ASOCIATE)) {
//				op = 5;
//			}
			// Error APIS
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.API_NO_EXIST)) {
				op = 5;
			}
			if (valueError.contains(ConstantesJwt.Oauth.ErrorsDB.API_NO_ACTIVE)) {
				op = 6;
			}

			switch (op) {
			case 1:
				response = new ErrorResponse(ConstantesJwt.Codes.NOT_FOUND, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.USER_NO_EXIST);
//			apiResponse  =  new ResponseEntity <>(response,HttpStatus.NOT_FOUND);	
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.USER_NO_EXIST);
				break;
			case 2:
				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.PASSWORD_INCORRECT);
//			apiResponse  =  new ResponseEntity <>(response,HttpStatus.UNAUTHORIZED);	
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.PASSWORD_INCORRECT);
				break;
			case 3:
				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.USER_NO_ACTIVE);
//			apiResponse  =  new ResponseEntity <>(response,HttpStatus.UNAUTHORIZED);	
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.USER_NO_ACTIVE);
				break;	
			case 4:
				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.USER_NOT_CREDENTIALS);
//			apiResponse  =  new ResponseEntity <>(response,HttpStatus.NOT_FOUND);	
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.USER_NOT_CREDENTIALS);
				break;
//			case 4:
//				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
//						ConstantesJwt.Oauth.ErrorsDB.CREDENTIALS_API_ACTIVE_ERROR);
////			apiResponse  =   new ResponseEntity <>(response,HttpStatus.UNAUTHORIZED);
//				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.CREDENTIALS_API_ACTIVE_ERROR);
//				break;
//			case 5:
//				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
//						ConstantesJwt.Oauth.ErrorsDB.CREDENTIAL_NOT_ASOCIATE);
////			apiResponse  =   new ResponseEntity <>(response,HttpStatus.UNAUTHORIZED);
//				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.CREDENTIAL_NOT_ASOCIATE);
//				break;
			case 5:
				response = new ErrorResponse(ConstantesJwt.Codes.NOT_FOUND, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.API_NO_EXIST);
//			apiResponse  =   new ResponseEntity <>(response,HttpStatus.NOT_FOUND);
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.API_NO_EXIST);
				break;
			case 6:
				response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
						ConstantesJwt.Oauth.ErrorsDB.API_NO_ACTIVE);
//			apiResponse  =  new ResponseEntity <>(response,HttpStatus.UNAUTHORIZED);	
				session.setAttribute(error, ConstantesJwt.Oauth.ErrorsDB.API_NO_ACTIVE);
				break;

			default:
				response = new ErrorResponse(ConstantesJwt.Codes.INTERNAL_ERROR, ConstantesJwt.ApiResponses.FAILURE,
						e.getMessage());
//			apiResponse  =   new ResponseEntity <>(response,HttpStatus.INTERNAL_SERVER_ERROR);
				session.setAttribute(error, e.getMessage());
				break;
			}
			log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase [" + JwtAuthService.class.getName()
					+ "]  | [ detalle de error: " + response.getCodigo() + " | "
					+ session.getAttribute(error).toString() + " ]");
			conexion.guardaBitacora(new Gson().toJson(response), util.getClientIpAddress(request));

		}
		return response;
	}

	public JwtResponse generateJwtToken(JsonObject datosUsuario) throws Exception {

		try {
			JwtResponse jwt = new JwtResponse();
			long currentTimellis = System.currentTimeMillis();
			Date issuesAt = new Date(currentTimellis);
			Date expireIn = new Date(currentTimellis + (30 * 60 * 1000));// duracion de 30 minutos

			jwt.setTokenType("Bearer");
			jwt.setAccessToken(Jwts.builder().setExpiration(expireIn) // 24 horas de validez
					.signWith(SignatureAlgorithm.HS256, datosUsuario.get("consumersecret").getAsString()).compact());

			jwt.setExpiresIn(expireIn);
			jwt.setIssuedAt(issuesAt);
			return jwt;
		} catch (Exception e) {
			log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase [" + JwtAuthService.class
					+ "] -- Ocurrio un error al generar el token  | " + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public ApiJwtResponse validateToken(HttpServletRequest request)throws Exception, HTTPException {

		try {
			conexion = new ConexionDB();
			String authorizationHeader = request.getHeader("Authorization");

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String authToken = authorizationHeader.substring(7); // Para eliminar "Bearer " del encabezado
			//System.out.println("Bearer Token: " + authToken);
				// response = new ApiResponse(200, "Operaci�n exitosa", authToken);
				response = validate(authToken, request);
			} else {

				// Manejo si el encabezado no contiene un token Bearer
				response = new ErrorResponse(ConstantesJwt.Codes.BAD_REQUEST, "Operaci�n fallida",
						"No se proporcion� un token Bearer en el encabezado Authorization.");
			}
		} catch (HTTPException e) {
			response = new ErrorResponse(e.getStatusCode(), "Operaci�n fallida",
					e.getMessage());
		}
		
		conexion.guardaBitacora(new Gson().toJson(response), util.getClientIpAddress(request));
		
		return response;
	}

	public ApiJwtResponse validate(String token, HttpServletRequest request) {

		// Con la llave secreta del servicio ir por la lave a base 
		String secretKey = "c3ViKjEzNXUqbzMxKm91YkwwMzA=";// obtenerla de base de datos
		HttpSession session = request.getSession();
		String error = ConstantesJwt.ApiResponses.ERROR;
		System.out.println(request.getParameter("api"));
		
		
		
		
		try {
			// Desencriptar Jws<B>token
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

			Claims body = claims.getBody();

			response = new ApiResponse(ConstantesJwt.Codes.OK, ConstantesJwt.ApiResponses.OK, "Token v�lido");

		} catch (ExpiredJwtException e) {

			response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
					e.getMessage());
			session.setAttribute(error, e.getMessage());
		} catch (SignatureException e) {
			response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
					e.getMessage());
			session.setAttribute(error, e.getMessage());
		} catch (MalformedJwtException e) {
			response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
					e.getMessage());
			session.setAttribute(error, e.getMessage());
		} catch (JwtException e) {
			response = new ErrorResponse(ConstantesJwt.Codes.UNAUTHORIZED, ConstantesJwt.ApiResponses.FAILURE,
					e.getMessage());
			session.setAttribute(error, e.getMessage());
		} catch (Exception e) {
			// Manejo gen�rico de excepciones
			response = new ErrorResponse(ConstantesJwt.Codes.INTERNAL_ERROR, ConstantesJwt.ApiResponses.FAILURE,
					"Error al validar el token");
			session.setAttribute(error, "Error al validar el token");

		}

		return response;
	}

}
