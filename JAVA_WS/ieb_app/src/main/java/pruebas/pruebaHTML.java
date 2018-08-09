package pruebas;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class pruebaHTML {
	
	private static int iPosicion=0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc = Jsoup.connect("https://es.investing.com/equities/abertis").get();
					  /*.data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();*/
			System.out.println(doc.title());
			Element elem=doc.body();

			int n=elem.elementSiblingIndex();
			System.out.println("El cuerpo tiene "+n+" elementos.");
			/*for(int i=0;i<elem.childNodeSize();i++){
				Element elem2=elem.child(i);
				muestraYObtenHijos(elem2, "");
				int j=elem.elementSiblingIndex();
			}*/
			Elements elems=elem.children();
			for(int i=0;i<elems.size();i++){
				Element e3=elems.get(i);
				muestraYObtenHijos(e3, "");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
	}
	
	public static void muestraYObtenHijos(Element elem,String espacios){
		iPosicion++;
		
		System.out.println(espacios+iPosicion+" class: '"+elem.className()+"' - id: '"+elem.id()+"'");
		Elements elements=elem.children();
		if(elements==null || elements.size()==0){
			System.out.println(espacios+"    valor: '"+obtenAtributo(elem.toString())+"'");
			System.out.println(espacios+"    valor: '"+elem.toString()+"'");
			
		}
		String txtString=elem.toString();
		for(int i=0;i<elements.size();i++){
			Element elem2=elements.get(i);
			muestraYObtenHijos(elem2, espacios+"    ");
		}
	}
	
	private static String obtenAtributo(String elemento){
		String res="";
		int valor=elemento.indexOf(">");
		System.out.println("valor: "+valor);
		int valor2=elemento.indexOf("<",valor+1);
		System.out.println("valor2: "+valor2);
		if(valor>=0 && valor2>0){
			res=elemento.substring(valor+1, valor2);
		}
		return res;
	}

}
