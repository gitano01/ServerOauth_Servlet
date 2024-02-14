package com.msc.utils;

public class ConstantesJwt {

	public static class DataIP {
		protected static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
				"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
				"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
	}

	public static class Oauth {
		public static final String VERSION = "v1";
		public static final String GENERATION_TOKEN = "oauth/client_credential/accesstoken";

		public static class ErrorsDB {
			// errores usuario
			public static final String USER_NO_EXIST = "USUARIO NO EXISTE";
			public static final String PASSWORD_INCORRECT = "USUARIO Y/O CONTRASE�A";
			public static final String USER_NO_ACTIVE = "USUARIO NO ESTA ACTIVO";
			public static final String USER_NOT_CREDENTIALS = "USUARIO NO CUENTA CON CREDENCIALES";
			// errores credencial
		//	public static final String CREDENTIAL_NOT_EXIST = "LAS CREDENCIALES NO EXISTEN";
		//	public static final String CREDENTIAL_ACTIVE_ERROR = "LA CREDENCIAL NO SE ENCUENTRA ACTIVA, CONSULTA CON TU ADMINISTRADOR";
		//	public static final String CREDENTIALS_API_ACTIVE_ERROR = "LAS CREDENCIALES NO ESTAN ACTIVAS PARA REALIZAR LA SOLICITUD AL RECURSO";
		//	public static final String CREDENTIAL_NOT_ASOCIATE = "EL USUARIO NO CUENTA CON CREDENCIALES PARA CONSUMIR EL API SOLICITADO";
			
			// errores API
			public static final String API_NO_EXIST = "API NO EXISTE";
			public static final String API_NO_ACTIVE = "API NO ESTA ACTIVA";		
		}

		public static class log {
			public static final  String PROCESS_BEGIN = "Inicia proceso de la clase:  ";
			public static final String PROCESS_END = "Termina proceso con �xito de la clase: ";
			public static final String PROCESS_INTERRUPTOR = "Proceso interrumpido ";
		}

	}

	public static class Params {
		public static final String GRANT_TYPE = "grant_type";
		public static final String CLIENT_CREDENTIALS = "client_credentials";
		public static final String API = "api";
			
	}

	public static class ParamsError {
		public static final String GRANT_TYPE_ERROR = "El tipo de granType est� vac�o o no es el requerido";
		public static final String USER_ERROR = "Usuario y/ o Contrase�a incorrectos";
		public static final String USER_PASS_ERROR = "El usuario y/o contrase�a son requeridos";
		public static final String API_FAIL = "El p�rametro api es necesario";

	}

	public static class Swagger {
		public static final String TITLE_CONTROLLER = "Auth Token Api";
		public static final String TITLE = "JWT Token Server Auth";
		public static final String DESCRIPTION = "Documentaci�n para la autenticacion de usuarios en aplicaciones de efectivale mediante tokens";
		public static final String CONTROLLER_DESCRIPTION = "Genera un token para la autenticacion en otras aplicaciones con configuracion de seguridad";
		public static final String VERSION = "0.0.1";
		public static final String LICENSE = "Apache 2.0";
		public static final String LICENSESURL = "https://www.apache.org/licenses/LICENSE-2.0\"";

		// Repuestas para la vista del swagger
		public static final String OK = "Ok";
		public static final String BAD_REQUEST = "Petici�n fallida";
		public static final String UNAUTHORIZED = "No Autorizado";
		public static final String NOT_FOUND = "Recurso no encontrado";
		public static final String INTERNAL_ERROR = "Error Interno del Servidor";
	}

	public static class Codes {
		// codigo de repuesta
		public static final int OK = 200;
		public static final int BAD_REQUEST = 400;
		public static final int UNAUTHORIZED = 401;
		public static final int NOT_FOUND = 404;
		public static final int INTERNAL_ERROR = 500;
	}

	public static class ApiResponses {
		// Strings Respuestas
		public static final String OK = "Operaci�n Exitosa";
		public static final String FAILURE = "Operaci�n fallida";
		public static final String ERROR = "error";

	}

	public static class PgSql {
		public static final String QUERY_CRED = "SELECT * FROM sp_credencial_usuarioapi";
	}

	

}
