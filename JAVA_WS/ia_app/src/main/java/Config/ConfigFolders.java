package Config;

public class ConfigFolders {
	
	/*********************************************************/
	/*******************VARIABLE DE ENTORNO*******************/
	/*********************************************************/
	
	private static String entorno="windows";
	
	/*********************************************************/
	/*******************VARIABLE DE ENTORNO*******************/
	/*********************************************************/
	
	public static String rutaApp () {
		if(entorno.equals("windows")) {
			return "G:\\IA_APP_v1";
		}else {
			return "";
		}
	}
	public static String rutaConfig () {
		if(entorno.equals("windows")) {
			return rutaApp()+"\\config";
		}else {
			return rutaApp()+"/config";
		}
	}
	public static String rutaExport () {
		if(entorno.equals("windows")) {
			return rutaApp()+"\\exports";
		}else {
			return rutaApp()+"/exports";
		}
	}
	
	public static String rutaImport() {
		if(entorno.equals("windows")) {
			return rutaApp()+"\\imports";
		}else {
			return rutaApp()+"/imports";
		}
	}
	public static String rutaLog() {
		if(entorno.equals("windows")) {
			return rutaApp()+"\\Logs";
		}else {
			return rutaApp()+"/Logs";
		}
	}


}
