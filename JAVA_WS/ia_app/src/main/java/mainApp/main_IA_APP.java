package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import Logger.LoggerIA;
import MenuUtil.ActionsCLS;
import MenuUtil.MenusSQL;
import Config.InfoSQL;

public class main_IA_APP {
	
	private static Connection conexion=null;
	private static String usuario=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub.
		InfoSQL.iniciar();
		boolean opcionCorrecta=false;
		LoggerIA log=new LoggerIA("mainApp");
		String msg="Accediendo a la aplicación...";
		log.WriteMessage(msg);
		System.out.println(msg);
		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
			
		try {
			conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			conexion.setAutoCommit(false);
			msg="Conectado a BBDD.";
			log.WriteMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			msg="No se pudo conectar a la BBDD";
			log.WriteError(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteError(msg);
			System.out.println(msg);
			opcionCorrecta=false;
			while (opcionCorrecta==false) {
				System.out.println("\nSeleccione una opcion: \n    0 - Salir de la aplicacion\n    1 - Intentar reconectar");
				entradaTeclado = "";
		        //entradaEscaner = new Scanner (System.in);    //Creación de un objeto Scanner
		        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
		        //entradaEscaner.close();
		        if (entradaTeclado.equals("0") || entradaTeclado.equals("1")) {
		        	opcionCorrecta=true;
		        }else {
		        	System.out.println("Opcion no correcta. Vuelva a intentarlo.\n");
		        }
			}
			opcionCorrecta=false;
			if (entradaTeclado.equals("0")){
				msg="Saliendo de la aplicación...";
				log.WriteMessage(msg);
				System.out.println(msg);
				try {
					conexion.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				entradaEscaner.close();
				return;
			}else if (entradaTeclado.equals("1")){
				if (IntentaReconexion()==false) {
					msg="Saliendo de la aplicación...";
					log.WriteMessage(msg);
					System.out.println(msg);
					try {
						conexion.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
					entradaEscaner.close();
					return;
				}
			}
		}
		
		if(conectaUsuario()==false) {
			msg="Finalizada la ejecucion sin conectar usuario. Pulse cualquier tecla para finalizar.";
	        System.out.println(msg);
	        log.WriteMessage(msg);
	        entradaTeclado = "";
	        //entradaEscaner = new Scanner (System.in);    //Creación de un objeto Scanner
	        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
	        entradaEscaner.close();
	        return;
		}
        
        menuPrincipal(entradaEscaner);
        
        msg="Finalizada la ejecucion. Pulse cualquier tecla para finalizar.";
        System.out.println(msg);
        log.WriteMessage(msg);
        entradaTeclado = "";
        //entradaEscaner = new Scanner (System.in);    //Creación de un objeto Scanner
        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        entradaEscaner.close();
        try {
			conexion.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static boolean IntentaReconexion() {
		boolean opcionCorrecta=false;
		String entradaTeclado = "";
		Scanner entradaEscaner = null; //Creación de un objeto Scanner
		LoggerIA log=new LoggerIA("mainApp");
		String msg="Tratando de reconectar...";
		log.WriteMessage(msg);
		System.out.println(msg);
		try {
			conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			conexion.setAutoCommit(false);
			msg="Conectado a BBDD.";
			log.WriteMessage(msg);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			msg="No se pudo conectar a la BBDD";
			log.WriteError(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteError(msg);
			System.out.println(msg);
			
			while (opcionCorrecta==false) {
				System.out.println("\nSeleccione una opcion: \n    0 - Salir de la aplicacion\n    1 - Intentar reconectar");
				entradaTeclado = "";
		        entradaEscaner = new Scanner (System.in);    //Creación de un objeto Scanner
		        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
		        entradaEscaner.close();
		        if (entradaTeclado.equals("0") || entradaTeclado.equals("1")) {
		        	opcionCorrecta=true;
		        }else {
		        	System.out.println("Opcion no correcta. Vuelva a intentarlo.\n");
		        }
			}
			opcionCorrecta=false;
			if (entradaTeclado.equals("0")){
				return false;
			}else if (entradaTeclado.equals("1")){
				if (IntentaReconexion()==false) {
					return false; //ret
				}else {
					return true; //ret
				}
			}else {
				return false;
			}
			
			
			
		}
	}
	
	private static boolean conectaUsuario() {
		
		LoggerIA log=new LoggerIA("mainApp");
		String msg;
		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
		boolean opcioncorrecta=false;
		
		System.out.print("Indroduzca el usuario:    ");
		entradaTeclado = "";
        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        String user=entradaTeclado;

		System.out.print("Indroduzca la contraseña: ");
		entradaTeclado = "";
        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        String contra=entradaTeclado;

        try {
			Statement sta=conexion.createStatement();
			String query="SELECT COUNT(*) FROM IA_APP.T_USUARIOS WHERE NOMBRE = '"+user+"' AND CONTRA = '"+contra+"'";
			int cuenta=0;
			ResultSet res=sta.executeQuery(query);
			while(res.next()) {
				cuenta=res.getInt(1);
			}
			res.close();
			sta.close();
			
			if(cuenta>0) {
				usuario=user;
				msg="Conectado como "+usuario+"...";
				System.out.println(msg);
				log.WriteMessage(msg);
				return true;
			}else {
	
				opcioncorrecta=false;
				while (opcioncorrecta==false) {
					msg="Usuario y password incorrectos";
					System.out.println(msg);
					log.WriteMessage(msg);
					msg="¿Que desea hacer? \n    0 - Salir\n    1-Reintentar";
					System.out.println(msg);
					entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner´
					if (entradaTeclado.equals("0") || entradaTeclado.equals("1")) {
						opcioncorrecta=true;
			        }else {
			        	System.out.println("Opcion no disponible. Vuelva a intentarlo.\n\n");
			        }
				}
				if(entradaTeclado.equals("0")) {
					return false;
				}else if (entradaTeclado.equals("1")) {
					return conectaUsuario();
				}else {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="No se pudo consultar el usuario.";
			log.WriteError(msg);
			log.WriteError(e.toString());
			
			opcioncorrecta=false;
			while (opcioncorrecta==false) {
				msg="No se pudo acceder a la aplicacion. ¿Que desea hacer? \n    0 - Salir\n    1-Reintentar";
				System.out.println(msg);
				entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner´
				if (entradaTeclado.equals("0") || entradaTeclado.equals("1")) {
					opcioncorrecta=true;
		        }else {
		        	System.out.println("Opcion no disponible. Vuelva a intentarlo.\n\n");
		        }
			}
			if (entradaTeclado.equals("0")){
				return false;
			}else if (entradaTeclado.equals("1")){
				if (conectaUsuario()==false) {
					return false; //ret
				}else {
					return true; //ret
				}
			}else {
				return false;
			}	
		}
	}
	
	
	public static void menuPrincipal(Scanner entradaEscaner) {
		String accion=MenusSQL.getAction(conexion, "menuPrincipal", entradaEscaner);
		System.out.println("La accion elegida es: "+accion);
		ActionsCLS.procesaAccion(accion, conexion, entradaEscaner);
	}

}
