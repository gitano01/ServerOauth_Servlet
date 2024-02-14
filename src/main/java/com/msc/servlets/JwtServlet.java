package com.msc.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.msc.dao.jwt.JwtAuthService;
import com.msc.dto.ApiJwtResponse;
import com.msc.dto.ErrorResponse;
import com.msc.utils.AuthValidator;
import com.msc.utils.ConstantesJwt;
import com.msc.utils.Utils;

public class JwtServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(JwtServlet.class);
	private Utils util = new Utils();
	private AuthValidator validator;
	private JwtAuthService service;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws HTTPException, IOException, ServletException {
		ApiJwtResponse resp = null;

		HttpSession session = request.getSession();

		try {
			validator = new AuthValidator();
			System.out.println(request.getParameter("username"));

			resp = validator.validate(request);

			if (resp != null) {
				log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase ["
						+ JwtServlet.class.getName() + "]  | [ detalle de error: " + resp.getCodigo() + " ]");

			} else {

				log.info(ConstantesJwt.Oauth.log.PROCESS_BEGIN + "[" + JwtServlet.class.getName()
						+ "] | ipConsumidor: " + util.getClientIpAddress(request));
				service = new JwtAuthService();
				resp = service.login(request);
				if (resp.getCodigo() != 200) {
					log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase ["
							+ JwtServlet.class.getName() + "]  | [ detalle de error: " + resp.getCodigo() + " | "
							+ session.getAttribute("error").toString() + " ]");
				} else {
					log.info(ConstantesJwt.Oauth.log.PROCESS_END + "[" + JwtServlet.class.getName()
							+ "] |  ipConsumidor: " + util.getClientIpAddress(request) + " |");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp = new ErrorResponse(500,"Operaci�n fallida", e.getMessage());
		}

		// Devuelve la respuesta de salida en el Json de postman
		doResponseApi(resp.getCodigo(), resp, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,HTTPException{
		ApiJwtResponse resp = null;
		HttpSession session = request.getSession();
		try {
			
			service = new JwtAuthService();
			resp = service.validateToken(request);
			if (resp.getCodigo() != 200) {
				log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR + " en la clase ["
						+ JwtServlet.class.getName() + "]  | [ detalle de error: " + resp.getCodigo() + " | "
						+ session.getAttribute("error").toString() + " ]");
			} else {
				log.info(ConstantesJwt.Oauth.log.PROCESS_END + "[" + JwtServlet.class.getName()
						+ "] |  ipConsumidor: " + util.getClientIpAddress(request) + " |");
			}
			
		}catch(HTTPException e) {			
			
			resp = new ErrorResponse(e.getStatusCode(),"Operaci�n fallida", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp = new ErrorResponse(ConstantesJwt.Codes.INTERNAL_ERROR,"Operaci�n fallida", e.getMessage());
			
		}
		
		doResponseApi(resp.getCodigo(), resp, response);
		
		
	}

	protected void doResponseApi(int code, ApiJwtResponse resp, HttpServletResponse response) throws IOException {
		// Devolver el token en formato JSON
		response.setContentType("text/json");
		response.setStatus(code);
		PrintWriter out = response.getWriter();

		out.println(new Gson().toJson(resp));
	}

}
