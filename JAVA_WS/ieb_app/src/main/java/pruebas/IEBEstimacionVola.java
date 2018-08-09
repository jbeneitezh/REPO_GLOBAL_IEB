package pruebas;

import FilesAndFolders.FicherosCLS;
import IEB.OptionValuationExtract;
import Utiles.Matrix;
import config.InfoIEB;

public class IEBEstimacionVola {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String IDActivo="30004";
		String tipo="call";
		String vto="21-09-2018";
		String vto2="21-12-2018";
		double Spot=3440.92;
		
		String matRes[][]=new String[21][2];
		String matRes2[][]=new String [21][7];
		
		for (int i=0;i<matRes.length;i++) {
			double strike=Spot*(90+i)/100;
			OptionValuationExtract opcion=new OptionValuationExtract(IDActivo, tipo, strike, vto);
			opcion.extraeValoracion();
			matRes[i][0]=String.valueOf(strike).replace(".", ",");
			matRes[i][1]=String.valueOf(opcion.getVolaDbl()).replace(".", ",");
			
			OptionValuationExtract opcion2=new OptionValuationExtract(IDActivo, tipo, strike, vto2);
			opcion2.extraeValoracion();
			matRes2[i][0]=String.valueOf(strike).replace(".", ",");
			matRes2[i][1]=vto;
			matRes2[i][2]=String.valueOf(opcion.getVolaDbl()).replace(".", ",");
			matRes2[i][3]=String.valueOf(opcion.getPriceDbl()).replace(".", ",");
			matRes2[i][4]=vto2;
			matRes2[i][5]=String.valueOf(opcion2.getVolaDbl()).replace(".", ",");
			matRes2[i][6]=String.valueOf(opcion2.getPriceDbl()).replace(".", ",");
			System.out.println(i);
		}
		Matrix.printMatrix(matRes);
		String ruta=InfoIEB.rutaExport()+"\\EstimacionVolatilidad.csv";
		FicherosCLS.matrixToCSV(ruta, matRes);
		
		Matrix.printMatrix(matRes2);
		String ruta2=InfoIEB.rutaExport()+"\\EstimacionVolatilidad2.csv";
		FicherosCLS.matrixToCSV(ruta2, matRes2);
		
	}

}
