package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;

import DateAndTime.CronometroCLS;
import FilesAndFolders.FicherosCLS;
import IEB.FutureInfo;
import IEB.IEB_Querys;
import IEB.VolatilitySurface;
import config.InfoIEB;
import config.InfoSQL;

public class IEB_VolSurfPrueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CronometroCLS crono=new CronometroCLS(50);
			crono.start();
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			Statement stat=conexion.createStatement();

			FicherosCLS.deleteFile(InfoIEB.rutaListaFicheros());
			
			
			String activo="30001";
			VolatilitySurface volai=new VolatilitySurface(activo, conexion);
			volai.run();
			volai.run();
			volai=null;

			
			conexion.close();

			//crono.
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PETEEEEEEEEEEEEEE");
			System.out.println(e.toString());
		}
	}

}
