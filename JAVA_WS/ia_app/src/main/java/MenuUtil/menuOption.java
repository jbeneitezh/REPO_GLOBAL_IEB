package MenuUtil;

import java.util.Scanner;
import java.util.Vector;

import Logger.LoggerIA;

public class menuOption {
	
	private String nombre="";
	private String descr="";
	private int opcion;
	private String nextMenu="";
	
	public menuOption(String nombreM, int opcionM, String DescrM, String NextMenu) {
		nombre=nombreM;
		opcion=opcionM;
		descr=DescrM;
		nextMenu=NextMenu;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getOpcion() {
		return opcion;
	}
	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
	public String getNextMenu() {
		return nextMenu;
	}
	public void setNextMenu(String nextMenu) {
		this.nextMenu = nextMenu;
	}
	
	public String getMenuOption() {
		return "    "+opcion+" - "+descr;
	}
	
	
	public static boolean existeOpcion(Vector<String>vector, String nombre) {
		String msg="";
		LoggerIA log=new LoggerIA("menuOption");
		msg="menuOption::existeOpcion::Evaluando opciones";
		log.WriteMessage(msg);
		try {
			if(vector.size()<1) {
				msg="menuOption::existeOpcion::No existen registros";
				log.WriteMessage(msg);
				return false;
			}
			
			for(int i=0;i<vector.size();i++) {
				if(vector.get(i).equals(nombre)) {
					msg="menuOption::existeOpcion::opcion ('"+nombre+"') OK";
					log.WriteMessage(msg);
					return true;
				}
			}
			msg="menuOption::existeOpcion::opcion ('"+nombre+"') KO!!";
			log.WriteMessage(msg);
			return false;
			
		} catch (Exception e) {
			// TODO: handle exception
			msg="menuOption::existeOpcion::ERROR";
			log.WriteError(msg);
			msg=e.toString();
			log.WriteError(msg);
			return false;
		}
		
	}
	
	public static String procesaConsultaSINO(String consulta, Scanner scanner) {
		
		LoggerIA log=new LoggerIA("menuOption");
		String msg="Procesando consulta de S/N";
		log.WriteMessage(msg);
		boolean valorcorrecto=false;
		while (valorcorrecto==false) {
			msg="";
			System.err.println(msg);
			log.WriteMessage(msg);
			msg=consulta;
			System.err.println(msg);
			log.WriteMessage(msg);
			String respuesta=""+scanner.nextLine();
			if(respuesta.toUpperCase().equals("S") || respuesta.toUpperCase().equals("N")){
				log.WriteMessage(respuesta);
				return respuesta.toUpperCase();
			}else {
				msg="Opcion no valida ('"+respuesta+"')";
				System.err.println(msg);
				log.WriteMessage(msg);
			}
			
		}
		return "N";
	}
	
}
