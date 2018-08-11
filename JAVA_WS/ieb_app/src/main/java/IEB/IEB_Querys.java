package IEB;

import java.sql.Connection;
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
	public static Vector<String> getIDActivos(Statement sta, String Tipo){
		Vector<String>res=new Vector<String>();
		try {
			String Query="SELECT a.ID_SUBYACENTE "
						+"  FROM IEB_PRO.T_SUBYACENTES a"
						+" WHERE a.TIPO_SUBYACENTE = '"+Tipo+"' "
						+" ORDER BY a.ID_SUBYACENTE";
			ResultSet result=sta.executeQuery(Query);
			while (result.next()) {
				int IDint=result.getInt(1);
				String IDStr=String.valueOf(IDint);
				res.addElement(IDStr);
			}
			result.close();
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getIDActivos::ERROR::No se pudo realizar la extracción de los activos \n"+e.toString();
			System.out.println(msg);
			res=null;
			return res;
		}
	}
	public static Vector<String> getIDActivosUso(Statement sta, String Tipo){
		Vector<String>res=new Vector<String>();
		try {
			String Query="SELECT ";
			if(Tipo.toUpperCase().equals("FUTURE")) {
				Query+="a.ID_FUTURO \n";
			}else if(Tipo.toUpperCase().equals("OPTION")) {
				Query+="a.ID_OPCION \n";
			}
			Query+="  FROM IEB_PRO.T_RELA_OPT_FUT a, \n"
				  +"       IEB_PRO.T_ACTIVOS_USO b \n"
				  +" WHERE a.ID_FUTURO = b.ID_SUBYACENTE \n";
		    if(Tipo.toUpperCase().equals("FUTURE")) {
				Query+=" ORDER BY a.ID_FUTURO";
			}else if(Tipo.toUpperCase().equals("OPTION")) {
				Query+=" ORDER BY a.ID_OPCION";
			}

			ResultSet result=sta.executeQuery(Query);
			while (result.next()) {
				int IDint=result.getInt(1);
				String IDStr=String.valueOf(IDint);
				res.addElement(IDStr);
			}
			result.close();
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
			resultset.close();

			return res;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getIDActivos::ERROR::No se pudo realizar la extracción de los activos \n"+e.toString();
			System.out.println(msg);
			res=null;
			return res;
		}
	}
	
	public static String getEsInvestingSubyacenteNombre(Connection con, String nombre) {
		
		try {
			Statement sta=con.createStatement();
			String Query="SELECT a.NOMBRE_BBDD \n"
						+"  FROM IEB_PRO.T_ES_INVESTING a \n"
						+" WHERE a.NOMBRE_ES_INV = '"+nombre+"'";
			ResultSet resul=sta.executeQuery(Query);
			String nombreResultado="";
			while(resul.next()) {
				nombreResultado=resul.getString(1);
			}
			resul.close();
			sta.close();
			return nombreResultado;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getEsInvestingSubyacenteNombre::ERROR::No se pudo realizar la extracción del nombre. \n"+e.toString();
			System.out.println(msg);
			return "";
		}
	}
	public static String [][]getEsInvestingSubyacentes(Connection con){
		String [][]matRes=null;
		try {
			Statement sta=con.createStatement();
			String Query="SELECT a.ID_SUBYACENTE, \n"
						+"       a.LINK_ES_INV"
						+"  FROM IEB_PRO.T_ES_INVESTING a \n";
			ResultSet resul=sta.executeQuery(Query);
			matRes=resultsetUseful.resultsetToMatrix(resul);
			resul.close();
			sta.close();
			return matRes;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getEsInvestingSubyacentes::ERROR::No se pudo realizar la extracción. \n"+e.toString();
			System.out.println(msg);
			return null;
		}
	}
	
	public static String [][]getAlphaVantageTickers(Connection con){
		String [][]matRes=null;
		try {
			Statement sta=con.createStatement();
			String Query="SELECT a.ID_SUBYACENTE, \n"
						+"       a.TICKER"
						+"  FROM IEB_PRO.T_ALPHAVANTAGE a \n";
			ResultSet resul=sta.executeQuery(Query);
			matRes=resultsetUseful.resultsetToMatrix(resul);
			resul.close();
			sta.close();
			return matRes;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getEsInvestingSubyacentes::ERROR::No se pudo realizar la extracción. \n"+e.toString();
			System.out.println(msg);
			return null;
		}
	}
	
	
	
	public static String getExcelVolatilidadesQuery() {
		String Query = "SELECT b.NOMBRE, b.MULTIPLICADOR, a.* \n"
	            	 +"  FROM T_VOLATILIDADES_D01 a, T_SUBYACENTES b \n"
	            	 +" WHERE a.F_HISTO_VOL_D01 = (SELECT MAX(d.F_HISTO_VOL_D01) FROM T_VOLATILIDADES_D01 d) \n"
	            	 +"   AND a.ID_SUBYACENTE = b.ID_SUBYACENTE \n"
	            	 +"   AND a.VENCIMIENTO >= NOW()"
	            	 +" ORDER BY b.NOMBRE, a.VENCIMIENTO ASC";
		return Query;
	}
	public static String getQueryVolatilidadSubyacente(String subyacente) {
		String Query = "SELECT b.NOMBRE, b.MULTIPLICADOR, a.* \n"
           	 +"  FROM T_VOLATILIDADES_D01 a, T_SUBYACENTES b \n"
           	 +" WHERE a.F_HISTO_VOL_D01 = (SELECT MAX(d.F_HISTO_VOL_D01) FROM T_VOLATILIDADES_D01 d) \n"
           	 +"   AND a.ID_SUBYACENTE = b.ID_SUBYACENTE \n"
           	 +"   AND a.ID_SUBYACENTE = "+subyacente+"\n "
           	 +" ORDER BY b.NOMBRE, a.VENCIMIENTO ASC";
		return Query;
	}
	public static Vector<String> getSubyacentesUso(Connection con){
		Vector<String>res=new Vector<String>();
		try {
			Statement sta=con.createStatement();
			String Query="SELECT a.ID_SUBYACENTE \n"
						+"  FROM IEB_PRO.T_ACTIVOS_USO a, \n"
						+"       IEB_PRO.T_SUBYACENTES b \n"
						+" WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE \n"
						+" ORDER BY b.NOMBRE";
			ResultSet resul=sta.executeQuery(Query);
			while(resul.next()) {
				res.add(resul.getString(1));
			}
			resul.close();
			sta.close();
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_Querys::getSubyacentesUso::ERROR::No se pudo realizar la extraccion. \n"+e.toString();
			System.out.println(msg);
			return null;
		}
	}
	
	
	
	
	public static String QueryPerimetroHistoricoSubyacente(Vector<String>subyacentes) {
		
		String Select="SELECT DISTINCT(a.FECHA), \n";
		String From  ="  FROM IEB_PRO.T_HISTORICOS_SUBYACENTES a \n"
					 +" WHERE a.FECHA IN (";
		String FecSelect="";
	    for(int i=0;i<subyacentes.size()-1;i++) {
	    	Select  +="       (SELECT t"+i+".P_CLOSE FROM IEB_PRO.T_HISTORICOS_SUBYACENTES t"+i
	    			+" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	    			+" AND t"+i+".FECHA = a.FECHA) AS SUBY_"+subyacentes.get(i)+", \n";
	    	FecSelect+="SELECT t"+i+".FECHA FROM IEB_PRO.T_HISTORICOS_SUBYACENTES t"+i+" \n"
	                    +" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	                    +"   AND t"+i+".FECHA IN (\n";
	    }
	    
	    Select  +="       (SELECT t"+(subyacentes.size()-1)+".P_CLOSE FROM IEB_PRO.T_HISTORICOS_SUBYACENTES t"+(subyacentes.size()-1)
    			+" WHERE t"+(subyacentes.size()-1)+".ID_SUBYACENTE = "+subyacentes.get((subyacentes.size()-1))
    			+" AND t"+(subyacentes.size()-1)+".FECHA = a.FECHA) AS SUB_"+subyacentes.get((subyacentes.size()-1))+" \n";
	    
	    FecSelect+="SELECT t"+(subyacentes.size()-1)+".FECHA FROM IEB_PRO.T_HISTORICOS_SUBYACENTES t"+(subyacentes.size()-1)+" \n"
	                    +" WHERE t"+(subyacentes.size()-1)+".ID_SUBYACENTE = "+subyacentes.get(subyacentes.size()-1);
	
	    for(int i=0;i<subyacentes.size();i++) {
	    	FecSelect+=")";
	    }
	    String Query=Select+From+FecSelect;
	    return Query;

	}

	public static String QueryPerimetroHistoricoSubyacente_v2(Vector<String>subyacentes) {
		
		String Select="SELECT DISTINCT(a.FECHA), \n"
					 +"(SELECT t0.P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA t0 WHERE t0.ID_SUBYACENTE = "+subyacentes.get(0)
		    			+" AND t0.FECHA = a.FECHA) AS SUB_"+subyacentes.get(0);
		String From  ="  FROM IEB_PRO.T_HISTORICOS_COMPLETA a \n"
					 +" WHERE a.FECHA = (SELECT t0.FECHA FROM T_HISTORICOS_COMPLETA t0 WHERE t0.ID_SUBYACENTE = "+subyacentes.get(0)
					 +" AND t0.FECHA = a.FECHA) \n";

	    for(int i=1;i<subyacentes.size();i++) {
	    	Select  +=", \n (SELECT t"+i+".P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA t"+i+" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	    			+" AND t"+i+".FECHA = a.FECHA) AS SUB_"+subyacentes.get(i);
	    	From    +="   AND a.FECHA = (SELECT t"+i+".FECHA FROM T_HISTORICOS_COMPLETA t"+i+" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	    			+" AND t"+i+".FECHA = a.FECHA) \n";
	    }
	    Select+=" \n";
	    String Order=" ORDER BY a.FECHA \n";
	    
	    String Query=Select+From+Order;
	    return Query;

	}


	public static String QueryPerimetroHistoricoVolatilidad(Vector<String>opciones) {

	    String txtAux="";
	    for(int i=0;i<opciones.size()-1;i++) {
	          txtAux+="SELECT t"+i+".FECHA FROM IEB_PRO.T_HISTORICOS_VOLATILIDADES t"+i+" \n"
	                    +" WHERE t"+i+".ID_SUBYACENTE = "+opciones.get(i)
	                    +"   AND t"+i+".FECHA IN (\n";
	    }
	
	    txtAux+="SELECT t"+(opciones.size()-1)+".FECHA FROM IEB_PRO.T_HISTORICOS_VOLATILIDADES t"+(opciones.size()-1)+" \n"
	                    +" WHERE t"+(opciones.size()-1)+".ID_SUBYACENTE = "+opciones.get(opciones.size()-1);
	
	    for(int i=0;i<opciones.size()-1;i++) {
	          txtAux+=")";
	    }
	    return txtAux;
	
	 }
	
	
	public static String Query_Historicos_Precios_Volatilidades(Vector<String>subyacentes) {
		
		String Select="SELECT DISTINCT(a.FECHA), \n"
					 +"(SELECT t0.P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA t0 WHERE t0.ID_SUBYACENTE = "+subyacentes.get(0)
		    			+" AND t0.FECHA = a.FECHA) AS SUB_"+subyacentes.get(0);
		String From  ="  FROM IEB_PRO.T_HISTORICOS_COMPLETA a \n"
					 +" WHERE a.FECHA = (SELECT t0.FECHA FROM T_HISTORICOS_COMPLETA t0 WHERE t0.ID_SUBYACENTE = "+subyacentes.get(0)
					 +" AND t0.FECHA = a.FECHA) \n";

	    for(int i=1;i<subyacentes.size();i++) {
	    	Select  +=", \n (SELECT t"+i+".P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA t"+i+" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	    			+" AND t"+i+".FECHA = a.FECHA) AS SUB_"+subyacentes.get(i);
	    	From    +="   AND a.FECHA = (SELECT t"+i+".FECHA FROM T_HISTORICOS_COMPLETA t"+i+" WHERE t"+i+".ID_SUBYACENTE = "+subyacentes.get(i)
	    			+" AND t"+i+".FECHA = a.FECHA) \n";
	    }
	    Select+=" \n";
	    String Order=" ORDER BY a.FECHA \n";
	    
	    String Query=Select+From+Order;
	    return Query;

	}
}
