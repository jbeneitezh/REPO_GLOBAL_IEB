package config;

public class InfoIEB {
	public static String rutaPrincipal () {
		return "C:\\IEB_APP";
	}
	public static String rutaExport () {
		return rutaPrincipal()+"\\Exports";
	}
	
	public static String rutaExportDrive1() {
		return "C:\\Users\\Javier\\Google Drive\\MASTER\\Gestión_Renta_Variable\\Exports";
	}
	public static String rutaExportDrive2() {
		return "C:\\Users\\Javier\\Google Drive\\MASTER\\Gestion_Renta_Variable_Compartido\\Exports";
	}
	public static String rutaListaFicheros() {
		return rutaExport()+"\\Activos.csv";
	}
	public static double DiasBaseAnho() {
		return 365;
	}
}
