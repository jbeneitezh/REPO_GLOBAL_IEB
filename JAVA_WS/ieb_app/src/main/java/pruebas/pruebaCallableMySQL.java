package pruebas;


import java.sql.*;

import config.InfoSQL;
import config.LogGlobal;

public class pruebaCallableMySQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String textoComprobar="MorningStar";
		int res=0;
		String resultado;
		InfoSQL.iniciar();
		System.out.println(InfoSQL.BBDDAccess());
		try {
			Connection micon=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			resultado="Conectado";
			System.out.println(resultado);
			CallableStatement stat=micon.prepareCall("{call ACCION_EAFI_PRE.PR_EX_PK_T_FUENTES(?,?)}");
			stat.setString(1, textoComprobar);
			//stat.setInt(2, res);
	        stat.registerOutParameter(2, java.sql.Types.INTEGER);
	        stat.execute();
	        res=stat.getInt(2);
	        stat.close();
	        micon.close();
	        System.out.println(textoComprobar+" - "+res+" registros.");
			
		} catch (Exception e) {
			// TODO: handle exception
			resultado=e.toString();
		}
		System.out.println(resultado);
		LogGlobal.WriteMessage("Prueba de conexión realizada con resultado: "+resultado);
	}

}
