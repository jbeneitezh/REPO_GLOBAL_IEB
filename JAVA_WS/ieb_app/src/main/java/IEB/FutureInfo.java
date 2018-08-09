package IEB;


import java.sql.Connection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DateAndTime.FechaSimple;
import FilesAndFolders.FicherosCLS;
import PCK_HTML.JSoupCon;
import config.InfoIEB;


public class FutureInfo extends Thread {
	
	private String nombre;
	private String titulo;
	private String ultPrecioStr;
	private double ultPrecioDbl;
	private String fecha;
	private Connection conexion;
	boolean working=false;
	
	public FutureInfo(Connection conexio, String Nombr) {
		nombre=Nombr;
		conexion=conexio;
		fecha=FechaSimple.FechaSistemaCompletaString();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		working=true;
		getFutureInfo(nombre);
		if(isNumeric(ultPrecioStr)) {
			sendInfo();
		}
		working=false;
	}
	
	
	private void getFutureInfo(String name) {
		
		System.out.println(name+" Probando...");
		
		String link="https://ieb-simulador.com/cgi-bin/ver_precios.cgi?id_activo="+name;
		
		Document doc;
		
	
		 try {
			 doc=JSoupCon.getLink(link);
			 Element tabla=doc.selectFirst("TABLE");
			 Elements filas=tabla.select("TR");
			 //Vector<String[]>filasStr=new Vector<>();
			 Element Titulo = filas.get(0).select("TD").get(0);
			 titulo=Titulo.text();
			 
			 Element Precio=filas.get(3).select("TD").get(2);
			 ultPrecioStr=Precio.text().replace(".", "")
								       .replace(",", ".")
								       .replace(" ", "")
								       .replace("€", "")
								       .replace("$", "");
			 if(isNumeric(ultPrecioStr)) {
				 
				 ultPrecioDbl=Double.parseDouble(ultPrecioStr);
	
				System.out.println(nombre+";"+titulo);
				System.out.println("PrecioStr: "+ultPrecioStr+" - PrecioDbl: "+ultPrecioDbl);
				System.out.println(filas.size()+" filas.");
								 
				FicherosCLS.writeLine(InfoIEB.rutaListaFicheros(), nombre+";"+titulo+";"+ultPrecioDbl);
				 
			 }
			 
			 	
		 } catch (Exception e) {
			 e.printStackTrace();
		 	
		 }
        
        
	}
	
	private void sendInfo() {
		try {
			IEB_MyProcedures.PR_UPDATE_HISTORIC_FUTURES(conexion, fecha, nombre, ultPrecioStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static boolean isNumeric(String cadena){
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe){
			try{
				Double.parseDouble(cadena);
				return true;
			}catch(Exception e){
				return false;
			}
		}
	}

	public boolean isWorking() {
		return working;
	}

}

