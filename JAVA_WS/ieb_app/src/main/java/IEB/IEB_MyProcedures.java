package IEB;

import java.sql.CallableStatement;
import java.sql.Connection;


public class IEB_MyProcedures {
	
	public static boolean PR_UPDATE_HISTORIC_OPTIONS(Connection con, String fecha, String activo, String precio) {
		try {
			
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_UPDATE_HISTORIC_OPTIONS(?,?,?,?)}");
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
	
	public static boolean PR_UPDATE_HISTORIC_FUTURES(Connection con, String fecha, String activo, String precio) {
		try {
			
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_UPDATE_HISTORIC_FUTURES(?,?,?,?)}");
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
			System.out.println("PR_UPDATE_HISTORIC_FUTURES::ERROR");
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
												double vol85,
												double vol90,
												double vol95,
												double vol100,
												double vol105,
												double vol110,
												double vol115,
												double vol120,
												double tasaR) {
		try {
			
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_UPDATE_VOLAT_HISTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			stat.setInt(1, IDActivo);
			stat.setString(2, fecActual);
			stat.setString(3, fecVto);
			stat.setDouble(4, ATM);
			stat.setDouble(5, vol80);
			stat.setDouble(6, vol85);
			stat.setDouble(7, vol90);
			stat.setDouble(8, vol95);
			stat.setDouble(9, vol100);
			stat.setDouble(10, vol105);
			stat.setDouble(11, vol110);
			stat.setDouble(12, vol115);
			stat.setDouble(13, vol120);
			stat.setDouble(14, tasaR);
			stat.registerOutParameter(15, java.sql.Types.INTEGER);
	        stat.execute();
	        int res=stat.getInt(15);
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
	

	public static boolean PR_INSERTA_BOLETA_OPCION(Connection con,
												   String fecOrden,
												   String nombre,
												   int titulos,
												   double precio,
												   double tipoCambio,
												   double subyacente,
												   String call_put,
												   double strike,
												   String vencimiento,
												   double volatilidad,
												   double tasaR,
												   String compra_venta) {
		try {
			
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_INSERTA_BOLETA_OPCION(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			stat.setString(1, fecOrden);
			stat.setString(2, nombre);
			stat.setInt(3, titulos);
			stat.setDouble(4, precio);
			stat.setDouble(5, tipoCambio);
			stat.setDouble(6, subyacente);
			stat.setString(7, call_put);
			stat.setDouble(8, strike);
			stat.setString(9, vencimiento);
			stat.setDouble(10, volatilidad);
			stat.setDouble(11, tasaR);
			stat.setString(12, compra_venta);
			stat.registerOutParameter(13, java.sql.Types.INTEGER);
	        stat.execute();
	        int res=stat.getInt(13);
	        stat.close();
	        
			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PR_INSERTA_BOLETA_OPCION::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
	
	
	public static boolean PR_INSERTA_BOLETA_FUTURO(Connection con,
												   String fecOrden,
												   String nombre,
												   int titulos,
												   String compra_venta,
												   double precio,
												   double tipoCambio,
												   String vencimiento) {
		try {

			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_INSERTA_BOLETA_FUTURO(?,?,?,?,?,?,?,?)}");
			stat.setString(1, fecOrden);
			stat.setString(2, nombre);
			stat.setInt(3, titulos);
			stat.setString(4, compra_venta);
			stat.setDouble(5, precio);
			stat.setDouble(6, tipoCambio);
			stat.setString(7, vencimiento);
			stat.registerOutParameter(8, java.sql.Types.INTEGER);
			stat.execute();
			int res=stat.getInt(8);
			stat.close();

			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("PR_INSERTA_BOLETA_FUTURO::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
	
	public static boolean PR_HISTORICAL_ATM_VOL(Connection con,
											   String nombre,
											   String fecha,
											   double vola1,
											   double vola2,
											   double vola3,
											   double vola4,
											   double vola5,
											   double vola6,
											   String fec1,
											   String fec2,
											   String fec3,
											   String fec4,
											   String fec5,
											   String fec6) {
		try {
		
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_HISTORICAL_ATM_VOL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			stat.setString(1, nombre);
			stat.setString(2, fecha);
			stat.setDouble(3, vola1);
			stat.setDouble(4, vola2);
			stat.setDouble(5, vola3);
			stat.setDouble(6, vola4);
			stat.setDouble(7, vola5);
			stat.setDouble(8, vola6);
			stat.setString(9,  fec1);
			stat.setString(10, fec2);
			stat.setString(11, fec3);
			stat.setString(12, fec4);
			stat.setString(13, fec5);
			stat.setString(14, fec6);
			stat.registerOutParameter(15, java.sql.Types.INTEGER);
			stat.registerOutParameter(16, java.sql.Types.VARCHAR);
			stat.execute();
			int res=stat.getInt(15);
			String mensaje=stat.getString(16);
			System.out.println(mensaje);
			stat.close();
			
			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			//TODO: handle exception
			System.out.println("PR_HISTORICAL_ATM_VOL::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
	
	
	public static boolean PR_HISTORICAL_SUBYACENTE(Connection con,
											   String nombre,
											   String fecha,
											   double precio) {
		try {
		
			CallableStatement stat=con.prepareCall("{call IEB_PRO.PR_HISTORICAL_SUBYACENTE(?,?,?,?,?)}");
			stat.setString(1, nombre);
			stat.setString(2, fecha);
			stat.setDouble(3, precio);
			stat.registerOutParameter(4, java.sql.Types.INTEGER);
			stat.registerOutParameter(5, java.sql.Types.VARCHAR);
			stat.execute();
			int res=stat.getInt(4);
			String mensaje=stat.getString(5);
			System.out.println(mensaje);
			stat.close();
		
			if(res==1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			//TODO: handle exception
			System.out.println("PR_HISTORICAL_SUBYACENTE::ERROR");
			System.out.println(e.toString());
			return false;
		}
	}
}
