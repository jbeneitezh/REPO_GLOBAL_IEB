package DateAndTime;
import java.text.SimpleDateFormat;
import java.util.*;
public class FechaSimple {
		public static String FechaString(Calendar fecha){
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
			String Res="";
			Res=String.valueOf(anho);
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			return Res;
		}
		public static String FechaCompletaString(Calendar fecha){
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        int hora=fecha.get(Calendar.HOUR_OF_DAY);
	        int min=fecha.get(Calendar.MINUTE);
	        
			String Res="";
			Res=String.valueOf(anho);
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			if (hora<10){
				Res+="0"+String.valueOf(hora);
			}else{
				Res+=String.valueOf(hora);
			}
			if (min<10){
				Res+="0"+String.valueOf(min);
			}else{
				Res+=String.valueOf(min);
			}
			return Res;
		}
		public static String FechaSistemaString(){
			Calendar fecha = new GregorianCalendar();
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
			String Res="";
			Res=String.valueOf(anho);
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			return Res;
		}
		
		public static String FechaSistemaCompletaTXT() {
			Calendar fecha = new GregorianCalendar();
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        int hora=fecha.get(Calendar.HOUR_OF_DAY);
	        int min=fecha.get(Calendar.MINUTE);
	        int sec=fecha.get(Calendar.SECOND);
	        String Res="";
			Res=String.valueOf(anho)+".";
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			Res+=".";
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			Res+=" - ";
			
			if (hora<10){
				Res+="0"+String.valueOf(hora);
			}else{
				Res+=String.valueOf(hora);
			}
			Res+=":";
			if (min<10){
				Res+="0"+String.valueOf(min);
			}else{
				Res+=String.valueOf(min);
			}
			Res+=":";
			if (sec<10){
				Res+="0"+String.valueOf(sec);
			}else{
				Res+=String.valueOf(sec);
			}
			return Res;
	        
			
		}
		public static String FechaSistemaCompletaString(){
			Calendar fecha = new GregorianCalendar();
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        int hora=fecha.get(Calendar.HOUR_OF_DAY);
	        int min=fecha.get(Calendar.MINUTE);
	        int sec=fecha.get(Calendar.SECOND);
	        
			String Res="";
			Res=String.valueOf(anho);
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			if (hora<10){
				Res+="0"+String.valueOf(hora);
			}else{
				Res+=String.valueOf(hora);
			}
			if (min<10){
				Res+="0"+String.valueOf(min);
			}else{
				Res+=String.valueOf(min);
			}
			if (sec<10){
				Res+="0"+String.valueOf(sec);
			}else{
				Res+=String.valueOf(sec);
			}
			return Res;
		}
		public static String FechaSistemaLogString(){
			Calendar fecha = new GregorianCalendar();
			int anho = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH)+1;
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
	        int hora=fecha.get(Calendar.HOUR_OF_DAY);
	        int min=fecha.get(Calendar.MINUTE);
	        int sec=fecha.get(Calendar.SECOND);
	        
