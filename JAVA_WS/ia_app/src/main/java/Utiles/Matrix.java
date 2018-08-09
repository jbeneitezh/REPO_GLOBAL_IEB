package Utiles;

import Logger.LoggerIA;

public class Matrix {
	public static void printMatrix(String [][] arr, boolean logWrite) {
		
		LoggerIA log=new LoggerIA("printMatrix");
		int cols=arr[0].length;
		int []maxs=new int [cols];
		
		if (arr.length<1 || arr[0].length<0) {
			return;
		}
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<cols;j++) {
				if(arr[i][j]==null) {
					arr[i][j]="";
				}
				if(arr[i][j].length()>maxs[j]) {
					maxs[j]=arr[i][j].length();
				}
			}
			
		}
		
		for(int i=0;i<arr.length;i++) {
			String txtFila="";
			for(int j=0;j<cols;j++) {
				String txtCol=arr[i][j];
				while (txtCol.length()<maxs[j]+1) {
					txtCol+=" ";
				}
				if(j>0) {
					txtCol=" - '"+txtCol;
				}
				txtFila+=txtCol;
			}
			System.out.println(txtFila);
			if(logWrite) {
				log.WriteMessage(txtFila);
			}
		}
		
	}
}
