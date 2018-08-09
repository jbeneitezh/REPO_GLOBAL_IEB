package pruebas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jsoup.select.Elements;

import PCK_HTML.MorningStar;
import config.InfoSQL;
import config.LogGlobal;

public class pruebaMorningStar {

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
			Statement stat=micon.createStatement();
			String query="SELECT LINK FROM T_ACTIVOS WHERE FUENTE = 'MorningStar' AND TIPO_ACTIVO = 'Fondos RV'";
			ResultSet result=stat.executeQuery(query);
			while (result.next()) {
				String link=result.getString("LINK");
				//if (link.equals("http://www.morningstar.es/es/funds/snapshot/snapshot.aspx?id=FOGBR05KLU")){
					MorningStar morning=new MorningStar();
					morning.setLink(link);
					morning.obtenerDatosPrincipales();
					morning.Actualiza_T_ACTIVOS(micon);
				//}
			}
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
