package com.msc.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

public class ConexionDB implements Serializable{

	private static final long serialVersionUID = 1L;
	private PoolConexion pool = new PoolConexion();
	private Logger log = Logger.getLogger(ConexionDB.class);
	
		private Connection conn = null;
		private PreparedStatement ps = null;
		private ResultSet rs = null;
	
	public JsonObject validaCredencial(String consumer, String secretKey,String apiNombre) throws Exception{	
		
		JsonObject validaCred = null;
		
		
		try {
		conn = pool.getConnection("dbservicio");
		ps = conn.prepareStatement("select * from sp_credencial_usuarioapi(?,?,?)");
		ps.setString(1, consumer);
		ps.setString(2, secretKey);
		ps.setString(3, apiNombre);
		if((rs = ps.executeQuery()).next()) {
			validaCred = new JsonObject();
			do {
				validaCred.addProperty("consumerkey", rs.getString("rs_consumerkey"));
				validaCred.addProperty("consumersecret", rs.getString("rs_consumersecret"));
				validaCred.addProperty("rol", rs.getString("rs_rol"));
			}while(rs.next());
		}
				
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			pool.closeConnection(conn, ps, rs);
		}
		
		return validaCred;
	}
	
	public void guardaBitacora(String response, String ipConsumidor) throws Exception {
		try {
			
			conn = pool.getConnection("dbservicio");
			ps = conn.prepareStatement("select  * from tok_ins_bitacora(?,?,?)");
			
			ps.setDate(1, fechaHoy());
			ps.setString(2, response);
			ps.setString(3, ipConsumidor);
			 
			if(ps.execute()){
				System.out.println("se guardo en bitacora");
			}			
			}catch(Exception e) {
				log.error(ConstantesJwt.Oauth.log.PROCESS_INTERRUPTOR +" en la clase ["+ ConexionDB.class.getName() + " detalle de error: " + e.getMessage());
				throw new Exception(e.getMessage());
			}finally {
				pool.closeConnection(conn, ps);
			}
		
	}
	
	public java.sql.Date fechaHoy() {
		
		Date fechaActual = new Date();
		//pasar a formato SQL
		return  new java.sql.Date(fechaActual.getTime());
				
	}

}
