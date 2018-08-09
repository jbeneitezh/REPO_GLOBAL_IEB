package IEB_Mains;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import DateAndTime.CronometroCLS;
import FilesAndFolders.FicherosCLS;
import IEB.FutureInfo;
import IEB.IEB_Querys;
import IEB.VolatilitySurface;
import IEB_History_Data.SubyacentesSeries;
import Utiles.resultsetUseful;
import config.InfoIEB;
import config.InfoSQL;

public class IEB_Automatic_Downloads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int diaActual=0;
		int pausa=15;
		int vuelta=0;
		while(true) {
			try {
				
				System.out.println("Comprobando necesidad de exportar volatilidades...");
				Calendar fecha = new GregorianCalendar();
		        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		        if(dia!=diaActual) {
		        	diaActual=dia;
		        	System.out.println("Se migrarán las volatilidades e históricos...");
		        	Thread.sleep(2000);
		        	MigraVolatilidades();
		        	System.out.println("Se actualizarán los históricos...");
		        	AlphaVantageDownload.main(null);
		        	System.out.println("Históricos actualizados");
		        	System.out.println("Se exportarán los historicos");
		        	SubyacentesSeries series=new SubyacentesSeries();
		        	series.getSubyacentesSeries();
		        }
				
				CronometroCLS crono=new CronometroCLS(50);
				crono.start();
				InfoSQL.iniciar();
				Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
				Statement stat=conexion.createStatement();
				Vector<String>opciones=IEB_Querys.getIDActivos(stat, "OPTION");
				Vector<String>futuros=IEB_Querys.getIDActivos(stat, "FUTURE");
				FicherosCLS.deleteFile(InfoIEB.rutaListaFicheros());
				/*
				for (int j=0;j<opciones.size();j++) {
					String activo=opciones.get(j);
					VolatilitySurface vol=new VolatilitySurface(activo, conexion);
					vol.run();
				}
				for (int j=0;j<futuros.size();j++) {
					String activo=futuros.get(j);
					FutureInfo fut=new FutureInfo(conexion, activo);
					fut.run();
				}
				*/
				
				VolatilitySurface []vol=new VolatilitySurface [opciones.size()] ;
				FutureInfo []fut=new FutureInfo [futuros.size()] ;
				
				for (int j=0;j<opciones.size();j++) {
					String activo=opciones.get(j);
					VolatilitySurface volai=new VolatilitySurface(activo, conexion);
					vol[j]=volai;
					vol[j].run();
					volai=null;
				}
				for (int j=0;j<futuros.size();j++) {
					String activo=futuros.get(j);
					FutureInfo futi=new FutureInfo(conexion, activo);
					fut[j]=futi;
					fut[j].run();
					futi=null;
				}
				while (IsAnyoneWorking(vol, fut)) {
					//System.out.println("Hilos trabajando...");
					try {
						Thread.sleep(150);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				
				anexoExcelExportacion(stat);
				
				stat.close();
				conexion.close();
				vuelta++;
				System.out.println("FIN DE LA VUELTA "+vuelta+" - "+String.valueOf(crono.getSeconds())+" segundos.");
				//crono.
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("PETEEEEEEEEEEEEEE");
				System.out.println(e.toString());
			}
			
			
			try {
				System.out.println("Realizando pausa de "+pausa+" segundos");
				Thread.sleep(pausa*1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private static boolean IsAnyoneWorking(VolatilitySurface vol[], FutureInfo fut[]) {
		try {
			boolean res=false;
			for(int i=0;i<vol.length;i++) {
				if(vol[i].isWorking()) {
					res=true;
				}
			}
			for(int i=0;i<fut.length;i++) {
				if(fut[i].isWorking()) {
					res=true;
				}
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("IEB_Automatic_Downloads::IsAnyoneWorking::ERROR\n"+e.toString());
			return false;
		}
		
	}
	private static void anexoExcelExportacion(Statement stat) {
		try {
			String Query=IEB_Querys.getExcelVolatilidadesQuery();
			String ruta=InfoIEB.rutaExportDrive1()+"\\";
			String nombre="VolatilidadesExcel";
			ResultSet res=stat.executeQuery(Query);
			resultsetUseful.toCSV(ruta, nombre, res);
			
			ruta=InfoIEB.rutaExportDrive2()+"\\";
			resultsetUseful.toCSV(ruta, nombre, res);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("IEB_Automatic_Downloads::anexoExcelExportacion::ERROR\n"+e.toString());
		}
		
		
	}
	
	
	public static void MigraVolatilidades() {
		// TODO Auto-generated method stub
		String []periodos=new String[2];
		periodos[0]="D01";
		periodos[1]="H01";
		//periodos[2]="M10";
		
		System.out.println("EXPORTANDO VOLATILIDADES...");
		
		for(int i=0;i<periodos.length;i++) {
			
			String Query="SELECT * FROM IEB_PRO.T_VOLATILIDADES_"+periodos[i];
			String ruta=InfoIEB.rutaExport()+"\\";
			try {

				InfoSQL.iniciar();
				Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
				Statement stat=conexion.createStatement();
				ResultSet res=stat.executeQuery(Query);
				resultsetUseful.toCSV(ruta, "T_VOLATILIDADES_"+periodos[i], res);
				res.close();
				stat.close();
				conexion.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Fallo en la vuelta "+i+"\n"+e.toString());
			}
			
			Query="SELECT * FROM IEB_PRO.T_SUBYACENTES_HISTO_"+periodos[i];
			try {

				InfoSQL.iniciar();
				Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
				Statement stat=conexion.createStatement();
				ResultSet res=stat.executeQuery(Query);
				resultsetUseful.toCSV(ruta, "T_SUBYACENTES_HISTO_"+periodos[i], res);
				res.close();
				stat.close();
				conexion.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Fallo en la vuelta "+i+"\n"+e.toString());
			}
			System.out.println("Fin vuelta "+i);
		}
		
	}

}
