package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;

import FilesAndFolders.FicherosCLS;
import IEB.FutureInfo;
import IEB.IEB_Querys;
import config.InfoIEB;
import config.InfoSQL;

public class IEB_FutureInfo {
	public static void main(String[] args) {
		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			Statement sta=conexion.createStatement();
			FicherosCLS.deleteFile(InfoIEB.rutaListaFicheros());
			Vector<String>listaFuturos=IEB_Querys.getIDActivos(sta, "FUTURE");
			for (int i=0;i<listaFuturos.size();i++) {
				String idStr=listaFuturos.get(i);
				FutureInfo fut=new FutureInfo(conexion,idStr);
				fut.run();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("IEB_Future::ERROR::"+e.toString());
		}
		
		
	}
}
