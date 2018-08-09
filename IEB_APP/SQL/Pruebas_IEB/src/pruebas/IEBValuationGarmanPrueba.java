package pruebas;

import DateAndTime.FechaSimple;
import IEB.IEB_OptionValuation;

public class IEBValuationGarmanPrueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double Spot=3440.12;
		double Strike=3430;
		double r=-0.0037;
		double y=0;
		double sigma=0.1438;
		String tipo="call";
		String fecIni="2018-07-06 00:00:00";
		String fecFin="2018-09-21 00:00:00";
		
		
		double tiempo=FechaSimple.DifAnhosBase365(fecIni, fecFin);
		System.out.println("Tiempo: "+tiempo);
		double valor=IEB_OptionValuation.PriceGarmanKohlhagen(tipo, Spot, Strike, r, y, tiempo, sigma);
		System.out.println("El resultado de la valoración es: "+valor);
		double delta=IEB_OptionValuation.DeltaGarmanKohlhagen(tipo, Spot, Strike, r, y, tiempo, sigma);
		System.out.println("El resultado de la delta es: "+delta);
		double gamma=IEB_OptionValuation.GammaGarmanKohlhagen(Spot, Strike, r, y, tiempo, sigma);
		System.out.println("El resultado de la gamma es: "+gamma);
		double vega=IEB_OptionValuation.VegaGarmanKohlhagen(Spot, Strike, r, y, tiempo, sigma);
		System.out.println("El resultado de la vega es: "+vega);
		
		
		
	}

}
