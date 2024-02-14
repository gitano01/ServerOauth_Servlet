package com.msc.utils;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class PoolConexion {


	public Connection getConnection(String database) throws Exception, SQLException {
		Connection  con = null;
		try {
		InitialContext initialContext = new InitialContext();
		Context env = (Context) initialContext.lookup("java:comp/env/");
//		System.out.println(new Gson().toJson(env));
		DataSource ds = (DataSource) env.lookup("jdbc/"+database);	
		
		con = ds.getConnection();
		}catch (Exception ex) {
			ex.printStackTrace();
			//log.fatal(ex);
			throw new Exception("Se genero un error al solicitar la conexion a la base de datos ["+database+"]. "+ex.getMessage());
		}
		finally
		{
			if(con==null)
				throw new Exception("No se logro conectar con la base de datos ["+database+"]");
		}
		return con;
	}


	public void close(ResultSet rs) throws Exception, SQLException {
		rs.close();
	}

	public void close(PreparedStatement ps) throws Exception, SQLException {
		ps.close();
	}

	public void close(Connection conn) throws Exception, SQLException {
		conn.close();
	}

	public void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception, SQLException {
		if (rs != null) {
			rs.close();
		}

		if (ps != null) {
			ps.close();
		}

		if (conn != null) {
			conn.close();
		}
	}

	public void closeConnection(Connection conn, PreparedStatement ps) throws Exception, SQLException {
		if (ps != null) {
			ps.close();
		}

		if (conn != null) {
			conn.close();
		}
	}
}
