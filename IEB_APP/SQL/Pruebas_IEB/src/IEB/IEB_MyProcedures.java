package IEB;

import java.sql.CallableStatement;
import java.sql.Connection;

public class IEB_MyProcedures {
	
	public static boolean PR_UPDATE_HISTORIC_OPTIONS(Connection con, String fecha, String activo, String precio) {
		try {
			
			CallableStatement stat=con.prepareCall("{call PR_UPDATE_HISTORIC_OPTIONS(?,?,?,?)}");
			stat.setString(1, fecha);
			stat.setInt(2, Integer.parseInt(activo));
			stat.setDouble(3, Double.parseDouble(precio));
			//stat.setInt(2, res);
	        stat.registerOutParameter(4, java.sql.Types.INTEGER);
	        stat.execute();
	        int res=stat.getInt(4);
	        stat.close();
			
	        System.out.println("fecha: "+fecha+" - activo: "+activo);
	        System.out.println("Precio: "+precio+" - "+Double.parseDouble(precio));
	        
			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PR_UPDATE_HISTORIC_OPTIONS::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
	
	public static boolean PR_UPDATE_VOLAT_HISTO(Connection con,
												int IDActivo,
												String fecActual,
												String fecVto,
												double ATM,
												double vol80,
												double vol90,
												double vol100,
												double vol110,
												double vol120,
												double tasaR) {
		try {
			
			CallableStatement stat=con.prepareCall("{call PR_UPDATE_VOLAT_HISTO(?,?,?,?,?,?,?,?,?,?,?)}");
			stat.setInt(1, IDActivo);
			stat.setString(2, fecActual);
			stat.setString(3, fecVto);
			stat.setDouble(4, ATM);
			stat.setDouble(5, vol80);
			stat.setDouble(6, vol90);
			stat.setDouble(7, vol100);
			stat.setDouble(8, vol110);
			stat.setDouble(9, vol120);
			stat.setDouble(10, tasaR);
			stat.registerOutParameter(11, java.sql.Types.INTEGER);
	        stat.execute();
	        int res=stat.getInt(11);
	        stat.close();
	        
			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PR_UPDATE_VOLAT_HISTO::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
}
