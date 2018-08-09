package pruebas;

import DateAndTime.FechaSimple;
import IEB.IEB_OptionValuation;

public class IEBValuationGarmanPrueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double Spot=3440.92;
		double Strike=3450;
		double r=-0.0037;
		double y=0;
		double sigma=0.1413;
		String fecIni="2018-07-06 00:00:00";
		String fecFin="2018-09-21 00:00:00";
		
		
		double tiempo=FechaSimple.DifAnhosBase365(fecIni, fecFin);
		System.out.println("Tiempo: "+tiempo);
		double valor=IEB_OptionValuation.PriceGarmanKohlhagen("put", Spot, Strike, r, y, tiempo, sigma);
		System.out.println("El resultado de la valoración es: "+valor);
	}

}
