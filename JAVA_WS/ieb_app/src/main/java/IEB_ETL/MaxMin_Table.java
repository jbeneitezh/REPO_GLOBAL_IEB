package IEB_ETL;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;

import DateAndTime.FechaSimple;
import IEB.IEB_Querys;
import Utiles.resultsetUseful;

public class MaxMin_Table {
	public static void generaMaxMin(Connection conexion) {
		int aplica=0;int noaplica=0;
		Statement sta;
		try {
			sta=conexion.createStatement();
	
			int []periodos=ETL_VARIABLES.PeriodosLargos();
			
			String truncar="TRUNCATE TABLE IEB_PRO.T_HISTO_MAXMIN_PERIOD";
			sta.executeUpdate(truncar);
			/*truncar="TRUNCATE TABLE IEB_PRO.T_HISTO_VOLA_PROM_0";
			sta.executeUpdate(truncar);
			truncar="TRUNCATE TABLE IEB_PRO.T_HISTO_VOLA_PROM_VAR";
			sta.executeUpdate(truncar);*/
			System.out.println("Truncada la tabla T_HISTO_MAXMIN_PERIOD");
			
			Vector<String>IdsFuturos=IEB_Querys.getIDActivos(sta, "FUTURE");
			for(int i=0;i<IdsFuturos.size();i++) {
				
				System.out.println("Procesando el subyacente "+IdsFuturos.get(i));
				
				String q1="SELECT a.ID_SUBYACENTE, a.FECHA, a.P_HIGH, a.P_LOW, a.P_CLOSE \n"
						 +"  FROM IEB_PRO.T_HISTORICOS_COMPLETA a \n"
						 +" WHERE a.ID_SUBYACENTE = "+IdsFuturos.get(i)+" \n"
						 +" ORDER BY a.ID_SUBYACENTE, a.FECHA";
				
				String [][]preciosStr=resultsetUseful.queryToMatrix(q1, conexion);
				//Matrix.printMatrix(preciosStr);
				
				for(int j=0;j<periodos.length;j++) {
					for(int k=periodos[j]+1;k<preciosStr.length;k++) {
						String fActual=FechaSimple.convierteFechaDatetime(preciosStr[k][1]); //fecha del registro a analizar
						//Obtenemos el n�mero de veces que se debe propagar hacia atras hasta llegar a la fecha
						boolean continuar=true;
						int delay=0;
						while(continuar) {
							delay++;
							String f2=FechaSimple.convierteFechaDatetime(preciosStr[k-delay][1]);
							double diasdif=FechaSimple.DifDias(f2, fActual, "yyyy-MM-dd 00:00:00");
							if(diasdif>periodos[j]) {
								delay--;
								continuar=false;
								f2=FechaSimple.convierteFechaDatetime(preciosStr[k-delay][1]);
								diasdif=FechaSimple.DifDias(f2, fActual, "yyyy-MM-dd 00:00:00");
								double an=FechaSimple.DifYears(f2, fActual, "yyyy-MM-dd 00:00:00");
								/*System.out.println(fActual+" - Per: "+periodos[j]+" - Diferencia: "+diasdif+" (a�os "+an+")"
										                  +" - fec ini: "+f2+" - fec fin: "+fActual);*/
							}
						}

						//Si se cumplen las condiciones se calcula el promedio y se inserta
						if((double)delay/(double)periodos[j]>=ETL_RESTRICTIONS.minPorcentajeDias()) {
							
							double pmaximo=Double.valueOf(preciosStr[k][2]);
							double pminimo=Double.valueOf(preciosStr[k][3]);
							double pcierre=Double.valueOf(preciosStr[k][4]);
							String fmaximo=FechaSimple.convierteFechaDatetime(preciosStr[k][1]);
							String fminimo=FechaSimple.convierteFechaDatetime(preciosStr[k][1]);
							double yearagomax=0;
							double yearagomin=0;
							
							for(int h=1;h<=delay;h++) {
								if(Double.valueOf(preciosStr[k-h][2])>pmaximo) {
									pmaximo=Double.valueOf(preciosStr[k-h][2]);
									String f2=FechaSimple.convierteFechaDatetime(preciosStr[k-h][1]);
									yearagomax=FechaSimple.DifYears(f2, fActual, "yyyy-MM-dd 00:00:00");
									fmaximo=FechaSimple.convierteFechaDatetime(preciosStr[k-h][1]);
								}
								if(Double.valueOf(preciosStr[k-h][3])<pminimo) {
									pminimo=Double.valueOf(preciosStr[k-h][3]);
									String f2=FechaSimple.convierteFechaDatetime(preciosStr[k-h][1]);
									yearagomin=FechaSimple.DifYears(f2, fActual, "yyyy-MM-dd 00:00:00");
									fminimo=FechaSimple.convierteFechaDatetime(preciosStr[k-h][1]);
								}
							}
							
							double rtoMax=Math.log(pmaximo/pcierre);
							double rtoMin=Math.log(pminimo/pcierre);
							/*System.out.println("Rto maxmimo: "+rtoMax+" - Rto minimo: "+rtoMin
									         +" anhos max: "+yearagomax+" - anhos min: "+yearagomin
									         +" fmax: "+fmaximo+" - fmin: "+fminimo);*/
							
							if(rtoMax<0.35 && rtoMin>-0.35) {
								//Insercion del contenido
								String insert="INSERT INTO IEB_PRO.T_HISTO_MAXMIN_PERIOD VALUES("+IdsFuturos.get(i)+", "
																							+"CAST('"+fActual+"' AS DATETIME), "
																							+periodos[j]+", "
																							+rtoMax+", "
																							+rtoMin+", "
																							+yearagomax+", "
																							+yearagomin+")";
								//System.out.println(insert);
								sta.executeUpdate(insert);
							}
							/*
							//insercion de la volatilidad com media 0
							insert="INSERT INTO IEB_PRO.T_HISTO_VOLA_PROM_0 VALUES("+IdsFuturos.get(i)+", "
																					+"CAST('"+f1+"' AS DATETIME), "
																				    +periodos[j]+", "
																				    +volaM0+")";
							//System.out.println(insert);
							sta.executeUpdate(insert);
							
							//insercion de la volatilidad com media movil
							insert="INSERT INTO IEB_PRO.T_HISTO_VOLA_PROM_VAR VALUES("+IdsFuturos.get(i)+", "
																					  +"CAST('"+f1+"' AS DATETIME), "
																				      +periodos[j]+", "
																				      +volaMMovil+")";
							//System.out.println(insert);
							sta.executeUpdate(insert);
							*/
							aplica++;
						}else {
							noaplica++;
						}
					}
				}
				
				System.out.println("Procesado el subyacente "+IdsFuturos.get(i));
				
			}
			System.out.println(String.valueOf(aplica+noaplica)+" registros total. "+aplica+" registros insertados");
			conexion.commit();
			System.out.println("commit");
			sta.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR::"+e.toString());
			try {
				conexion.rollback();
				System.out.println("rollback");
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
	}
}
