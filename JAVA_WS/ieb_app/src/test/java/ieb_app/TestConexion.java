package ieb_app;

import java.sql.Connection;
import java.sql.DriverManager;

import config.InfoSQL;
import config.LogGlobal;

public class TestConexion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resultado;
		InfoSQL.iniciar();
		System.out.println(InfoSQL.BBDDAccess());
		System.out.println(InfoSQL.BBDDUser());
		System.out.println(InfoSQL.BBDDKey());
		
		try {
			Connection micon=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			//Connection micon=DriverManager.getConnection("jdbc:mysql://localhost:3306", InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			micon.close();
			resultado="Conectado";
		} catch (Exception e) {
			// TODO: handle exception
			resultado="No conectado";
		}
		System.out.println(resultado);
		LogGlobal.WriteMessage("Prueba de conexion realizada con resultado: "+resultado);
	}
	
	public static boolean realizaTestConexion(){
		boolean res=false;
		InfoSQL.iniciar();
		System.out.println("Realizando test de conexion");
		try {
			//Connection micon=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			Connection micon=DriverManager.getConnection("jdbc:mysql://localhost:3306", InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			micon.close();
			res=true;
			System.out.println("Conexion verificada con exito");
		} catch (Exception e) {
			// TODO: handle exception
			String msg="Fallo al verificar la conexion";
			LogGlobal.WriteCritical(msg);
		}
		return res;
	}
}
