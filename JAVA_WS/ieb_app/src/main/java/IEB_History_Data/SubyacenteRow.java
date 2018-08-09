package IEB_History_Data;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import IEB.IEB_MyProcedures;

public class SubyacenteRow {
	private Connection conexion;
	private String nombre="";
	private String fecha="";
	private Calendar fechaCal=new GregorianCalendar();
	private double precio=-1;
	
	public SubyacenteRow(Connection con, String []arr) {
		// TODO Auto-generated constructor stub
		try {
			
			conexion=con;
			nombre=arr[0];
			fecha=arr[1].replace(".", "-").replace("\"", "");
			precio=Double.parseDouble(arr[2].replace(".", "").replace(",", ".").replace("\"", ""));

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date fec=dateFormat.parse(fecha);
			fechaCal.setTime(fec);
			
		} catch (Exception e) {
			// TODO: handle exception
			String msg="SubyacenteRow::SubyacenteRow::ERROR::No se pudo inicializar \n"+e.toString();
			System.out.println(msg);
		}
		
		
	}
	public SubyacenteRow(Connection con, String name, String []arr) {
		// TODO Auto-generated constructor stub
		try {
			
			conexion=con;
			nombre=name;
			fecha=arr[0].replace(".", "-").replace("\"", "");
			precio=Double.parseDouble(arr[1].replace(".", "").replace(",", ".").replace("\"", ""));

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date fec=dateFormat.parse(fecha);
			fechaCal.setTime(fec);
			
		} catch (Exception e) {
			// TODO: handle exception
			String msg="SubyacenteRow::SubyacenteRow::ERROR::No se pudo inicializar \n"+e.toString();
			System.out.println(msg);
		}
		
		
	}
	public void insertaFila() {
		try {
			if(registroValido()) {
				IEB_MyProcedures.PR_HISTORICAL_SUBYACENTE(conexion, 
														  nombre, 
														  fecha, 
														  precio);
			}else {
				String msg="SubyacenteRow::insertaFila::Registro no válido";
				System.out.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg="SubyacenteRow::insertaFila::ERROR \n"+e.toString();
			System.out.println(msg);
		}
		
	}
	
	
	private boolean registroValido() {
		if(nombre.equals("")) {
			return false;
		}
		if(isDate(fecha)==false) {
			return false;
		}	
		if(precio<0) {
			return false;
		}
		
		return true;
	}
	
	private static boolean isDate(String cadena) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			/*Date fecha=*/dateFormat.parse(cadena);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
