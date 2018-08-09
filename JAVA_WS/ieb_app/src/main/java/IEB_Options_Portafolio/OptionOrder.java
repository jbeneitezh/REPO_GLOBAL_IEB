package IEB_Options_Portafolio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import IEB.IEB_MyProcedures;

public class OptionOrder {
	//private String mercado;      
	private String fechaOrden;   
	private String nombre;       
	private String compra_venta; 
	private int    titulos;      
	private int    titulosSigno; 
	private double precio;       
	private double tipoCambio;   
	private double subyacente;   
	private String call_put;
	private double strike;
	private String vencimiento;
	private double volatilidad;
	private double tasaR;
	private Connection con;
	
	public OptionOrder(Connection cone,String arr[]) {
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
		subyacente=Double.parseDouble(arr[7].replace(".", "").replace(",","."));    
		call_put=arr[8];      
		strike=Double.parseDouble(arr[9].replace(".", "").replace(",","."));       
		vencimiento=arr[10];  
		volatilidad=Double.parseDouble(arr[11].replace(".", "").replace(",","."));   
		tasaR=Double.parseDouble(arr[12].replace(".", "").replace(",","."));   
		System.out.println("OptionOrder::Orden recuperada");
	}
	
	public void insertaOrden() {
		IEB_MyProcedures.PR_INSERTA_BOLETA_OPCION(con,
												  fechaOrden, 
												  nombre, 
												  titulosSigno, 
												  precio, 
												  tipoCambio, 
												  subyacente, 
												  call_put, 
												  strike, 
												  vencimiento, 
												  volatilidad, 
												  tasaR,
												  compra_venta);
	}
	
	
	
	
	public boolean existeOpcion() {
		try {
			Statement sta=con.createStatement();
			int res=0;
			String Query="SELECT COUNT(*) \n"
						+"  FROM IEB_PRO.T_OPCIONES a\n"
					    +" WHERE a.NOMBRE = '"+nombre+"' \n"
					    +"   AND a.TIPO   = '"+call_put+"' \n"
					    +"   AND a.VENCIMIENTO = STR_TO_DATE('"+vencimiento+"', '%d-%m-%Y') \n"
					    +"   AND a.STRIKE = "+strike;
			ResultSet resul=sta.executeQuery(Query);
			while (resul.next()) {
				res=resul.getInt(1);
			}
			if (res==0) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg="OptionOrder::existeOpcion::ERROR\n"+e.toString();
			System.out.println(msg);
			return false;
		}
	}
}
