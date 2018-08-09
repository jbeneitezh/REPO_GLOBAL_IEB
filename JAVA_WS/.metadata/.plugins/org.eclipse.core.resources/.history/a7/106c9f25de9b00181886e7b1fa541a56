package FilesAndFolders;



import java.util.*;
import java.io.File;

public class DirectorioCLS {
	public DirectorioCLS(){
		
	};
	public static Vector <String> GetDirectoryFordersVector(String Path){
		Vector <String> v=new Vector<>();
        File folder = new File(Path);
        File[] listOfFiles = folder.listFiles(); 
        for (int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isDirectory()){
                v.add(listOfFiles[i].getName());
                //System.out.println(v.lastElement());
            }
        }
        return v;
	}
	public static Vector <String> GetDirectoryFilesVector(String Path){
		Vector <String> v=new Vector<>();
        File folder = new File(Path);
        File[] listOfFiles = folder.listFiles(); 
        for (int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                v.add(listOfFiles[i].getName());
                //System.out.println(v.lastElement());
            }
        }
        return v;
	}
	public static String[] removeElements(String[] input, int itemRemove) {
	    if (input != null) {
	        List<String> list = new ArrayList<String>(Arrays.asList(input));
	        list.remove(itemRemove);
	        return list.toArray(new String[0]);
	    } else {
	        return new String[0];
	    }
	}
			
	public static String [] GetDirectoryFilesArray(String Path){
        File folder = new File(Path);
        File [] Fich=folder.listFiles();
        int Contador=0;
        for (int i = 0; i < Fich.length; i++){
            if (Fich[i].isFile()){
                Contador++;
            }
        }
        String [] Ficheros = new String [Contador];
        int j=0;
        for (int i = 0; i < Fich.length; i++){
            if (Fich[i].isFile()){
                Ficheros[j]=Fich[i].getName();
                //System.out.println(Ficheros[j]);
                j++;
            }
        }
        return Ficheros;
	}		
	
	public static String rutaJar(){
		String txt=DirectorioCLS.class.getProtectionDomain().getCodeSource().getLocation().toString();
		txt=txt.substring(6,txt.length());
		return txt;
	}
	        
	
}
