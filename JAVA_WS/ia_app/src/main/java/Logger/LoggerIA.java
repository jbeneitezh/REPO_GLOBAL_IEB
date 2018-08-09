package Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;

import Config.ConfigFolders;
import DateAndTime.FechaSimple;


public class LoggerIA {
	
	private String FolderLog;
	private String rutaFull;
	private String rutaLog;
	private String rutaError;
	
	
	public LoggerIA(String nombre){
		FolderLog=ConfigFolders.rutaLog();
		rutaFull=FolderLog+"\\logFull_"+FechaSimple.FechaSistemaString()+".txt";
		rutaLog=FolderLog+"\\"+nombre+"_"+FechaSimple.FechaSistemaString()+".txt";
		rutaError=FolderLog+"\\"+nombre+"_error_"+FechaSimple.FechaSistemaString()+".txt";
	}
	
	public void WriteMessage(String msg){
		try{
			FileWriter Escritor=new FileWriter(rutaFull, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaFull);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaLog);
		}
	}
	public void WriteMessage(String msg, boolean imprimir){
		try{
			FileWriter Escritor=new FileWriter(rutaFull, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaFull);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaLog);
		}
		if(imprimir) {
			System.out.println(msg);
		}
	}
	public void WriteError(String msg){
		try{
			FileWriter Escritor=new FileWriter(rutaFull, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaFull);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaLog);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaError, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaError);
		}
	}
	public void WriteError(String msg, boolean imprimir){
		try{
			FileWriter Escritor=new FileWriter(rutaFull, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaFull);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaLog);
		}
		try{
			FileWriter Escritor=new FileWriter(rutaError, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaLogString()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+rutaError);
		}
		if(imprimir) {
			System.out.println(msg);
		}
	}
	
}