			String Res="";
			Res=String.valueOf(anho);
			Res+=".";
			if (mes<10){
				Res+="0"+String.valueOf(mes);
			}else{
				Res+=String.valueOf(mes);
			}
			Res+=".";
			if (dia<10){
				Res+="0"+String.valueOf(dia);
			}else{
				Res+=String.valueOf(dia);
			}
			Res+=" ";
			if (hora<10){
				Res+="0"+String.valueOf(hora);
			}else{
				Res+=String.valueOf(hora);
			}
			Res+=":";
			if (min<10){
				Res+="0"+String.valueOf(min);
			}else{
				Res+=String.valueOf(min);
			}
			Res+=":";
			if (sec<10){
				Res+="0"+String.valueOf(sec);
			}else{
				Res+=String.valueOf(sec);
			}
			return Res;
		}
		
		public FechaSimple(){
			
		}
		
		public static double DifDias(String fecIni, String fecFin, String formato) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
				Date fechaInicial=dateFormat.parse(fecIni);
				Date fechaFinal=dateFormat.parse(fecFin);
				double fecIniDbl=(double) (fechaInicial.getTime());
				double fecFinDbl=(double) (fechaFinal.getTime());
				double anhos=(fecFinDbl-fecIniDbl)/86400000;
				return anhos;
			} catch (Exception e) {
				// TODO: handle exception
				String msg="FechaSimple::DifDias::ERROR::No se pudieron restar las fechas\n"+e.toString();
				System.out.println(msg);
				return 0;
			}
		}
		
		public static double DifAnhosBase365(String fecIni, String fecFin) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date fechaInicial=dateFormat.parse(fecIni);
				Date fechaFinal=dateFormat.parse(fecFin);
				double fecIniDbl=(double) (fechaInicial.getTime());
				double fecFinDbl=(double) (fechaFinal.getTime());
				double anhos=(fecFinDbl-fecIniDbl)/86400000/365;
				return anhos;
			} catch (Exception e) {
				// TODO: handle exception
				String msg="FechaSimple::DifAnhosBase365::ERROR::No se pudieron restar las fechas\n"+e.toString();
				System.out.println(msg);
				return 0;
			}
		}
		
		public static double DifAnhosBase360(String fecIni, String fecFin) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date fechaInicial=dateFormat.parse(fecIni);
				Date fechaFinal=dateFormat.parse(fecFin);
				double fecIniDbl=(double) (fechaInicial.getTime());
				double fecFinDbl=(double) (fechaFinal.getTime());
				double anhos=(fecFinDbl-fecIniDbl)/(86400000*360);
				return anhos;
			} catch (Exception e) {
				// TODO: handle exception
				String msg="FechaSimple::DifAnhosBase360::ERROR::No se pudieron restar las fechas\n"+e.toString();
				System.out.println(msg);
				return 0;
			}
		}
		public static double diferenciaAnhosFechas(Date ini, Date fin, double base) {
			double fecIniDbl=(double) (ini.getTime());
			double fecFinDbl=(double) (fin.getTime());
			double anhos=(fecFinDbl-fecIniDbl)/(86400000*base);
			return anhos;
		}
		
		public static double diferenciaDiasFechas(Date ini, Date fin) {
			double fecIniDbl=(double) (ini.getTime());
			double fecFinDbl=(double) (fin.getTime());
			double anhos=(fecFinDbl-fecIniDbl)/(86400000);
			return anhos;
		}

		public static String [] getFechasVtos(int numVtos, int anho, int mes, int dia){
			try {
				String []res=new String[numVtos];
				for(int i=0;i<numVtos;i++) {
					Calendar cal=PrimerVto(anho, mes, dia);
					res[i]=cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
					anho=cal.get(Calendar.YEAR);
					mes=cal.get(Calendar.MONTH)+1;
					dia=cal.get(Calendar.DAY_OF_MONTH);
				}
				return res;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}
		
		
		private static Calendar PrimerVto(int anho, int mes, int dia) {
			
			//String res="";
			//String fechaArgumento=anho+"-"+mes+"-"+dia;
			int conta=0;
			boolean continua=true;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				while (continua) {
					conta++;
					dia++;
					if(dia>31) {
						dia=1;
						mes++;
						if(mes>12) {
							mes=1;
							anho++;
						}
					}
					String fecStr=anho+"-"+mes+"-"+dia;
					//System.out.println(fecStr);
					try {
						Date fecha=dateFormat.parse(fecStr);
						Calendar cal=new GregorianCalendar();
						cal.setTime(fecha);
						
						if(esVto(cal)) {
							//System.out.println("Vencimiento encontrado para : "+fechaArgumento+" - "+fecStr);
							continua=false;
							return cal;
						}
					} catch (Exception e2) {
						// TODO: handle exception
						System.out.println("Fallo al evaluar la fehca");
					}
					
					if(conta>100) {
						continua=false;
					}
					
				}
				return null;
			} catch (Exception e) {
				// TODO: handle exception
				String msg="FechaSimple::PrimerVto::ERROR::Anho:"+anho+" - mes;"+mes+" - dia:"+dia+"\n"+e.toString();
				System.out.println(msg);
				return null;
			}
			
			
		}

		private static boolean esVto(Calendar fecha) {
			try {
				if(fecha.get(Calendar.DAY_OF_WEEK)==6 && fecha.get(Calendar.DAY_OF_WEEK_IN_MONTH)==3) {
					int mes=fecha.get(Calendar.MONTH);
					if(mes==2 || mes==5 || mes==8 || mes==11) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("FechaSimple::esVto::ERROR::"+e.toString());
				return false;
			}
		}

}
