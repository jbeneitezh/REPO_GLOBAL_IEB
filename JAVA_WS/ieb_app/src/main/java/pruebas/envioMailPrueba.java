package pruebas;

import EnvioEmail.EnvioMailMain;

public class envioMailPrueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dest="jbeneitezh@gmail.com";
		String asu="ASUNTO...";
		String bo="BODY\nL2....\nL3....";
		String adj="C:\\JavaEAFI\\conexion\\Connection_Config.txt;C:\\JavaEAFI\\conexion\\mail_config.txt";
		String []argum=new String [4];
		argum[0]=dest;
		argum[1]=asu;
		argum[2]=bo;
		argum[3]=adj;
		
		EnvioMailMain.main(argum);
		
	}

}
