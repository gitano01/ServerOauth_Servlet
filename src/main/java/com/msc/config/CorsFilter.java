package com.msc.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter{

	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        // No se necesita ninguna inicializaci�n, pero si necesitas algo aqu�, puedes hacerlo.
	    }

	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
	            throws IOException, ServletException {
	    	
	    	   HttpServletRequest request = (HttpServletRequest) servletRequest;
	           
	           // Authorize (allow) all domains to consume the content
	           ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
	           ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	           ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // Agrega 'Authorization' aqu�
	           HttpServletResponse response = (HttpServletResponse) servletResponse;

	           // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
	           if (request.getMethod().equals("OPTIONS")) {
	               response.setStatus(HttpServletResponse.SC_ACCEPTED);
	               return;
	           }
//	        HttpServletRequest httpResponse = (HttpServletRequest) request;
//	        
//	        // Configurar las cabeceras CORS
//	        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//	        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//	        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//	        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
	        
	        chain.doFilter(request, response);
	    }

	    @Override
	    public void destroy() {
	        // Si necesitas hacer algo al destruir el filtro, puedes hacerlo aqu�.
	    }

}
