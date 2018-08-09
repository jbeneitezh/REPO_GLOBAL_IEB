package config;

public class InfoIEB {
	public static String rutaPrincipal () {
		return "C:\\IEB_APP";
	}
	public static String rutaExport () {
		return rutaPrincipal()+"\\Exports";
	}
	public static String rutaListaFicheros() {
		return rutaExport()+"\\Activos.csv";
	}
}
