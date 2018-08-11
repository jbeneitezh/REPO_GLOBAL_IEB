package IEB_History_Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import IEB.IEB_Querys;
import Utiles.resultsetUseful;
import config.InfoIEB;
import config.InfoSQL;

public class SubyacentesSeries {
	private Vector<String> ids_subyacentes=new Vector<>();
	public SubyacentesSeries() {
		
	}
	
	public void getSubyacentesSeries() {
		try {
			InfoSQL.iniciar();
			
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			ids_subyacentes=IEB_Querys.getSubyacentesUso(conexion);
			for(int i=0;i<ids_subyacentes.size();i++) {
				System.out.println("Subyacente "+i+" - "+ids_subyacentes.get(i));
			}
			
			String Query=IEB_Querys.QueryPerimetroHistoricoSubyacente_v2(ids_subyacentes);
			System.out.println(Query);
			
			
			String ruta=InfoIEB.rutaExportDrive1()+"\\";
			Statement sta=conexion.createStatement();
			ResultSet res=sta.executeQuery(Query);
			resultsetUseful.toCSV(ruta, "Historico_Subyacentes", res);
			
			/*
			int k=0;
			while(res.next()) {
				k++;
				System.out.println(k+" - '"+res.getString(1)+"'");
			}
			*/
			
			res.close();
			sta.close();
			
			conexion.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			
		}
	}
}
