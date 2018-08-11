package Utiles;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.Vector;

import Logger.LoggerIA;




public class resultsetUseful {
	public resultsetUseful() {
		
	}
	
	public static void toCSV(String Ruta, String Nombre, ResultSet res) {
		String RutaOutput=Ruta+Nombre+".csv";
		try {
			
			FileWriter fw=new FileWriter(RutaOutput);
			BufferedWriter bw=new BufferedWriter(fw);
			
			try {
				
				String cabecera="";
				int k=0;
				//System.out.println("Aqui llega");
				//res.beforeFirst();
				//System.out.println("aqui no");
				while(res.next()) {
			    	if(k==0) {
			    		for (int i=1;i<=res.getMetaData().getColumnCount();i++) {
			    			//System.out.println("Columna "+i+" = "+res.getMetaData().getColumnName(i));
			    			cabecera+=res.getMetaData().getColumnName(i)+";";
			    		}
			    		//System.out.println("Cabecera: "+cabecera);
			    		bw.write(cabecera);
			    		bw.newLine();
			    		k++;
			    	}
					String linea="";
			    	for (int i=1;i<=res.getMetaData().getColumnCount();i++) {
			    		//System.out.println("Sacando el argumento "+i);
			    		String txt=res.getString(res.getMetaData().getColumnName(i));
			    		//System.out.println("Sacando el argumento "+i+" "+txt);
			    		if(txt==null) {
			    			//linea+=";";
			    			txt="";
			    		}/*else {
			    			linea+=txt+";";
			    		}*/
			    		linea+=txt+";";
			    	}
			    	if(k%100==0) {
			    		System.out.println("Linea "+k+" - "+linea);
			    	}
			    	bw.write(linea);
			    	bw.newLine();
			    	k++;
				}
				bw.close();
				fw.close();
			}catch(Exception e) {
				
				bw.close();
				fw.close();
				System.out.println("resultsetUseful::toCSV - No se pudo leer el resultset.");
				System.out.print(e.toString());;
			}
		}catch(Exception e) {	
			System.out.println("resultsetUseful::toCSV - No se pudo escribir en la ruta: "+RutaOutput);
			System.out.print(e.toString());;
			
		}
		
	}
	
	public static String [][] resultsetToMatrix(ResultSet res){
		
		String matres [][]=new String [1][1];
		matres[0][0]="";
		
		Vector<String[]>resprev=new Vector<String[]>();
		int k=0;
		int nCols=0;
		
		try {
			while (res.next()) {
				if(k==0) {
					nCols=res.getMetaData().getColumnCount();
					String arraux[]=new String [nCols];
					resprev.add(arraux);
					for (int i=0;i<resprev.get(k).length;i++) {
						resprev.get(k)[i]=res.getMetaData().getColumnName(i+1);
					}
					k++;
				}
				String arraux[]=new String [nCols];
				resprev.add(arraux);
				for (int i=0;i<resprev.get(k).length;i++) {
					resprev.get(k)[i]=res.getString(i+1);
				}
				k++;
			}
			matres=new String [resprev.size()][nCols];
			for (int i=0;i<resprev.size();i++) {
				for(int j=0;j<nCols;j++) {
					matres[i][j]=resprev.get(i)[j];
				}
			}
			return matres;
		}catch(Exception e) {
			System.out.println("resultsetUseful::toMatriz::ERROR - No se pudo obtener la matriz");
			System.out.println(e.toString());
			return matres;
		}	
	}
	public static String [][] queryToMatrix(String Query, Connection conexion) {
		String msg="";
		Statement sta=null;
		ResultSet res=null;
		String [][]matRes=null;
		LoggerIA log=new LoggerIA("resultsetUsefull");
		msg="queryToMatrix::lanzando query";
		log.WriteMessage(msg,false);
		try {
			
			log.WriteMessage(Query, false);
			sta=conexion.createStatement();
			res=sta.executeQuery(Query);
			matRes=resultsetToMatrix(res);

		} catch (Exception e) {
			// TODO: handle exception
			msg="queryToMatrix::ERROR::No se pudo obtener la query.";
			log.WriteMessage(msg, true);
			msg=e.toString();
			log.WriteMessage(msg, true);
		}finally {
			try {
				res.close();
				sta.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return matRes;
	}
}
