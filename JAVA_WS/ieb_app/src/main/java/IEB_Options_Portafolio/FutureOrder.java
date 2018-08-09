package IEB_Options_Portafolio;

import java.sql.Connection;

import IEB.IEB_MyProcedures;

public class FutureOrder {
	//private String mercado;      
	private String fechaOrden;   
	private String nombre;       
	private String compra_venta; 
	private int    titulos;      
	private int    titulosSigno; 
	private double precio;       
	private double tipoCambio;     
	private String vencimiento;
	private Connection con;
	
	public FutureOrder(Connection cone,String arr[]) {
		con=cone;
		//mercado=arr[0];      
		fechaOrden=arr[1];   
		nombre=arr[2];       
		compra_venta=arr[3];  
		titulos=Integer.parseInt(arr[4]); 
		if(compra_venta.equals("C")) {
			titulosSigno=titulos; 
		}else if (compra_venta.equals("V")) {
			titulosSigno=-titulos; 
		}
		 
		precio=Double.parseDouble(arr[5].replace(".", "").replace(",","."));        
		tipoCambio=Double.parseDouble(arr[6].replace(".", "").replace(",","."));      
		vencimiento=arr[7];   
		System.out.println("FutureOrder::Orden recuperada");
	}
	
	public void insertaOrden() {
		IEB_MyProcedures.PR_INSERTA_BOLETA_FUTURO(con,
												  fechaOrden, 
												  nombre, 
												  titulosSigno, 
												  compra_venta,
												  precio, 
												  tipoCambio,
												  vencimiento);
	}
	
}
