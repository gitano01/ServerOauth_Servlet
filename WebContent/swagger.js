var host = document.URL;
host = (!host.includes("localhost")) ? host.substring(7, 19) : host.substring(7, 16) ;
var spec = {
	"swagger": "2.0",
	"info": {
		"description": "Documentación para la autenticacion de usuarios en aplicaciones de efectivale mediante tokens",
		"version": "0.0.1",
		"title": "EfvServeOauth",
		"license": {
			"name": "Apache 2.0",
			"url": "https://www.apache.org/licenses/LICENSE-2.0"
		}
	},
	"host": host + ":8080",
	"tags": [
		{
			"name": "Auth Token Api",
			"description": "Auth Controller"
		},
		{
			"name": "swagger-controller",
			"description": "Swagger Controller"
		}
	],
	"paths": {
		"/EfvServerOauth/api/v1/validateToken": {
			"get": {
				"tags": [
					"swagger-controller"
				],
				"summary": "getRedirectUrl",
				"operationId": "getRedirectUrlUsingGET",
				"produces": [
					"application/json"
				],
				"parameters": [
					{
						"name": "Authorization",
						"in": "header",
						"description": "Para ocupar la peticion este input debe tener esta estructura ejemplo: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc0MTc3NDB9.-4zDa6AYnvU_-PPyDKRrBz5HetYsBTFhLeZdaArDdPo",
						"required": true,
						"type": "string"
					},
					{
						"name": "api",
						"in": "query",
						"description": "Nombre del api que va validar el token",
						"required": true,
						"type": "string"
					}
				],
				"responses": {
					"200": {
						"description": "OK",
						"schema": {
							"type": "string"
						}
					}
				}
			}
		},
		"/EfvServerOauth/api/v1/generateToken": {
			"post": {
				"tags": [
					"Auth Token Api"
				],
				"summary": "Genera un token para la autenticacion en otras aplicaciones con configuracion de seguridad",
				"operationId": "loginUsingPOST",
				"consumes": [
					"application/json"
				],
				"produces": [
					"application/json"
				],
				"parameters": [
					{
						"name": "api",
						"in": "query",
						"description": "Nombre del recurso API",
						"required": true,
						"type": "string"
					},
					{
						"name": "grant_type",
						"in": "query",
						"description": "Tipo de acceso (grant_type)",
						"required": true,
						"type": "string"
					}
				],
				"responses": {
					"200": {
						"description": "Ok",
						"schema": {
							"$ref": "#/definitions/ApiJwtMockResponse"
						}
					},
					"400": {
						"description": "Petición fallida",
						"schema": {
							"$ref": "#/definitions/Error400"
						}
					},
					"401": {
						"description": "No Autorizado",
						"schema": {
							"$ref": "#/definitions/Error401"
						}
					},
					"404": {
						"description": "Recurso no encontrado",
						"schema": {
							"$ref": "#/definitions/Error404"
						}
					},
					"500": {
						"description": "Error Interno del Servidor",
						"schema": {
							"$ref": "#/definitions/Error500"
						}
					}
				},
				"security": [
					{
						"basicAuth": []
					}
				]
			}
		}
	},
	"securityDefinitions": {
		"basicAuth": {
			"type": "basic"
		}
	},
	"definitions": {
		"ApiResponse": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32",
					"example": 200,
					"description": "Estado de la operación"
				},
				"mensaje": {
					"type": "string",
					"example": "Operación Exitosa",
					"description": "Mensaje de la operación"
				},
				"response": {
					"$ref": "#/definitions/JwtResponse"
				}
			},
			"title": "ApiJwtMockResponse",
			"description": "Resultado de ejemplo que se mostrará en caso de éxito"
		},
		"ApiJwtResponse": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32"
				},
				"mensaje": {
					"type": "string"
				}
			},
			"title": "ApiJwtResponse"
		},
		"Error400": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32",
					"example": 400,
					"description": "Estado de la operación"
				},
				"detalle": {
					"type": "string",
					"example": "Describe el valor de error de validación",
					"description": "Detalle de error en la operación"
				},
				"mensaje": {
					"type": "string",
					"example": "Operación fallida",
					"description": "Mensaje de la operación"
				}
			},
			"title": "Error400",
			"description": "Resultado presentado al usuario en caso de generarse un error de validación"
		},
		"Error401": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32",
					"example": 401,
					"description": "Estado de la operación"
				},
				"detalle": {
					"type": "string",
					"example": "No Autorizado",
					"description": "Detalle de error en la operación"
				},
				"mensaje": {
					"type": "string",
					"example": "Operación fallida",
					"description": "Mensaje de la operación"
				}
			},
			"title": "Error401",
			"description": "Resultado presentado al usuario en caso de generarse un error de autorización"
		},
		"Error404": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32",
					"example": 404,
					"description": "Estado de la operación"
				},
				"error": {
					"type": "string",
					"example": "Recurso no encontrado",
					"description": "Detalle de error en la operación"
				},
				"mensaje": {
					"type": "string",
					"example": "Operación fallida",
					"description": "Mensaje de la operación"
				}
			},
			"title": "Error404",
			"description": "Resultado presentado al usuario en caso de generarse un error sobre el recurso"
		},
		"Error500": {
			"type": "object",
			"properties": {
				"codigo": {
					"type": "integer",
					"format": "int32",
					"example": 500,
					"description": "Estado de la operación"
				},
				"detalle": {
					"type": "string",
					"example": "Error Interno del Servidor",
					"description": "Detalle de error en la operación"
				},
				"mensaje": {
					"type": "string",
					"example": "Operación fallida",
					"description": "Mensaje de la operación"
				}
			},
			"title": "Error500",
			"description": "Resultado presentado al usuario en caso de generarse un error de validación"
		},
		"JwtResponse": {
			"type": "object",
			"properties": {
				"acessToken": {
					"type": "string",
					"example": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTk3MTI4MDMsImlhdCI6MTY1OTcxMjQ0MywiaXNzIjoiaHR0cHM6Ly93d3cuZWZlY3RpdmFsZS5jb20ubXgvIiwic3ViIjoie1wiYXBpXCI6XCJqd3RUb2tlblNlcnZpY2VcIixcImNyZWRlbmNpYWxBY3RpdmFcIjpcInRcIn0ifQ.NZRmsMa93Y5a57fjv4rMwwZvG_LPaia6NeGPKjMWrGc",
					"description": "Valor del token"
				},
				"expiresIn": {
					"type": "integer",
					"format": "int32",
					"example": 360,
					"description": "Tiempo de expiración del token"
				},
				"issuedAt": {
					"type": "string",
					"example": 1641395643000,
					"description": "Expedicion del token en base al sistema"
				},
				"tokenType": {
					"type": "string",
					"example": "bearer",
					"description": "Tipo de token"
				}
			},
			"title": "JwtResponse",
			"description": "Resultado que se mostrará cuando se generé el token"
		}
	}
}