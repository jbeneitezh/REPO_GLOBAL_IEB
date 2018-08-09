package Utiles;

public class Matrix {
	public static void printMatrix(String [][] arr) {
		
		int cols=arr[0].length;
		int []maxs=new int [cols];
		
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
			String txtFila=String.valueOf(i);
			for(int j=0;j<cols;j++) {
				String txtCol=arr[i][j]+"'";
				while (txtCol.length()<maxs[j]+1) {
					txtCol+=" ";
				}
				txtCol=" - '"+txtCol;
				txtFila+=txtCol;
			}
			System.out.println(txtFila);
		}
		
	}
}
