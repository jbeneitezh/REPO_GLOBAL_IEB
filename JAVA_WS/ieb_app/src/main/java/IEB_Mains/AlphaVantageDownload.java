package IEB_Mains;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import IEB.IEB_Querys;
import config.InfoSQL;

public class AlphaVantageDownload {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		descargaHistoricos();
	}
	
	
	public static void descargaHistoricos() {
		
		try {
			
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			String [][]subyacentes=IEB_Querys.getAlphaVantageTickers(conexion);
			
			for(int i=1;i<subyacentes.length;i++) {
				String id=subyacentes[i][0];
				String ticker=subyacentes[i][1];
				descargaSubyacente(conexion,id,ticker, true);
				System.out.println("Subyacente finalizado - "+id+" - "+ticker);
				Thread.sleep(1000);
			}
			eliminaIncorrectos(conexion);
			conexion.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AlphaVantageDownload::descargaHistoricos::ERROR \n"+e.toString());
		}
	}
	
	
	public static void descargaSubyacente(Connection con, String idsuby, String ticker, boolean outputFull) {
		
		System.out.println("Descargando el subyacente con ID: "+idsuby+" - Link: "+ticker);
		try {
			
			String link="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol="+ticker;
			if(outputFull==true) {
				link+="&outputsize=full";
			}
			link+="&apikey=CTCRVLX684HQUUDK&datatype=csv";
			
			//Document doc=JSoupCon.getLink(link);
			
			URL url = new URL(link);

			URLConnection hc = url.openConnection();

			InputStreamReader inputStream = new InputStreamReader(hc.getInputStream(), "UTF-8");
		    BufferedReader bufferedReader = new BufferedReader(inputStream);
		    String line;
		    int i=-1;
		    while ((line = bufferedReader.readLine()) != null) {
			   	i++;
			    System.out.println(i+"- -"+line);
			    try {
			    	
					String []vaux=line.split(",");
					/*for(int j=0;j<vaux.length;j++) {
						System.out.println(j+" - "+vaux[j]);
					}*/
					
					String fecha=vaux[0];
					String open=vaux[1];
					String high=vaux[2];
					String low=vaux[3];
					String close=vaux[4];
					String adj_close=vaux[5];
					String volume=vaux[6];
					String dividend=vaux[7];
					String split_coef=vaux[8];
					
					int cuenta=cuentaRegistros(con, idsuby, fecha);
					
					if(cuenta==0){
						
						insertaFila(con, idsuby, fecha, open, high, low, close, adj_close, volume, dividend, split_coef);
						
					}else {
						System.out.println("Suby: "+idsuby+" - fecha: "+fecha+" - cuenta: "+cuenta);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("AlphaVantageDownload::descargaSubyacente::"+ticker+" - Error linea "+i);
				}
		    }
		    bufferedReader.close();
		    
		    

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AlphaVantageDownload::descargaHistoricos::ERROR::No se pudo procesar id: "+idsuby+" - '"+ticker+"' \n"+e.toString());
		}
		
	}
	
	private static int cuentaRegistros(Connection con, String idsuby, String fecha) {
		
		try {
			String query="SELECT COUNT(*) FROM IEB_PRO.T_HISTORICOS_COMPLETA a \n"
						+" WHERE a.ID_SUBYACENTE = "+idsuby+" \n"
						+"   AND a.FECHA = STR_TO_DATE('"+fecha+"', '%Y-%m-%d')";
			Statement sta=con.createStatement();
			ResultSet res=sta.executeQuery(query);
			int cuenta=-1;
			while(res.next()) {
				cuenta=res.getInt(1);
			}
			return cuenta;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AlphaVantageDownload::cuentaRegistros::ERROR::No se pudo contar id: "+idsuby+" - '"+fecha+"' \n"+e.toString());
			return -1;
		}
	}
	
	
	
	private static void insertaFila(Connection con, 
									String idsuby, 
									String fecha,
									String open,
									String high,
									String low,
									String close,
									String adj_close,
									String volume,
									String dividend,
									String split_coef) {
		
		try {
			Statement sta=con.createStatement();
			String Query="INSERT INTO IEB_PRO.T_HISTORICOS_COMPLETA VALUES("+idsuby+", \n"
						+"                                                 STR_TO_DATE('"+fecha+"', '%Y-%m-%d'), \n"
					    +"                                                "+open+", \n"
					    +"                                                "+high+", \n"
					    +"                                                "+low+", \n"
					    +"                                                "+close+", \n"
					    +"                                                "+adj_close+", \n"
					    +"                                                "+volume+", \n"
					    +"                                                "+dividend+", \n"
					    +"                                                "+split_coef+")";
			System.out.println(Query);
			sta.executeUpdate(Query);
			sta.close();
			System.out.println("Insertado "+idsuby+" - "+fecha);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AlphaVantageDownload::insertaFila::ERROR::No se pudo insertar id: "+idsuby+" - '"+fecha+"' \n"+e.toString());
		}
		
	}
	
	private static void eliminaIncorrectos(Connection con) {
		try {
			Statement sta=con.createStatement();
			String Query="DELETE FROM IEB_PRO.T_HISTORICOS_COMPLETA \n"
						+"      WHERE P_ADJ_CLOSE <= 0";
			System.out.println(Query);
			sta.executeUpdate(Query);
			sta.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AlphaVantageDownload::eliminaIncorrectos::ERROR::No se pudo realizar la limpieza de 0 en precios \n"+e.toString());
		}
		
		
	}
}
