package IEB;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import Utiles.resultsetUseful;

public class IEB_Querys {
	public static int countVtoRate(Statement sta, int IDsub, String fechaVto, String fechaActual) {
		
		try {
			String fecActOK=fechaActual.substring(0, 10);
			//System.out.println(fechaActual+" - "+fecActOK);
			String Query="SELECT COUNT(*) \n"
						+"  FROM IEB_PRO.T_HOUR_RATE a \n"
						+" WHERE a.ID_SUBYACENTE = "+IDsub+" \n"
						+"   AND a.FEC_ACTUAL = '"+fecActOK+"' \n"
						+"   AND a.FEC_VTO ='"+fechaVto+"'";
			//System.out.println(Query);
			ResultSet res=sta.executeQuery(Query);
			int contador=0;
			while(res.next()) {
				contador=res.getInt(1);
			}
			res.close();
			return contador;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::countVtoRate::ERROR::No se pudo realizar el conteo para el activo "
					  +IDsub+" - Vto: "+fechaVto+" - Actual: "+fechaActual+"\n"+e.toString();
			System.out.println(msg);
			return -1;
		}
		
		
	}
	public static double getVtoRate(Statement sta, int IDsub, String fechaVto, String fechaActual) {
		try {
			String fecActOK=fechaActual.substring(0, 10);
			//System.out.println(fechaActual+" - "+fecActOK);
			String Query="SELECT a.RATE \n"
						+"  FROM IEB_PRO.T_HOUR_RATE a \n"
						+" WHERE a.ID_SUBYACENTE = "+IDsub+" \n"
						+"   AND a.FEC_ACTUAL = '"+fecActOK+"' \n"
						+"   AND a.FEC_VTO ='"+fechaVto+"'";
			//System.out.println(Query);
			ResultSet res=sta.executeQuery(Query);
			double rate=0;
			while(res.next()) {
				rate=res.getDouble(1);
			}
			res.close();
			return rate;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::countVtoRate::ERROR::No se pudo realizar el conteo para el activo "
					  +IDsub+" - Vto: "+fechaVto+" - Actual: "+fechaActual+"\n"+e.toString();
			System.out.println(msg);
			return -100;
		}
	}
	public static boolean insertaVtoRate(Statement sta, int IDsub, String fechaVto, String fechaActual, Double rate) {
		try {
			String fecActOK=fechaActual.substring(0, 10);
			String Query="INSERT INTO IEB_PRO.T_HOUR_RATE (ID_SUBYACENTE, \n"
						+"									FEC_ACTUAL, \n"
						+"                                  FEC_VTO, \n"
						+"                                  RATE) \n"
						+"                   VALUES("+IDsub+", \n"
						+"							'"+fecActOK+"', \n"
						+"							'"+fechaVto+"', \n"
						+"							"+rate+")";
			//System.out.println(Query);
			sta.execute(Query);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::insertaVtoRate::ERROR::No se pudo realizar el conteo para el activo "
					  +IDsub+" - Vto: "+fechaVto+" - Actual: "+fechaActual+"\n"+e.toString();
			System.out.println(msg);
			return false;
		}
	}
	public static Vector<String> getIDActivos(Statement sta){
		Vector<String>res=new Vector<>();
		try {
			String Query="SELECT a.ID_SUBYACENTE "
						+"  FROM IEB_PRO.T_SUBYACENTES a"
						+" ORDER BY a.ID_SUBYACENTE";
			ResultSet result=sta.executeQuery(Query);
			while (result.next()) {
				int IDint=result.getInt(1);
				String IDStr=String.valueOf(IDint);
				res.addElement(IDStr);
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getIDActivos::ERROR::No se pudo realizar la extracción de los activos \n"+e.toString();
			System.out.println(msg);
			res=null;
			return res;
		}
	}
	
	public static String[][]getActualVolMatrix(Statement sta, String IDSubyacente){
		String res[][]=new String[0][0];
		try {
			String Query="SELECT a.ID_SUBYACENTE, b.NOMBRE, a.F_HISTO_VOL_M10, a.VENCIMIENTO, a.ATM, a.VOL80, a.VOL90, a.VOL100, a.VOL110, a.VOL120, a.TASA_R \n"
						+"  FROM T_SUBYACENTES b, T_VOLATILIDADES_M10 a \n"
						+" WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE"
						+"   AND a.ID_SUBYACENTE = "+IDSubyacente
						+"   AND a.F_HISTO_VOL_M10 = (SELECT MAX(c.F_HISTO_VOL_M10) FROM T_VOLATILIDADES_M10 c WHERE c.ID_SUBYACENTE = "+IDSubyacente+")";
			System.out.println(Query);
			ResultSet resultset=sta.executeQuery(Query);
			res=resultsetUseful.resultsetToMatrix(resultset);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getIDActivos::ERROR::No se pudo realizar la extracción de los activos \n"+e.toString();
			System.out.println(msg);
			res=null;
			return res;
		}
		
		
	}
}
