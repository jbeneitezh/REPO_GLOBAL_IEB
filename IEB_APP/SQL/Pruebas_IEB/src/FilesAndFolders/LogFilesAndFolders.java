package FilesAndFolders;

import java.io.BufferedWriter;
import java.io.FileWriter;

import config.InfoSQL;
import DateAndTime.FechaSimple;

public class LogFilesAndFolders {
	public LogFilesAndFolders(){
		RutaLog=InfoSQL.LogOperaciones();
		RutaErrors=InfoSQL.LogErrores();
	}
	private String RutaLog;
	private String RutaErrors;
	
	public void WriteMessage(String msg){
		try{
			FileWriter Escritor=new FileWriter(RutaLog, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaCompletaTXT()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+RutaLog);
		}
	}
	public void WriteError(String msg){
		try{
			FileWriter Escritor=new FileWriter(RutaErrors, true);
			BufferedWriter BufEscritor=new BufferedWriter(Escritor);
			BufEscritor.write(FechaSimple.FechaSistemaCompletaTXT()+" "+msg);
			BufEscritor.newLine();
			BufEscritor.close();
			BufEscritor=null;
			Escritor.close();
			Escritor=null;
		}catch(Exception e){
			System.out.println("No se pudo escribir en: "+RutaErrors);
		}
	}
}
