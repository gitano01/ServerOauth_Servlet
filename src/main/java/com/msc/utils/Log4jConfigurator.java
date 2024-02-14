package com.msc.utils;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

public class Log4jConfigurator implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

		
	//	String workspaceDirectory = event.getServletContext().getRealPath("/").replace("\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\EfvServerOauth", "");
		
        // Concatena la ruta relativa del directorio de logs
        String logsDirectoryPath = "/var/log/efvServerOauth" +"/"+"logs";
		System.out.println("Ruta del directorio de logs: " + logsDirectoryPath);
	
		  // Crea el directorio si no existe
        File logsDirectory = new File(logsDirectoryPath);
        if (!logsDirectory.exists()) {
        	
            if (logsDirectory.mkdirs()) {
                System.out.println("Directorio de logs creado en: " + logsDirectory.getAbsolutePath());
            } else {
                System.err.println("Error al crear el directorio de logs.");
            }
        }
        
        
     // Configuraci�n de Log4j
     		String log4jConfigFile = event.getServletContext().getRealPath("/") + "WEB-INF/classes/log4j.properties";
     		PropertyConfigurator.configure(log4jConfigFile);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Aqu� puedes realizar tareas de limpieza al cerrar la aplicaci�n, si es
		// necesario.
	}

}
