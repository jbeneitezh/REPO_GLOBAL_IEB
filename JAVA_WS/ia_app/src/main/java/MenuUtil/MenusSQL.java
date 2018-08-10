package MenuUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

import Logger.LoggerIA;

public class MenusSQL {
	public static Vector<menuOption> getMenuOpciones(Connection con, String menu){
		LoggerIA log=new LoggerIA("MenuSQL");
		String msg="Obteniendo opciones para el menu '"+menu+"'";
		log.WriteMessage(msg);
		try {
			String query="SELECT * FROM IA_APP.T_MENUS WHERE NOMBRE_MENU = '"+menu+"'";
			Statement sta=con.createStatement();
			ResultSet res=sta.executeQuery(query);
			Vector<menuOption>vRes=new Vector<>();
			while (res.next()) {
				menuOption men=new menuOption(menu, res.getInt(2), res.getString(3), res.getString(4));
				vRes.add(men);
				men=null;
			}
			res.close();
			sta.close();
			return vRes;
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error obteniendo opciones para el menu '"+menu+"'";
			log.WriteMessage(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteMessage(msg);
			System.out.println(msg);
			return null;
		}
		
	}
	
	public static String getAction(Connection con, String menu, Scanner entradaEscaner) {
		LoggerIA log=new LoggerIA("MenuSQL");
		String msg="";
		Vector<menuOption>opciones=getMenuOpciones(con, menu);
		if(opciones==null || opciones.size()<1) {
			msg="No se realizará ninguna accion";
			log.WriteError(msg);
			System.out.println(msg);
			return null;
		}
		
		boolean repetir=true;
		String accion="";
		while(repetir) {
			msg="";
			System.out.println(msg);
			log.WriteMessage(msg);
			msg="Seleccione una opcion de la lista:";
			System.out.println(msg);
			log.WriteMessage(msg);
			for(int i=0;i<opciones.size();i++) {
				msg=opciones.get(i).getMenuOption();
				System.out.println(msg);
				log.WriteMessage(msg);
			}
			String opcionElegida=entradaEscaner.nextLine();
			msg="Elegida: "+opcionElegida;
			log.WriteMessage(msg);
			boolean contenido=false;
			for(int i=0;i<opciones.size();i++) {
				//System.out.println(String.valueOf(opciones.get(i).getOpcion())+"=?"+opcionElegida);
				if(String.valueOf(opciones.get(i).getOpcion()).equals(opcionElegida)==true) {
					contenido=true;
					repetir=false;
					accion=opciones.get(i).getNextMenu();
					return accion;
				}
			}
			if(contenido==false) {
				msg="Opcion no valida. Vuelva a intentarlo";
				System.out.println(msg);
				log.WriteMessage(msg);
			}
			
		}
		return "";
	}
	
	public static boolean preguntaConfirmacion(String mensaje, Scanner scanner) {
		LoggerIA log=new LoggerIA("MenuSQL");
		boolean repetir=true;
		String msg="preguntaConfirmacion::Realizando pregunta de confirmacion";
		log.WriteMessage(msg);
		while(repetir) {
			msg="";
			System.out.println(msg);
			log.WriteMessage(msg);
			msg=mensaje;
			System.out.println(msg);
			log.WriteMessage(msg);
			msg="s / n";
			System.out.println(msg);
			log.WriteMessage(msg);
			String opcionElegida=scanner.nextLine();
			msg="Elegida: "+opcionElegida;
			log.WriteMessage(msg);
			
			if(opcionElegida.toUpperCase().equals("S")) {
				repetir=false;
				return true;
			}else if(opcionElegida.toUpperCase().equals("N")) {
				repetir=false;
				return false;
			}else {
				msg="Seleccion no valida. Debe seleccionar S / N";
				System.out.println(msg);
				log.WriteMessage(msg);
				repetir=true;
			}
			
		}
		return false;
	}
	public static String generaConsulta(String consulta, String [][]matOpciones, Scanner scanner, int iParamOUT){
		String msg="MaenusSQL::generaConsulta::Generando consulta...";
		LoggerIA log=new LoggerIA("MenusSQL");
		log.WriteMessage(msg,false);
		
		if(matOpciones.length<2 || matOpciones[0].length<2) {
			msg="MenusSQL::generaConsulta::La matriz no tiene registros. Retorna ''";
			log.WriteMessage(msg,true);
			return "";
		}
		
		boolean validado=false;
		while (validado==false) {
			msg="";
			log.WriteMessage(msg,true);
			msg=consulta;
			log.WriteMessage(msg,true);
			
			for(int i=1;i<matOpciones.length;i++) {
				msg="    "+matOpciones[i][0]+" - "+matOpciones[i][1];
				log.WriteMessage(msg,true);
			}
			
			String respuesta=scanner.nextLine();
			
			for(int i=0;i<matOpciones.length;i++) {
				if(matOpciones[i][0].equals(respuesta)) {
					respuesta=matOpciones[i][iParamOUT];
					validado=true;
					return respuesta;
				}else {
					msg="Opcion no disponible. Por favor, seleccione una de la lista.";
					log.WriteMessage(msg,true);
					validado=false;
				}
			}
			
		}
		
		return "";
		
		
		
		
	}
}
