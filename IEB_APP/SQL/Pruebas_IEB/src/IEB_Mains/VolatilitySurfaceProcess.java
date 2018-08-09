package IEB_Mains;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Vector;

import FilesAndFolders.FicherosCLS;
import IEB.IEB_Querys;
import IEB.VolatilitySurface;

import config.InfoIEB;
import config.InfoSQL;

public class VolatilitySurfaceProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int repeticiones=100000;
		
		for (int i=0;i<repeticiones;i++) {
			try {
				
				
				
				
				InfoSQL.iniciar();
				Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
				Statement stat=conexion.createStatement();
				Vector<String>activos=IEB_Querys.getIDActivos(stat);
				FicherosCLS.deleteFile(InfoIEB.rutaListaFicheros());
				/*
				int maxNhilos=100;
				Thread[]misthreads=new Thread[maxNhilos];
				Vector<VolatilitySurface> vols=new  Vector<VolatilitySurface>();
				for (int i=0;i<maxNhilos;i++) {
					VolatilitySurface vol=new VolatilitySurface(String.valueOf(30000+i),conexion);
					vols.addElement(vol);
					misthreads[i]=new Thread(vols.get(i));
					misthreads[i].run();
				}
				*/
				/*VolatilitySurface vol=new VolatilitySurface("30001", conexion);
				vol.run();*/
				
				for (int j=0;j<activos.size();j++) {
					String activo=activos.get(j);
					VolatilitySurface vol=new VolatilitySurface(activo, conexion);
					vol.run();
				}
				
				conexion.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("PETEEEEEEEEEEEEEE");
				System.out.println(e.toString());
			}
			
			System.out.println("FIN DE LA VUELTA "+i);
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
		
		
		
	}

}

