package IEB_History_Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import FilesAndFolders.DirectorioCLS;
import FilesAndFolders.FicherosCLS;
import IEB.IEB_Querys;
import config.InfoSQL;

public class SubyacenteProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		procesoEsInvestingImportacion();

	}
	/*
	public static void procesarFichero(String fichero) {

		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			
			String matFic[][]=FicherosCLS.readFileMatrixStringCodification(fichero, ";", "UTF8");
			for(int i=0;i<matFic.length;i++) {
				String fila[]=matFic[i];
				VolatilityRow volFila=new VolatilityRow(conexion, fila);
				volFila.insertaFila();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("VolatilityProcess::procesarFichero::ERROR::"+fichero+" \n"+e.toString());
		}
		
		
		
	}*/
	
	public static void procesoEsInvestingImportacion() {
		String rutaImportaciones="C:\\IEB_APP\\Import\\subyacente_historicos";
		Vector<String>ficheros=DirectorioCLS.GetDirectoryFilesVector(rutaImportaciones);
		for(int i=0;i<ficheros.size();i++) {
			String ruta=rutaImportaciones+"\\"+ficheros.get(i);
			String nombre=ficheros.get(i).replace(".csv", "").replace("Datos históricos ","");
			procesarFicheroEsInvesting(ruta, nombre);
		}
	}
	public static void procesarFicheroEsInvesting(String fichero, String nombre) {

		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			String nombreBBDD=IEB_Querys.getEsInvestingSubyacenteNombre(conexion, nombre);
			String matFic[][]=FicherosCLS.readFileMatrixStringCodification(fichero, "\",\"", "UTF8");

			for(int i=0;i<matFic.length;i++) {
				String fila[]=matFic[i];
				SubyacenteRow subyFila=new SubyacenteRow(conexion, nombreBBDD, fila);
				subyFila.insertaFila();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubyacenteProcess::procesarFichero::ERROR::"+fichero+" \n"+e.toString());
		}
		
		
		
	}
	
	
}
