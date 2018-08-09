package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import FilesAndFolders.FicherosCLS;
import IEB.IEB_Querys;
import IEB.VolatilitySurface;
import Utiles.Matrix;
import config.InfoIEB;
import config.InfoSQL;

public class IEBGetVolMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int repeticiones=10000;
		
		try {
			
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			Statement stat=conexion.createStatement();
			Vector<String>activos=IEB_Querys.getIDActivos(stat);
			FicherosCLS.deleteFile(InfoIEB.rutaListaFicheros());

			for (int j=0;j<activos.size();j++) {
				String [][] matVol=IEB_Querys.getActualVolMatrix(stat, activos.get(j));
				System.out.println("\n\n");
				Matrix.printMatrix(matVol);
				Thread.sleep(5000);
			}
			
			conexion.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PETEEEEEEEEEEEEEE");
			System.out.println(e.toString());
		}
			
	}

}
