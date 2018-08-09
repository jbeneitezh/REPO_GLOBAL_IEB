package Config;

import FilesAndFolders.FicherosCLS;

public class InfoSQL {
	private static String acceso="";
	private static String ip="";
	private static String puerto="";
	private static String user="";
	private static String key="";
	private static String ficConnection=RutaAplicacion()+"\\Config\\Connection.txt";
	private static String instancia="";
	private static String servicio="";
	private static String rutaWamp="";
	private static String rutaEmail=RutaAplicacion()+"\\conexion\\mail_config.txt";
	
	public InfoSQL(){
		
	}
	
	public static String RutaAplicacion(){
		return ConfigFolders.rutaApp();
	}
	
	
	public static void iniciar(){
		String lectura[][]=FicherosCLS.readFileMatrixString(ficConnection, ";");
		for(int i=0;i<lectura.length;i++){
			if (lectura[i][0].equals("ip")) {
				ip=lectura[i][1];
				//asignarPuerto(ip);
			}else if (lectura[i][0].equals("puerto")) {
				puerto=lectura[i][1];
			}else if (lectura[i][0].equals("user")) {
				user=lectura[i][1];
			}else if (lectura[i][0].equals("key")) {
				key=lectura[i][1];
			}else if (lectura[i][0].equals("instancia")){
				instancia=lectura[i][1];
			}else if (lectura[i][0].equals("servicio")){
				servicio=lectura[i][1];
			}else if (lectura[i][0].equals("rutaWamp")) {
				rutaWamp=lectura[i][1];
			}
		}
		acceso="jdbc:mysql://"+ip+":"+puerto;
	}
	public static String activeIp(){
		return ip;
	}
	public static String getFicConnection(){
		return ficConnection;
	}

	public static String getInstancia(){
		return instancia;
	}
	public static String getServicio(){
		return servicio;
	}
	public static String BBDDAccess(){
		//return "jdbc:oracle:thin:system@//192.168.0.155:1521/FOREXBBDD";
		return acceso;
		//return "SYSTEM";
	}
	public static String BBDDUser(){
		return user;
	}
	public static String BBDDKey(){
		//return "";
		return key;
	}	
	public static String getRutaWamp() {
		return rutaWamp;
	}
	public static String getRutaEmail() {
		return rutaEmail;
	}


	
	public static String  asignarPuerto(String IP) {
		try {
			String a=IP.substring(IP.length()-2, IP.length());
			int p=Integer.parseInt(a)+3000-30;
			puerto=String.valueOf(p);
			return puerto;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("InfoSQL::asignaPuerto::ERROR::No se pudo obtener la IP");
			System.out.println(e.toString());
			return "";
		}
		
		
	}
	
}
