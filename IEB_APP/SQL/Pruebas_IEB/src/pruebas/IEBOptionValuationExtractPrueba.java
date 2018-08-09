package pruebas;

import IEB.OptionValuationExtract;

public class IEBOptionValuationExtractPrueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OptionValuationExtract option=new OptionValuationExtract("30004", "put", "3430", "21-09-2018");
		option.extraeValoracion();
		System.out.println(option.getPriceDbl());
	}

}
