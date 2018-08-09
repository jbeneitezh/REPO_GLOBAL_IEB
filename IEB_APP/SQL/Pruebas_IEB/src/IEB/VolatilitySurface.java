package IEB;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DateAndTime.FechaSimple;
import FilesAndFolders.FicherosCLS;
import PCK_HTML.JSoupCon;
import Utiles.Matrix;
import config.InfoIEB;

public class VolatilitySurface implements Runnable {
	
	private String Nombre;
	private  Connection conexion;
	private  String [][] matrizDatos;
	//private  String tituloOpciones;
	private String Spot;
	private String fecha="";
	//private String []tipos;
	
	public VolatilitySurface(String name, Connection con) {
		Nombre=name;
		conexion=con;
		matrizDatos=new String [0][0];
		//tituloOpciones="";
		Spot="";
		fecha=FechaSimple.FechaSistemaCompletaString();
		
	}
	
	/*public static void main(String args[]) {
		
		
		for (int i=1;i<100;i++) {
			int j=30000+i;
			String val=String.valueOf(j);
			getSurface(val);
		}
	}*/
	
	public void run() {
		getSurface(Nombre);
		if(matrizDatos.length>2) {
			sendSubyacenteInfo();
			Statement sta=null;
			try {
				sta=conexion.createStatement();
				procesaMatriz(sta);
				sta.close();
			} catch (Exception e) {
				// TODO: handle exception
				String msg="VolatilitySurface::run()::ERROR::Error al tratar la matriz\n"+e.toString();
				System.out.println(msg);
				try {
					sta.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			
			
		}
	}
	
	private String rutaOutput(String code, String title) {
		String r=InfoIEB.rutaExport()+"\\"+code+"_"+title+"_"+fecha+".csv";
		return r;
	}
	
	
	private void sendSubyacenteInfo() {
		IEB_MyProcedures.PR_UPDATE_HISTORIC_OPTIONS(conexion, fecha, Nombre, Spot);
	}
	
	private void getSurface(String name) {
		
		
		String link="https://ieb-simulador.com/cgi-bin/ver_volatilidades.cgi?id_activo="+name;
		
		Document doc;
		
	
		 try {
			 doc=JSoupCon.getLink(link);
			 //String title = doc.title();
			 //String desc = doc.select("meta[name=description]").first().attr("content");
			 //String keyword = doc.select("meta[name=keywords]").first().attr("content");
			 //System.out.println("title : " + title);
			 Element tabla=doc.selectFirst("TABLE");
			 Elements filas=tabla.select("TR");
			 Vector<String[]>filasStr=new Vector<>();
			 Element titulo = filas.get(1);
			 System.out.println(name+";"+titulo.text());
			 //System.out.println(filas.size()+" filas.");
			 for (int i=0;i<filas.size();i++) {
				 Element fila=filas.get(i);
				 Elements columnas=fila.select("TD");
				 Elements atms=fila.select("TH");
				 String ATM="";
				 if(atms.size()>0) {
					 for (int j=0;j<atms.size();j++) {
						 //System.out.println("atm:    "+i+", "+j+" - "+atms.get(j).text());
						 if(atms.get(j).text().contains("ATM ")) {
							String s = atms.get(j).text();
							s=s.substring(s.indexOf("(") + 1);
							s = s.substring(0, s.indexOf(")"));
							ATM=s;
							//System.out.println("¿¿¿¿ATM????? "+ATM);
						 }
					 } 
				 } 
				
				 if (columnas.size()>0) {
					 //System.out.println("La fila "+(i+1)+" contiene "+columnas.size()+" columnas.");
					 String arr[]=new String [columnas.size()];
					 for (int j=0;j<columnas.size();j++) {
						 //System.out.println("col:    "+i+", "+j+" - "+columnas.get(j).text());
						 arr[j]=columnas.get(j).text();
						 if(j==0 && arr[j].contains("(")) {
							 String s =arr[j];
							 s=s.substring(s.indexOf("(") + 1);
							 s = s.substring(0, s.indexOf(")"));
							 arr[j]=s;
						 }
					 } 
					 if (arr.length==9 && (arr[0].equals("") || arr[0].equals(null)) && ATM.equals("")==false){
						 String [] arrAux=new String[arr.length+1];
						 for(int k=1;k<5;k++) {
							 arrAux[k]=arr[k];
						 }
						 arrAux[5]=ATM;
						 Spot=ATM;
						 for(int k=6;k<10;k++) {
							 arrAux[k]=arr[k-1];
						 }
						 arr=arrAux;
						 ATM="";
					 }
					 boolean anadir=false;
					 if (arr.length>0) {
						 for(int k=0;k<arr.length;k++) {
							 try {
								 if(arr[k].equals("")==false && arr[k].equals(null)==false && anadir==false) {
									 anadir=true;
								 }
							} catch (Exception e) {
								// TODO: handle exception
							} 
						 }
					 }
					 if (anadir==true) {
						 filasStr.add(arr);
					 }
				 }
			 }
			 
			 int maxCol=0;
			 for (int i=0;i<filasStr.size();i++) {
				 if(filasStr.get(i).length>maxCol) {
					 maxCol=filasStr.get(i).length;
				 }
			 }
			 String [][]matRes=new String[filasStr.size()][maxCol];
			 for(int i=0;i<filasStr.size();i++) {
				 for(int j=0;j<filasStr.get(i).length;j++) {
					 matRes[i][j]=filasStr.get(i)[j];
				 }
			 }
			 if(filasStr.size()>2) {
				 Matrix.printMatrix(matRes);
				 FicherosCLS.matrixToCSV(rutaOutput(name, titulo.text()), matRes);
				 FicherosCLS.writeLine(InfoIEB.rutaListaFicheros(), name+";"+titulo.text());
				 matrizDatos=matRes;
				 //tituloOpciones=titulo.text();
				 
				 
			 }
			 
			 //System.out.println("");
			 	
		 } catch (Exception e) {
			 e.printStackTrace();
		 	
		 }
        
        
	}
	
	private void procesaMatriz(Statement sta) {
		for (int i=1;i<matrizDatos.length;i++) {
			double tasaR=getTasasR(sta, Nombre, matrizDatos[i][0], fecha, Double.parseDouble(Spot));
			if(tasaR!=-100) {
				
				double vol80=Double.parseDouble(matrizDatos[i][1].replace("%", ""));
				double vol90=Double.parseDouble(matrizDatos[i][2].replace("%", ""));
				double vol100=Double.parseDouble(matrizDatos[i][3].replace("%", ""));
				double vol110=Double.parseDouble(matrizDatos[i][4].replace("%", ""));
				double vol120=Double.parseDouble(matrizDatos[i][5].replace("%", ""));
				
				IEB_MyProcedures.PR_UPDATE_VOLAT_HISTO(conexion, 
													   Integer.parseInt(Nombre), 
													   fecha, 
													   matrizDatos[i][0], 
													   Double.parseDouble(Spot), 
													   vol80, 
													   vol90, 
													   vol100, 
													   vol110, 
													   vol120, 
													   tasaR);
			}
		}
	}
	
	private double getTasasR(Statement sta, String IDSuby, String fecVto, String fecActual, double spot) {
		try {
			double res=0;
			int cuenta=IEB_Querys.countVtoRate(sta, Integer.parseInt(IDSuby), fecVto, fecActual);
			if(cuenta==0) {
				OptionValuationExtract opcion=new OptionValuationExtract(IDSuby, "call", spot, fecVto);
				opcion.extraeValoracion();
				res=opcion.getTasaRDbl();
				IEB_Querys.insertaVtoRate(sta, Integer.parseInt(IDSuby), fecVto, fecActual, res);
				return res;
			}else{
				res=IEB_Querys.getVtoRate(sta, Integer.parseInt(IDSuby), fecVto, fecActual);
				return res;
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg="VolatilitySurface::getTasaR::ERROR::Suby: "+IDSuby
					  +" - vto: "+fecVto+" - actual: "+fecActual+" - spot: "+spot+"\n"+e.toString();
			System.out.println(msg);
			return -100;
		}
		
	}
	/*
	for(int i=1;i<matrizDatos.length;i++) {
		int cuenta=IEB_Querys.countVtoRate(sta, Integer.parseInt(Nombre), matrizDatos[i][0], fecha);
		System.out.println("La cuenta sale: "+cuenta);
		if (cuenta==0) {
			OptionValuationExtract opcion=new OptionValuationExtract(Nombre, "call", Spot, matrizDatos[i][0]);
			opcion.extraeValoracion();
			double rate=opcion.getTasaRDbl();
			IEB_Querys.insertaVtoRate(sta, Integer.parseInt(Nombre), matrizDatos[i][0], fecha, rate);
		}
	}
	*/
}
