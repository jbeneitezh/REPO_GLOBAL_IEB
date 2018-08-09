package IEB_Mains;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import IEB.IEB_Querys;
import PCK_HTML.JSoupCon;
import config.InfoSQL;

public class EsInvestingDownload {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		descargaHistoricos();
	}
	
	
	public static void descargaHistoricos() {
		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			String [][]subyacentes=IEB_Querys.getEsInvestingSubyacentes(conexion);
			for(int i=1;i<subyacentes.length;i++) {
				String id=subyacentes[i][0];
				String link=subyacentes[i][1];
				descargaSubyacente(conexion,id,link);
			}
			
			conexion.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EsInvestingDownload::descargaHistoricos::ERROR \n"+e.toString());
		}
	}
	
	
	public static void descargaSubyacente(Connection con, String idsuby, String link) {
		
		
		
		System.out.println("Descargando el subyacente con ID: "+idsuby+" - Link: "+link);
		try {
			Document doc=JSoupCon.getLink(link);
			Element tabla=doc.getElementById("results_box");
			Elements filas=tabla.select("tr");
			for(int i=0;i<filas.size();i++) {
				System.out.println(i+"- "+filas.get(i).text());
				
				
				Elements columnas=filas.get(i).select("td");
				
				
				
				for(int j=0;j<columnas.size();j++) {
					System.out.println("J: "+j+" - "+columnas.get(j).text());
				}
			}
			//System.out.println(tabla.html());


		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EsInvestingDownload::descargaHistoricos::ERROR::No se pudo procesar id: "+idsuby+" - '"+link+"' \n"+e.toString());
		}
		
	}
	
	public static int cuentaRegistros(Connection con, String idsuby, String fecha) {
		
		/*try {
			String query="SELECT COUNT(*) FROM IEB_PRO.T_HISTORICOS_SUBYACENTES a \n"
						+" WHERE a.ID_SUBYACENTE = "+idsuby+" \n"
						+"   AND a.FECHA = STR_TO_DATE('"+fecha+"', '%d.%m.%Y')";
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		return 0;
	}
	
	
}
