package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;

import FilesAndFolders.FicherosCLS;
import IEB_Options_Portafolio.FutureOrder;
import config.InfoIEB;
import config.InfoSQL;

public class IEBInsertaFuturosCartera {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			InfoSQL.iniciar();
			Connection conexion=DriverManager.getConnection(InfoSQL.BBDDAccess(), InfoSQL.BBDDUser(), InfoSQL.BBDDKey());
			//Statement stat=conexion.createStatement();
			String ruta=InfoIEB.rutaExport()+"\\boletas_fut_PruebaUnitaria.csv";
			String matBoletas[][]=FicherosCLS.readFileMatrixStringCodification(ruta, ";", "UTF8");
			for(int i=1;i<matBoletas.length;i++) {
				int n=matBoletas[i].length;
				String arr[]=new String[n];
				for(int j=0;j<n;j++) {
					arr[j]=matBoletas[i][j];
				}
				FutureOrder orden=new FutureOrder(conexion, arr);
				orden.insertaOrden();
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg="ERROR;;"+e.toString();
			System.out.println(msg);
		}
			
	}
}
