package config;

import java.io.BufferedWriter;
import java.io.FileWriter;

import DateAndTime.FechaSimple;

public class LogGlobal {
	
	
	private static String RutaLog = InfoSQL.LogOperaciones();
	private static String RutaErrors = InfoSQL.LogErrores();
	private static String RutaCritical= InfoSQL.LogCritical();
	
	public static void WriteMessage(String msg){
		try{
			FileWriter Escritor=new FileWriter(RutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaCompletaTXT()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
			System.out.println(msg);
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+RutaLog);
		}
	}
	public static void WriteError(String msg){
		try{
			FileWriter Escritor=new FileWriter(RutaErrors, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaCompletaTXT()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
			System.out.println(msg);
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+RutaErrors);
		}
	}
	public static void WriteCritical(String msg){
		try{
			FileWriter Escritor=new FileWriter(RutaCritical, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaCompletaTXT()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
			System.out.println(msg);
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+RutaCritical);
		}
	}
}
