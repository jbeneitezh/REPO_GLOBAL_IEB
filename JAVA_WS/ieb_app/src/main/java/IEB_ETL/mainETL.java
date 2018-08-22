package IEB_ETL;

import java.sql.Connection;
import java.sql.DriverManager;

import config.InfoSQL;

public class mainETL {

	public static void main(String []args) {
		int num=5;
		double valor=0.05/num;
		System.out.println( valor);
		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			conexion.setAutoCommit(false);
			//RtosTables.creaTablasRtos(conexion);
			Promedios_Table.generaPromedios(conexion);
			conexion.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
