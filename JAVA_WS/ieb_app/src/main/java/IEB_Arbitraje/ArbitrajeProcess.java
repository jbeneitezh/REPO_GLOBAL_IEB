package IEB_Arbitraje;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import DateAndTime.CronometroCLS;
import IEB.IEB_Querys;

import Utiles.resultsetUseful;
import config.InfoSQL;

public class ArbitrajeProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CronometroCLS crono=new CronometroCLS(50);
			crono.start();
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			Statement stat=conexion.createStatement();
			Vector<String>opciones=IEB_Querys.getIDActivos(stat, "OPTION");
			
			for(int i=0;i<opciones.size();i++) {
				//if(opciones.get(i).equals("30059")) {
					String Query=IEB_Querys.getQueryVolatilidadSubyacente(opciones.get(i));
					//System.out.println(Query);
					try {
						ResultSet res=stat.executeQuery(Query);
						String [][]matRes=resultsetUseful.resultsetToMatrix(res);
						if (matRes.length>1) {
							/*
							System.out.println("");
							Matrix.printMatrix(matRes);
							System.out.println("");
							*/
							ActivoArbitraje activoarbi=new ActivoArbitraje(matRes);
							activoarbi.getMatPricing();
							activoarbi.arbitrajeAnalisis();
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						String msg="Error al tirar la query de volatilidad por subyacente para el activo: "+opciones.get(i)+"\n "+e.toString();
						System.out.println(msg);
					}
				//}
			}
			
			stat.close();
			conexion.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
