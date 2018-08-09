package IEB_History_Data;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import DateAndTime.FechaSimple;
import IEB.IEB_MyProcedures;

public class VolatilityRow {
	
	private Connection conexion;
	private String nombre="";
	private String fecha="";
	private Calendar fechaCal=new GregorianCalendar();
	private String fvto1="";
	private String fvto2="";
	private String fvto3="";
	private String fvto4="";
	private String fvto5="";
	private String fvto6="";
	private double vol1=-1;
	private double vol2=-1;
	private double vol3=-1;
	private double vol4=-1;
	private double vol5=-1;
	private double vol6=-1;
	
	public VolatilityRow(Connection con, String []arr) {
		// TODO Auto-generated constructor stub
		try {
			
			conexion=con;
			nombre=arr[0];
			fecha=arr[1];
			vol1=Double.parseDouble(arr[2].replace(".", "").replace(",", "."));
			vol2=Double.parseDouble(arr[3].replace(".", "").replace(",", "."));
			vol3=Double.parseDouble(arr[4].replace(".", "").replace(",", "."));
			vol4=Double.parseDouble(arr[5].replace(".", "").replace(",", "."));
			vol5=Double.parseDouble(arr[6].replace(".", "").replace(",", "."));
			vol6=Double.parseDouble(arr[7].replace(".", "").replace(",", "."));

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date fec=dateFormat.parse(fecha);
			fechaCal.setTime(fec);
			
			String vtos[]=FechaSimple.getFechasVtos(6, 
													fechaCal.get(Calendar.YEAR), 
													fechaCal.get(Calendar.MONTH)+1, 
													fechaCal.get(Calendar.DAY_OF_MONTH));

			fvto1=vtos[0];
			fvto2=vtos[1];
			fvto3=vtos[2];
			fvto4=vtos[3];
			fvto5=vtos[4];
			fvto6=vtos[5];
	
		} catch (Exception e) {
			// TODO: handle exception
			String msg="VolatilityRow::VolatilityRow::ERROR::No se pudo inicializar \n"+e.toString();
			System.out.println(msg);
		}
		
		
	}
	public void insertaFila() {
		try {
			if(registroValido()) {
				IEB_MyProcedures.PR_HISTORICAL_ATM_VOL(conexion, 
													   nombre, 
													   fecha, 
													   vol1, 
													   vol2, 
													   vol3, 
													   vol4, 
													   vol5, 
													   vol6, 
													   fvto1, 
													   fvto2, 
													   fvto3, 
													   fvto4, 
													   fvto5, 
													   fvto6);
			}else {
				String msg="VolatilityRow::insertaFila::Registro no válido";
				System.out.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			String msg="VolatilityRow::insertaFila::ERROR \n"+e.toString();
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
		if(isDate(fvto1)==false) {
			return false;
		}
		if(isDate(fvto2)==false) {
			return false;
		}
		if(isDate(fvto3)==false) {
			return false;
		}
		if(isDate(fvto4)==false) {
			return false;
		}
		if(isDate(fvto5)==false) {
			return false;
		}
		if(isDate(fvto6)==false) {
			return false;
		}
		
		if(vol1<0) {
			return false;
		}
		if(vol2<0) {
			return false;
		}
		if(vol3<0) {
			return false;
		}
		if(vol4<0) {
			return false;
		}
		if(vol5<0) {
			return false;
		}
		if(vol6<0) {
			return false;
		}
		
		return true;
	}
	
	/*
	private static boolean isNumeric(String cadena){
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe){
			try{
				Double.parseDouble(cadena);
				return true;
			}catch(Exception e){
				return false;
			}
		}
	}*/
	
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
