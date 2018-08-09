package IEB_History_Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import FilesAndFolders.DirectorioCLS;
import FilesAndFolders.FicherosCLS;
import config.InfoSQL;

public class VolatilityProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SELECCIONAR LA CARPETA DONDE ESTAN LOS FICHEROS
		String rutaImportaciones="C:\\IEB_APP\\Import\\volatilidades_historicos";
		Vector<String>ficheros=DirectorioCLS.GetDirectoryFilesVector(rutaImportaciones);
		for(int i=0;i<ficheros.size();i++) {
			String ruta=rutaImportaciones+"\\"+ficheros.get(i);
			procesarFichero(ruta);
		}
		
		
	}
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
		
		
		
	}
}
