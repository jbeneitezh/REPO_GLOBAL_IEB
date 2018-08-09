package Menus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import Logger.LoggerIA;
import MenuUtil.ActionsCLS;

public class MENU_CREA_ESCENARIO {
	private Connection conexion;
	private String nombre="";
	private Scanner scanner;
	
	public MENU_CREA_ESCENARIO(Connection connection, Scanner sca) {
		// TODO Auto-generated constructor stub
		conexion=connection;
		scanner=sca;
	}
	public void crearNuevoEscenario() {
		LoggerIA log=new LoggerIA("MENU_CREA_ESCENARIO");
		String msg="Ha seleccionado crear un nuevo escenario.";
		log.WriteMessage(msg);
		System.out.println(msg);
		boolean nombrevalido=false;
		while(nombrevalido==false) {
			msg="Indique un nombre para el escenario: ";
			log.WriteMessage(msg);
			System.out.print(msg);
			nombre=scanner.nextLine();
			log.WriteMessage(nombre);
			if (validaNombre(nombre)==true) {
				nombrevalido=true;
				creaEscenario(nombre);
			}
		}
		ActionsCLS.procesaAccion("menuPrincipal", conexion, scanner);
		
	}
	
	
	
	
	
	private boolean validaNombre(String nom) {
		String msg="";
		LoggerIA log=new LoggerIA("MENU_CREA_ESCENARIO");
		msg="Validando el nombre: "+nom;
		log.WriteMessage(msg);
		try {
			Statement sta=conexion.createStatement();
			String Query="SELECT COUNT(*) FROM IA_APP.T_ESCENARIOS WHERE NOMBRE = '"+nom+"'";
			ResultSet res=sta.executeQuery(Query);
			int cuenta=0;
			while (res.next()) {
				cuenta=res.getInt(1);
			}
			if(cuenta==0) {
				msg="Nombre valido: "+nom;
				log.WriteMessage(msg);
				return true;
			}else {
				msg="Nombre no valido. Ya existe: "+nom;
				log.WriteMessage(msg);
				System.out.println(msg);
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="validaNombre::Error al validar el nombre: "+nom;
			log.WriteError(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteError(msg);
			System.out.println(msg);
			return false;
		}

	}
	
	private void creaEscenario(String nom) {
		
		String msg="Creando escenario '"+nom+"'...";
		LoggerIA log=new LoggerIA("MENU_CREA_ESCENARIO");
		log.WriteMessage(msg);
		System.out.println(msg);
		
		try {
			CallableStatement stat=conexion.prepareCall("{call IA_APP.NUEVO_ESCENARIO(?, ?)}");
			stat.setString(1, nombre);
			stat.registerOutParameter(2, java.sql.Types.INTEGER);
			stat.execute();
			int res=stat.getInt(2);
			if (res==1) {
				conexion.commit();
				msg="Se creo el escenario '"+nom+"'.";
				log.WriteMessage(msg);
				System.out.println(msg);
				msg="Se añadieron valores por defecto '"+nom+"'.";
				log.WriteMessage(msg);
				System.out.println(msg);
			}else if (res==0) {
				conexion.rollback();
				msg="Algo fallo al crear el escenario '"+nom+"'";
				log.WriteError(msg);
				System.out.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conexion.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			msg="Algo fallo al crear el escenario '"+nom+"'";
			log.WriteError(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteError(msg);
			System.out.println(msg);
		}
	}
}
