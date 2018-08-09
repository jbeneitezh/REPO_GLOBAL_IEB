package IEB;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import PCK_HTML.JSoupCon;

public class OptionValuationExtract {
	
	private String activoStr="";
	private String tipoOpcionStr="";
	private String strikeStr="";
	private String vtoStr="";
	private String spotStr="";
	private String tasaRStr="";
	private String volaStr="";
	private String priceStr="";
	private String deltaStr="";
	private String gammaStr="";
	private String vegaStr="";
	private String thetaStr="";
	
	private double strikeDbl=0;
	private double volaDbl=0;
	private double tasaRDbl=0;
	private double spotDbl=0;
	private double priceDbl=0;
	private double deltaDbl=0;
	private double gammaDbl=0;
	private double vegaDbl=0;
	private double thetaDbl=0;
	
	
	public OptionValuationExtract(String activo, String tipoOpcion, String strike, String vto) {
		activoStr=activo;
		tipoOpcionStr=tipoOpcion;
		strikeStr=strike;
		strikeDbl=Double.parseDouble(strikeStr);
		vtoStr=vto;
		
	}
	
	public OptionValuationExtract(String activo, String tipoOpcion, double strike, String vto) {
		activoStr=activo;
		tipoOpcionStr=tipoOpcion;
		strikeDbl=strike;
		strikeStr=String.valueOf(strikeDbl);
		vtoStr=vto;
		
	}
	
	public void extraeValoracion() {
		
		Document doc=docValorarOpcion(activoStr, tipoOpcionStr, strikeStr, vtoStr);
		docExtraeInfo(doc);
		
		
	}
	
	
	
	private Document docValorarOpcion(String activo, String tipoOpcion, String strike, String vto) {
		Document docRes=null;
		try {
			String link="https://ieb-simulador.com/cgi-bin/ver_precios.cgi?id_activo="
						+activo
						+"&tipo_opcion="+tipoOpcion
						+"&strike="+strike
						+"&fecha_vencimiento="+vto;
			docRes=JSoupCon.getLink(link);
			return docRes;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="OptionValuationExtract::docValorarOpcion::ERROR\n"+e.toString();
			System.out.println(msg);
			docRes=null;
			return docRes;
		}
		
		
	}
	
	private void docExtraeInfo(Document doc) {
		
		if (doc==null) {
			return;
		}
		
		try {
			//System.out.println(doc.toString());
			Elements body=doc.select("body");
			Elements tablas=body.select("table");
			
			//Extracción de datos de la primera tabla
			Elements filas1=tablas.get(1).select("tr");
			Element fila1=filas1.get(3);
			Elements elementos1=fila1.select("td");
			
			
			spotStr=elementos1.get(0).text().replace(".", "");
			tasaRStr=elementos1.get(1).text();
			volaStr=elementos1.get(2).text();
			spotDbl=Double.parseDouble(spotStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			tasaRDbl=Double.parseDouble(tasaRStr.replace("%", "").replace(",", ".").replace("-0.", "-."));
			volaDbl=Double.parseDouble(volaStr.replace("%", "").replace("-0.", "-."));
			
			/*
			System.out.println("El Spot es: "+spotStr+" - "+spotDbl);
			System.out.println("El interés es: "+tasaRStr+" - "+tasaRDbl);
			System.out.println("La volatilidad es: "+volaStr+" - "+volaDbl);
			*/
			
			//Extracción de datos de la segunda tabla
			Elements filas2=tablas.get(2).select("tr");
			Element fila2=filas2.get(1);
			Elements elementos2=fila2.select("td");	
			
			priceStr=elementos2.get(0).text();
			deltaStr=elementos2.get(1).text();
			gammaStr=elementos2.get(2).text();
			vegaStr =elementos2.get(3).text();
			thetaStr=elementos2.get(4).text();
			
			priceDbl=Double.parseDouble(priceStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			deltaDbl=Double.parseDouble(deltaStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			gammaDbl=Double.parseDouble(gammaStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			vegaDbl=Double.parseDouble(vegaStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			thetaDbl=Double.parseDouble(thetaStr.replace(".", "").replace(",", ".").replace("-0.", "-."));
			
			/*
			System.out.println("Precio es: "+priceStr+" - "+priceDbl);
			System.out.println("Delta es: "+deltaStr+" - "+deltaDbl);
			System.out.println("Gamma es: "+gammaStr+" - "+gammaDbl);
			System.out.println("Vega es: "+vegaStr+" - "+vegaDbl);
			System.out.println("Theta es: "+thetaStr+" - "+thetaDbl);
			*/
			
			
			/*
			for(int i=0;i<filas2.size();i++) {
				System.out.println("Tabla 2. Fila "+i+": "+filas2.get(i).text());
			}*/
			/*Element tabla2=doc.select("TABLE");
			Elements filas=tabla.select("TR");
			 Vector<String[]>filasStr=new Vector<>();
			 Element titulo = filas.get(1);
			 System.out.println(name+";"+titulo.text());*/
		} catch (Exception e) {
			// TODO: handle exception
			String msg="OptionValuationExtract::docExtraeInfo::ERROR\n"+e.toString();
			System.out.println(msg);
		}
		
		
	}
	
	
	

	public String getActivoStr() {
		return activoStr;
	}

	public String getTipoOpcionStr() {
		return tipoOpcionStr;
	}

	public String getStrikeStr() {
		return strikeStr;
	}

	public String getVtoStr() {
		return vtoStr;
	}

	public String getSpotStr() {
		return spotStr;
	}

	public String getTasaRStr() {
		return tasaRStr;
	}

	public String getVolaStr() {
		return volaStr;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public String getDeltaStr() {
		return deltaStr;
	}

	public String getGammaStr() {
		return gammaStr;
	}

	public String getVegaStr() {
		return vegaStr;
	}

	public String getThetaStr() {
		return thetaStr;
	}

	public double getStrikeDbl() {
		return strikeDbl;
	}

	public double getVolaDbl() {
		return volaDbl;
	}

	public double getTasaRDbl() {
		return tasaRDbl;
	}

	public double getSpotDbl() {
		return spotDbl;
	}

	public double getPriceDbl() {
		return priceDbl;
	}

	public double getDeltaDbl() {
		return deltaDbl;
	}

	public double getGammaDbl() {
		return gammaDbl;
	}

	public double getVegaDbl() {
		return vegaDbl;
	}

	public double getThetaDbl() {
		return thetaDbl;
	}
	
}
