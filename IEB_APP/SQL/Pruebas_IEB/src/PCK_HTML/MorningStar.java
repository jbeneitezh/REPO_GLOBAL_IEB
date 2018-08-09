package PCK_HTML;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Constantes.FrontConstants;
import config.LogGlobal;


public class MorningStar {
	public MorningStar(){
	}
	private String Link="";
	private String Nombre="";
	private String ISIN="";
	private String FechaActualizacion="";
	private String DivisaVL="";
	private double VL=0;
	//private double VLPerc=0;

	private String patrimDiv="";
	private String patrimClaseDiv="";
	private double patrimVal=0;
	private double patrimClaseVal=0;
	private double gastosCorrientes=0;
	
	private String Fuente="MorningStar";
	private String TipoActivo="";
	
	
	
	public void obtenerDatosPrincipales(){
		Document doc=JSoupCon.getLink(Link);
		if (doc==null){
			return;
		}
		try {
			Element elem=doc.body();
			Element nombre=elem.getElementById("snapshotTitleDiv");
			Elements nombres=nombre.getElementsByTag("h1");
			Nombre=nombres.get(0).text();
			elem=elem.getElementById("overviewQuickstatsDiv");
			//elem=elem.toggleClass("snapshotTextColor snapshotTextFontStyle snapshotTable overviewKeyStatsTable");
			
			Elements valores = elem.getElementsByTag("td");
		    //Elements etiq = elem.getElementsByTag("tr");
		    //System.out.println("Etiquetas: "+etiq.size()+" Valores: "+valores.size());
			//for(int i=0;i<Math.min(valores.size(), etiq.size());i++){
			for(int i=0;i<valores.size();i++){
				//String val=valores.get(i).text();
				System.out.println("Etiqueta: "+valores.get(i).text());//+" - Valor: "+valores.get(i).text());
			}
			for(int i=0;i<valores.size();i++){
				String val=valores.get(i).text();
				if(val.startsWith("VL ")){
					String OtrosValores[]=valores.get(i+2).text().split(" ");
					FechaActualizacion=val.replace("VL ", "");
					DivisaVL=OtrosValores[0];
					VL=Double.parseDouble(OtrosValores[1].replace(",", "."));
				}else if (val.equals("ISIN")) {
					ISIN=valores.get(i+2).text();
				}else if (val.contains("Patrimonio Clase")) {
					String OtrosValores[]=valores.get(i+2).text().split(" ");
					if(OtrosValores.length>1){
						patrimClaseDiv=OtrosValores[0];
						patrimClaseVal=Double.parseDouble(OtrosValores[1].replace(",", "."));
					}	
				}else if (val.contains("Patrimonio ")) {
					String OtrosValores[]=valores.get(i+2).text().split(" ");
					if(OtrosValores.length>1){
						patrimDiv=OtrosValores[0];
						patrimVal=Double.parseDouble(OtrosValores[1].replace(",", "."));
					}
				}else if (val.contains("Gastos Corrientes")) {
					gastosCorrientes=Double.parseDouble(valores.get(i+2).text().replace(",", ".").replace("%", ""));
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			String msg="Error al descargar los datos para: "+Link+" - "+e.toString();
			LogGlobal.WriteError(msg);
			return;
		}
		System.out.println("Recuperada info para el link: "+Link);
		if(-1==Math.random()){
			imprimeContenido();
		}

	}
	private void imprimeContenido(){
		
		System.out.println("NOMBRE                  : "+Nombre);
		System.out.println("FECHA ACTUALIZACION VL  : "+FechaActualizacion);
		System.out.println("DIVISA VL               : "+DivisaVL);
		System.out.println("VL                      : "+VL);
		System.out.println("ISIN                    : "+ISIN);
		System.out.println("DIV PATR                : "+patrimDiv);
		System.out.println("DIV PATR CLASE          : "+patrimClaseDiv);
		System.out.println("PATR                    : "+patrimVal);
		System.out.println("PATR CLASE              : "+patrimClaseVal);
		System.out.println("GASTOS CORRIENTES       : "+gastosCorrientes);
	}
	public void setLink(String link) {
		Link = link;
	}
	public String getFuente() {
		return Fuente;
	}
	public void setFuente(String fuente) {
		Fuente = fuente;
	}
	public String getTipoActivo() {
		return TipoActivo;
	}
	public void setTipoActivo(String tipoActivo) {
		TipoActivo = tipoActivo;
	}
	
	public void Actualiza_T_ACTIVOS(Connection conexion){
		if (ISIN.equals("") || VL==0){
			LogGlobal.WriteError("MorningStar::Actualiza_T_ACTIVOS::Informacion no recuperada - "+Link);
			return;
		}
		try {
			String query="UPDATE "+FrontConstants.getVarEntorno()+".T_ACTIVOS SET \n"
							+"	ISIN = '"+ISIN+"', \n"
							+"	NOMBRE = '"+Nombre+"', \n"
							+"	F_INTENTO = SYSDATE(), \n"
							+"	F_DESCARGA = SYSDATE(), \n"
							+"	DESCARGADO = 'S', \n"
							+"	PRECIO = "+VL+", \n"
							+"	F_PRECIO =(SELECT STR_TO_DATE('"+FechaActualizacion+"', '%d/%m/%Y'))\n"
							+"WHERE LINK = '"+Link+"'";
			System.out.println("QUERY::  ");
			System.out.println(query);
			Statement sta=conexion.createStatement();
			sta.execute(query);
			sta.close();
			System.out.println("Finalizada Query Update");
			LogGlobal.WriteMessage("MorningStar::Actualiza_T_ACTIVOS::Actualizada información - "+Nombre);
			
			CallableStatement stat=conexion.prepareCall("{call "+FrontConstants.getVarEntorno()+".PR_UPD_MAX_HISTO(?,?,?,?)}");
			stat.setString(1, Link);
			stat.setDouble(2, VL);
			stat.setString(3, FechaActualizacion);
	        stat.registerOutParameter(4, java.sql.Types.INTEGER);
	        stat.execute();
	        int res=stat.getInt(4);
	        stat.close();
	        
	        if(res==1){
	        	LogGlobal.WriteMessage("MorningStar::Actualiza_T_ACTIVOS::Actualizado máximo histórico - "+VL);
	        }else if (res==0) {
	        	LogGlobal.WriteMessage("MorningStar::Actualiza_T_ACTIVOS::No es necesario actualizar el máximo histórico");
			}else{
				LogGlobal.WriteMessage("MorningStar::Actualiza_T_ACTIVOS::Fallo al actualizar el máximo histórico");
			}

		} catch (Exception e) {
			// TODO: handle exception
			LogGlobal.WriteError("MorningStar::Actualiza_T_ACTIVOS::ERROR::"+Link+"::"+e.toString());
		}
	}
}

