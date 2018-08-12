package Menus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

import Config.ConfigFolders;
import DateAndTime.FechaSimple;
import FilesAndFolders.FicherosCLS;
import Logger.LoggerIA;
import MenuUtil.ActionsCLS;
import MenuUtil.MenusSQL;
import MenuUtil.menuOption;
import Utiles.Matrix;
import Utiles.resultsetUseful;

public class MENU_ESCENARIO {
	private Connection conexion;
	private String IDEscenario="";
	private Scanner scanner;
	
	private double []minimosX;
	private double []maximosX;
	private int    []decimalesX;
	private String []camposX;
	private String []tiposX;
	private double []minimosY;
	private double []maximosY;
	private int    []decimalesY;
	private String []camposY;
	private String []tiposY;
	
	private String []notNullX;
	private String []notNullY;
	
	private String nombreTablaX="";
	private String nombreTablaY="";
	
	private LoggerIA log=new LoggerIA("MENU_ESCENARIO");
	

	public MENU_ESCENARIO(Connection connection, Scanner sca, String id) {
		// TODO Auto-generated constructor stub
		conexion=connection;
		scanner=sca;
		IDEscenario = id;
		nombreTablaX="T_X_"+IDEscenario;
		nombreTablaY="T_Y_"+IDEscenario;
	}
	
	public void OpcionesEscenario() {
		String msg="Accediendo al escenario con id: "+IDEscenario;
		log.WriteMessage(msg);
		System.out.println(msg);
		msg="Buscando opciones...";
		String accion=MenusSQL.getAction(conexion, "MENU_ESCENARIO", scanner);
		ActionsCLS.procesaAccionPorIDEscenario(accion, conexion, scanner, IDEscenario);
	}
	
	
	public void verParamEscenario() {
		String msg="Buscando paramentros del escenario "+IDEscenario;
		log.WriteMessage(msg);
		System.out.println(msg);
		try {
			String Query="SELECT a.ID_PARAM, \n"
						+"       a.DESCRIPCION, \n"
						+"       b.VALOR, \n"
						+"       a.DATA_TYPE \n"
						+"  FROM IA_APP.T_CONFIG_ESC_PARAM a, \n"
						+"       IA_APP.T_CONFIG_ESCENARIOS b \n"
						+" WHERE b.ID_ESCENARIO = "+IDEscenario+" \n"
						+"   AND a.ID_PARAM = b.ID_PARAM \n"
						+"   AND a.MENU = 'INPUT' \n"
						+" ORDER BY a.ID_PARAM";
			log.WriteMessage(Query);
			System.out.println(Query);
			Statement sta=conexion.createStatement();
			ResultSet res=sta.executeQuery(Query);
			msg="Opciones encontradas del escenario "+IDEscenario;
			log.WriteMessage(msg);
			System.out.println(msg);
			msg="Mostrando opciones del escenario "+IDEscenario;
			log.WriteMessage(msg);
			System.out.println(msg);
			while(res.next()) {
				msg="  "+res.getInt(1)+" - "+res.getString(2)+" - '"+res.getString(3)+"' - "+res.getString(4);
				log.WriteMessage(msg);
				System.out.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="verParamEscenario::ERROR::No se pudieron mostrar los parametros.";
			log.WriteMessage(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteMessage(msg);
			System.out.println(msg);
		}
		OpcionesEscenario();
		
	}
	
	public void verStatusEscenario() {
		String msg="Buscando status del escenario "+IDEscenario;
		log.WriteMessage(msg);
		System.out.println(msg);
		try {
			String Query="SELECT a.ID_PARAM, \n"
						+"       a.DESCRIPCION, \n"
						+"       b.VALOR, \n"
						//+"       a.DATA_TYPE \n"
						+"       IFNULL((SELECT z.DESCRIPCION FROM T_PARAM_VALORES_VALIDOS z WHERE z.ID_PARAM = a.ID_PARAM AND z.ID_OPCION = b.VALOR), ' ') \n"
						+"  FROM IA_APP.T_CONFIG_ESC_PARAM a, \n"
						+"       IA_APP.T_CONFIG_ESCENARIOS b \n"
						+" WHERE b.ID_ESCENARIO = "+IDEscenario+" \n"
						+"   AND a.ID_PARAM = b.ID_PARAM \n"
						+"   AND a.MENU = 'STATUS' \n"
						+" ORDER BY a.ID_PARAM";
			log.WriteMessage(Query);
			//System.out.println(Query);
			Statement sta=conexion.createStatement();
			ResultSet res=sta.executeQuery(Query);
			msg="Status encontrado del escenario "+IDEscenario;
			log.WriteMessage(msg);
			System.out.println(msg);
			msg="Mostrando status del escenario "+IDEscenario;
			log.WriteMessage(msg);
			System.out.println(msg);
			while(res.next()) {
				msg="  "+res.getInt(1)+" - "+res.getString(2)+" - '"+res.getString(3)+"' - "+res.getString(4);
				log.WriteMessage(msg);
				System.out.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="verStatusEscenario::ERROR::No se pudieron mostrar los parametros.";
			log.WriteMessage(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteMessage(msg);
			System.out.println(msg);
		}
		OpcionesEscenario();
		
	}
	
	public void modParamEscenario() {
		Vector<String>ids=new Vector<String>();
		String msg="Buscando paramentros del escenario "+IDEscenario;
		log.WriteMessage(msg);
		System.out.println(msg);
		try {
			String Query="SELECT a.ID_PARAM, \n"
						+"       a.DESCRIPCION, \n"
						+"       b.VALOR, \n"
						+"       a.DATA_TYPE \n"
						+"  FROM IA_APP.T_CONFIG_ESC_PARAM a, \n"
						+"       IA_APP.T_CONFIG_ESCENARIOS b \n"
						+" WHERE b.ID_ESCENARIO = "+IDEscenario+" \n"
						+"   AND a.ID_PARAM = b.ID_PARAM"
						+"   AND a.MENU = 'INPUT' \n"
						+" ORDER BY a.ID_PARAM";
			log.WriteMessage(Query);
			//System.out.println(Query);
			Statement sta=conexion.createStatement();
			ResultSet res=sta.executeQuery(Query);
			String[][] matOpciones=resultsetUseful.resultsetToMatrix(res);
			if(matOpciones.length<1) {
				msg="No se encontraron parametros para el escenario "+IDEscenario;
				log.WriteMessage(msg);
				System.out.println(msg);
				OpcionesEscenario();
				return;
			}
			
			for(int i=0;i<matOpciones.length;i++) {
				ids.add(matOpciones[i][0]);
			}
			
			boolean valorvalido=false;
			while (valorvalido==false) {
				msg="";
				log.WriteMessage(msg);
				System.out.println(msg);
				msg="Seleccione el parametro que desea modificar: ";
				log.WriteMessage(msg);
				System.out.println(msg);
				Matrix.printMatrix(matOpciones, true);	
				msg="0 - Volver";
				log.WriteMessage(msg);
				System.out.println(msg);
				String opcionElegida=scanner.nextLine();
				if(opcionElegida.equals("0")) {
					OpcionesEscenario();
					return;
				}
				
				if(menuOption.existeOpcion(ids, opcionElegida)){
					valorvalido=true;
					for(int i=0;i<ids.size();i++) {
						if(ids.get(i).equals(opcionElegida)) {
							modificaParam(ids.get(i), matOpciones[i][2], matOpciones[i][3], matOpciones[i][1]);
						}
					}
				}else {
					msg="El parametro seleccionado no es valido. Debe seleccionar una opcion de la lista.";
					log.WriteMessage(msg);
					System.out.println(msg);
				}
			
			}
		}catch (Exception e) {
			// TODO: handle exception
			msg="modParamEscenario::ERROR::No se pudieron cambiar parametros.";
			log.WriteMessage(msg);
			System.out.println(msg);
			msg=e.toString();
			log.WriteMessage(msg);
			System.out.println(msg);
		}
		
	}
	
	public void procesaFicheros() {
		
		String msg="void::procesaFicheros";
		log.WriteMessage(msg,true);
		
		msg="\nSe procesaran los ficheros de inputs y outputs. \n\nSi las tablas del escenario no existen se generaran."
		   +" En caso contrario se actualizaran.\n\n"
		   +"Este proceso podria tardar varios minutos en funcion del tama�o de los ficheros. \n\n�Desea continuar?";
		
		if(MenusSQL.preguntaConfirmacion(msg, scanner)==false) {
			msg="Operacion cancelada.";
			log.WriteMessage(msg, true);
			OpcionesEscenario();
			return;
		}
		
		msg="Recuperando informacion de los ficheros...";
		log.WriteMessage(msg,true);
		String query="SELECT a.VALOR \n"
					+"  FROM IA_APP.T_CONFIG_ESCENARIOS a \n"
					+" WHERE a.ID_ESCENARIO = "+IDEscenario+" \n"
					+"   AND a.ID_PARAM IN (1, 2) \n"
					+" ORDER BY a.ID_PARAM";
		String [][]ficheros=resultsetUseful.queryToMatrix(query, conexion);
		msg="ficheros recuperados. Total "+ficheros.length+".";
		log.WriteMessage(msg, true);
		
		String rutaFic=ConfigFolders.rutaImport();
		String ficX="";
		String ficY="";
		
		try {
			ficX=ficheros[1][0];
			ficY=ficheros[2][0];
		} catch (Exception e) {
			// TODO: handle exception
			msg="Se produjo un error al recuperar los nombres de los ficheros.";
			log.WriteError(msg, true);
			msg=e.toString();
			log.WriteError(msg, true);
			OpcionesEscenario();
			return;
		}
		
		//Verificaciones previas - Comprobamos que existan los ficheros *****
		if(FicherosCLS.fileExists(rutaFic+"\\"+ficX)==false) {
			msg="El fichero de inputs no existe '"+rutaFic+"\\"+ficX;
			log.WriteMessage(msg, true);
			msg="Deteniendo...";
			log.WriteMessage(msg, true);
			OpcionesEscenario();
			return;
		}else {
			msg="El fichero de inputs existe. '"+ficX+"'";
			log.WriteMessage(msg, true);
		}
		
		if(FicherosCLS.fileExists(rutaFic+"\\"+ficY)==false) {
			msg="El fichero de outputs no existe '"+rutaFic+"\\"+ficY;
			log.WriteMessage(msg, true);
			msg="Deteniendo...";
			log.WriteMessage(msg, true);
			OpcionesEscenario();
			return;
		}else {
			msg="El fichero de outputs existe. '"+ficY+"'";
			log.WriteMessage(msg, true);
		}
		
		//Leemos los ficheros *****
		msg="Leyendo ficheros...(0/2)";
		log.WriteMessage(msg, true);
		String [][]ficXStr=FicherosCLS.readFileMatrixString(rutaFic+"\\"+ficX, ";");
		msg="Fichero de inputs leido";
		log.WriteMessage(msg, true);
		msg="Leyendo ficheros...(1/2)";
		log.WriteMessage(msg, true);
		String [][]ficYStr=FicherosCLS.readFileMatrixString(rutaFic+"\\"+ficY, ";");
		msg="Fichero leidos...(2/2)";
		log.WriteMessage(msg, true);
		
		
		//Realizamos validaciones *****
		if(evaluaFicheros(ficXStr, ficYStr)) {
			msg="Primera validacion superada.";
			log.WriteMessage(msg, true);
		}else {
			msg="Validaciones no cumplidas. Se detendra...";
			log.WriteMessage(msg, true);
			OpcionesEscenario();
			return;
		}
		
		//Comprobamos si existen las tablas
		if(modificaParametroLlamada("100", "1")==false) {
			msg="Error al modificar el parametro";
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		
		boolean existeX=resultsetUseful.existeTabla(nombreTablaX, conexion);
		boolean existeY=resultsetUseful.existeTabla(nombreTablaY, conexion);		
		
		if(existeX==false){
			msg="La tabla "+nombreTablaX+" no existe. Se creara...";
			log.WriteMessage(msg, true);
			if(creaTabla(nombreTablaX, camposX, tiposX, notNullX)==false) {
				msg="Error al crear la tabla "+nombreTablaX;
				log.WriteMessage(msg, true);
				rollBack();	
				OpcionesEscenario();
				return;
			}
		}else {
			msg="La tabla "+nombreTablaX+" ya existia";
			log.WriteMessage(msg, true);
		}
		
		if(existeY==false){
			msg="La tabla "+nombreTablaY+" no existe. Se creara...";
			log.WriteMessage(msg, true);
			if(creaTabla(nombreTablaY, camposY, tiposY, notNullY)==false) {
				msg="Error al crear la tabla "+nombreTablaY;
				log.WriteMessage(msg, true);
				rollBack();	
				OpcionesEscenario();
				return;
			}
		}else {
			msg="La tabla "+nombreTablaY+" ya existia";
			log.WriteMessage(msg, true);
		}
		
		boolean insertaX=realizaInserciones(nombreTablaX, ficXStr, camposX);
		if(insertaX) {
			msg="Procesado el fichero "+ficX;
			log.WriteMessage(msg, true);
		}else {
			msg="Error procesando el fichero "+ficX;
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		
		boolean insertaY=realizaInserciones(nombreTablaY, ficYStr, camposY);
		if(insertaY) {
			msg="Procesado el fichero "+ficY;
			log.WriteMessage(msg, true);
		}else {
			msg="Error procesando el fichero "+ficY;
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		
		for(int i=1;i<camposX.length;i++) {
			if(insertaAutocorrelacion(camposX[i], 0)==false) {
				msg="Error al insertar la autocorrelacion para el campo "+camposX[i];
				log.WriteMessage(msg,true);
				rollBack();	
				OpcionesEscenario();
				return;
			}
		}
		
		if(modificaParametroLlamada("100", "2")==false) {
			msg="Error al modificar el parametro";
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		
		if(modificaParametroLlamada("101", String.valueOf(camposX.length))==false) {
			msg="Error al modificar el parametro";
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		if(modificaParametroLlamada("102", String.valueOf(camposY.length))==false) {
			msg="Error al modificar el parametro";
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		if(modificaParametroLlamada("103", String.valueOf(camposX.length))==false) {
			msg="Error al modificar el parametro";
			log.WriteMessage(msg, true);
			rollBack();	
			OpcionesEscenario();
			return;
		}
		
		commit();
		
		msg="procesaFicheros::proceso completado";
		log.WriteMessage(msg, true);
		OpcionesEscenario();
	}
	
	public void modAutoCorrel() {
		
		System.out.println("void::modAutoCorrel");
		String msg="Entrando a modificar autocorrelaciones del escenario "+IDEscenario;
		log.WriteMessage(msg,true);
		
		try {
			Statement sta=conexion.createStatement();
			if(getParamEscenario(IDEscenario, "100", sta).trim().equals("1")) {
				msg="Los ficheros no han sido procesados. No se pueden modificar las autocorrelaciones hasta que no haya sido procesados.";
				log.WriteMessage(msg,true);
				sta.close();
				OpcionesEscenario();
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al evaluar el status";
			log.WriteMessage(msg,true);
			OpcionesEscenario();
			return;
		}
		
		
		String query="SELECT a.CAMPO, a.AUTOCORREL FROM T_ESCENARIOS_AUTOCORRELACION a WHERE ID_ESCENARIO = "+IDEscenario;
		String [][]opciones=resultsetUseful.queryToMatrix(query, conexion);
		String [][]opcionesOK=new String [opciones.length+1][opciones[0].length+1];
		for(int i=0;i<opciones.length;i++) {
			opcionesOK[i][0]=String.valueOf(i+1);
			for(int j=0;j<opciones[0].length;j++) {
				opcionesOK[i][j+1]=opciones[i][j];
			}
		}
		opcionesOK[opcionesOK.length-1][0]="0";
		opcionesOK[opcionesOK.length-1][1]="Volver";
		
		String consulta="Seleccione el campo al que modificar la autocorrelacion";
		
		String elegida=MenusSQL.generaConsulta(consulta, opcionesOK, scanner, 0);
		if(elegida.equals("0")) {
			msg="volviendo...";
			
			log.WriteMessage(msg,true);
			OpcionesEscenario();
			return;
		}
		
		int fila=Integer.parseInt(elegida);
		msg="Se va a modificar la autocorrelacion del campo "+opcionesOK[fila-1][1];
		log.WriteMessage(msg,true);
		
		boolean validado=false;
		String valorNuevo="";
		while(validado==false) {
			msg="Seleccione un nuevo valor para la autocorrelacion del campo "+opcionesOK[fila-1][1];
			log.WriteMessage(msg,true);
			valorNuevo=scanner.nextLine();
			if(esParametroNuevoValido(valorNuevo, "INT")==false) {
				msg="'"+valorNuevo+"' no es de tipo entero";
				log.WriteMessage(msg,true);
				validado=false;
			}else {
				validado=true;
			}
		}
		
		msg="Se va a modificar la autocorrelacion en "+opcionesOK[fila-1][1]+" pasando de '"+opcionesOK[fila-1][2]+"' a '"+valorNuevo+"'. �Esta de acuerdo? (S/N)";
		boolean confirma=MenusSQL.preguntaConfirmacion(msg, scanner);
		
		if(confirma==false) {
			msg="Cancelando...";
			log.WriteMessage(msg,true);
			OpcionesEscenario();
			return;
		}
		
		boolean modificada=insertaAutocorrelacion(opcionesOK[fila-1][1], Integer.parseInt(valorNuevo));
		if(modificada==false) {
			msg="Fallo al modificar la autocorrelacion en  el campo "+opcionesOK[fila-1][1];
			log.WriteMessage(msg,true);
			try {
				conexion.rollback();
				msg="rollback";
				log.WriteMessage(msg,true);
			} catch (Exception e) {
				// TODO: handle exception
				msg="Fallo en el rollback";
				log.WriteMessage(msg,true);
				msg=e.toString();
				log.WriteMessage(msg,true);
			}
			
		}else {
			msg="Modificada la autocorrelacion en el campo "+opcionesOK[fila-1][1]+" a "+valorNuevo;
			log.WriteMessage(msg,true);
			if(actualiza_total_columnas()==true) {
				try {
					conexion.commit();
					msg="commit";
					log.WriteMessage(msg,true);	
				} catch (Exception e) {
					// TODO: handle exception
					msg="Fallo en el commit";
					log.WriteMessage(msg,true);
					msg=e.toString();
					log.WriteMessage(msg,true);
					rollBack();
				}
			}else {
				msg="Fallo al actualizar el total de columnas";
				log.WriteMessage(msg,true);
				rollBack();
			}
			
		}
		OpcionesEscenario();
	}
	
	public void borraEscenario() {
		
		System.out.println("void::borraEscenario");
		
		String msg="Entrando a borrar el escenario "+IDEscenario;
		log.WriteMessage(msg,true);
		msg="";
		log.WriteMessage(msg,true);
		msg="El escenario se borrara junto a toda su parametrizacion. �Esta de acuerdo?";
		boolean respuesta=MenusSQL.preguntaConfirmacion(msg, scanner);
		if (respuesta==false) {
			msg="No se borrara el escenario "+IDEscenario;
			log.WriteMessage(msg);
			System.out.println(msg);
		}else {
			msg="Borrando el escenario "+IDEscenario+"...";
			log.WriteMessage(msg,true);
			
			try {
				CallableStatement stat=conexion.prepareCall("{call IA_APP.BORRA_ESCENARIO(?,?,?)}");
				stat.setInt(1, Integer.parseInt(IDEscenario));
				stat.registerOutParameter(2, java.sql.Types.INTEGER);
				stat.registerOutParameter(3, java.sql.Types.VARCHAR);
				stat.executeUpdate();
				int res=stat.getInt(2);
				String resStr=stat.getString(3);
				
				msg=resStr;
				System.out.println(msg);
				log.WriteMessage(msg);
				stat.close();

				int borraX=resultsetUseful.borraTabla(nombreTablaX, conexion);
				int borraY=resultsetUseful.borraTabla(nombreTablaY, conexion);
				
				if (res==1 && borraX>=0 && borraY>=0) {
					conexion.commit();
					msg="Borrado el escenario "+IDEscenario;
					log.WriteMessage(msg);
					System.out.println(msg);
					
					String action="escenariosExistentes";
					ActionsCLS.procesaAccion(action, conexion, scanner);
					return;
					
				}else if (res==0) {
					conexion.rollback();
					msg="No se pudo borrar el escenario "+IDEscenario;
					log.WriteError(msg);
					System.out.println(msg);
				}	
			} catch (Exception e) {
				// TODO: handle exception
				msg="Error al borrar el escenario "+IDEscenario;
				log.WriteError(msg,true);
				msg=e.toString();
				log.WriteError(msg,true);
				try {
					conexion.rollback();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		OpcionesEscenario();
		
	}
	
	private void modificaParam(String idParam, String valorActual, String tipoDato, String descripcion) {
		
		String valorNuevo=""; //variable que almacena la opcion elegida por el usuario
		boolean modificar=false;
		
		String msg="Accediendo a la modificacion del parametro '"+descripcion+"'";
		System.out.println(msg);
		log.WriteMessage(msg);
		msg="El valor actual es: '"+valorActual+"'";
		System.out.println(msg);
		log.WriteMessage(msg);
		
		int existeParamListaValores=cuentaParametrosValoresValidos(idParam);
		if(existeParamListaValores==0) {
			boolean valorvalido=false;
			while (valorvalido==false) {
				msg="Indique el nuevo valor";
				System.out.println(msg);
				log.WriteMessage(msg);
				valorNuevo=""+scanner.nextLine();
				log.WriteMessage(valorNuevo);
				
				if(esParametroNuevoValido(valorNuevo, tipoDato)==false) {
					msg="'"+valorNuevo+"' no es un "+tipoDato+" valido.";
					valorvalido=false;
				}else {
					String consulta="Se va a modificar el parametro '"+descripcion+"' cambiando '"+valorActual+"' por "+valorNuevo+"'. �Esta de acuerdo? (S/N)";
					String decision=menuOption.procesaConsultaSINO(consulta, scanner);
					if(decision.equals("S")) {
						valorvalido=true;
						modificar=true;
					}else if (decision.equals("N")) {
						msg="Modificacion cancelada";
						log.WriteMessage(msg,true);
						modParamEscenario();
						return;
					}
				}
			}
			
		}else if(existeParamListaValores>0) {
			
			try {
				String query="SELECT a.ID_OPCION, \n"
							+"       a.DESCRIPCION \n"
						    +"  FROM IA_APP.T_PARAM_VALORES_VALIDOS a \n"
							+" WHERE a.ID_PARAM = "+idParam;
				msg="Lanzando Query";
				log.WriteMessage(msg, false);
				msg=query;
				log.WriteMessage(msg, false);
				String [][]matOpciones=resultsetUseful.queryToMatrix(query, conexion);
				msg="Seleccione un valor de la lista";
				valorNuevo=MenusSQL.generaConsulta(msg, matOpciones, scanner, 0);
				
				
				msg="Ha elegido '"+valorNuevo+"'. Se realizar� la modificacion. �Desea continuar?";
				boolean decision=MenusSQL.preguntaConfirmacion(msg, scanner);
				if(decision) {
					modificar=true;
				}else {
					msg="Modificacion cancelada";
					log.WriteMessage(msg,true);
					modParamEscenario();
					return;
				}
				log.WriteMessage(msg,true);
				

			} catch (Exception e) {
				// TODO: handle exception
				msg="MENU_ESCENARIO::modificaParam::ERROR::"+e.toString();
				valorNuevo="";
				log.WriteError(msg, true);
			}

		}
		
		
		if (valorNuevo.equals("") || modificar==false) {
			msg="Valor no v�lido.";
			log.WriteMessage(msg,true);
			msg="";
			log.WriteMessage(msg,true);
			modParamEscenario();
			return;
		}

					
		try {
			
			CallableStatement stat=conexion.prepareCall("{call IA_APP.MODIFICA_PARAM_ESCENARIO(?,?,?,?,?)}");
			stat.setInt(1, Integer.parseInt(IDEscenario));
			stat.setInt(2, Integer.parseInt(idParam));
			stat.setString(3, valorNuevo);
			stat.registerOutParameter(4, java.sql.Types.INTEGER);
			stat.registerOutParameter(5, java.sql.Types.VARCHAR);
			stat.executeUpdate();
			int res=stat.getInt(4);
			String resStr=stat.getString(5);
			
			msg=String.valueOf(res)+" - "+resStr;
			System.out.println(msg);
			log.WriteMessage(msg);
			
			if (res==1) {
				msg="Se actualizo el parametro '"+descripcion+"' a '"+valorNuevo+"'";
				log.WriteMessage(msg,true);
				conexion.commit();
				msg="commit";
				log.WriteMessage(msg,true);
			}else if (res==0) {
				conexion.rollback();
				msg="rollback";
				log.WriteMessage(msg,true);
				msg="No se actualizo el parametro '"+descripcion+"'";
				log.WriteError(msg);
				System.out.println(msg);
			}else if(res==-1) {
				msg="Error al procesar el procedimiento. Param: '"+descripcion+"'";
				log.WriteError(msg,true);
				conexion.rollback();
				msg="rollback";
				log.WriteMessage(msg,true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al actualizar el valor";
			System.out.println(msg);
			log.WriteError(msg);
			try {
				conexion.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		modParamEscenario();
		return;
	}
	
	private boolean esParametroNuevoValido(String valor, String tipoDato) {
		
		String msg="";
		
		if(valor.equals("")) {
			return false;
		}
		
		if(tipoDato.contains("VARCHAR") && valor.length()>0) {
			return true;
		}else if(tipoDato.contains("VARCHAR")){
			msg="esParametroNuevoValido::FALSE - Param: "+valor+" - Tipo esperado: "+tipoDato;
			log.WriteMessage(msg, true);
			return false;
		}
		
		if(tipoDato.equals("INT")) {
			try {
				Integer.parseInt(valor);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				msg="esParametroNuevoValido::FALSE - Param: "+valor+" - Tipo esperado: "+tipoDato;
				log.WriteMessage(msg, true);
				return false;
			}
		}
		
		if(tipoDato.contains("NUMERIC")) {
			try {
				Double.parseDouble(valor);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				msg="esParametroNuevoValido::FALSE - Param: "+valor+" - Tipo esperado: "+tipoDato;
				log.WriteMessage(msg, true);
				return false;
			}
		}
		
		msg="tipo de dato desconocido::"+tipoDato;
		log.WriteError(msg);
		System.out.println(msg);
		return false;
	}
	
	private int cuentaParametrosValoresValidos(String idparam) {
		String msg="";
		try {
			msg="Comprobando si existen parametrizaciones sobre el parametro "+idparam;
			log.WriteMessage(msg,true);
			String query="SELECT COUNT(*) FROM IA_APP.T_PARAM_VALORES_VALIDOS a WHERE a.ID_PARAM = "+idparam;
			log.WriteMessage(query,false);
			Statement sta=conexion.createStatement();
			ResultSet res=sta.executeQuery(query);
			int cuenta=0;
			while (res.next()) {
				cuenta=res.getInt(1);
			}
			res.close();
			sta.close();
			return cuenta;
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al comprobar si existen parametrizaciones sobre el parametro "+idparam;
			log.WriteMessage(msg,true);
			msg=e.toString();
			log.WriteMessage(msg,true);
			return -1;
		}
	}
	
	private int cuentaCoincidenciasFecha(String fecha, String tabla, Statement sta) {
		try {
			String query="SELECT COUNT(*) FROM "+tabla+" WHERE FECHA = STR_TO_DATE('"+fecha+"' , '%Y-%m-%d %T')";
			ResultSet res=sta.executeQuery(query);
			int cuenta=0;
			while(res.next()) {
				cuenta=res.getInt(1);
			}
			res.close();
			return cuenta;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="Error al contar fechas en la tabla "+tabla;
			log.WriteMessage(msg, true);
			msg=e.toString();
			log.WriteMessage(msg, true);
			return -1;
		}
	}
	
	private boolean evaluaFicheros(String [][]ficX, String [][]ficY) {
		boolean resultado=true;
		String msg="Realizando validaciones...";
		log.WriteMessage(msg, true);
		if(ficX.length != ficY.length) {
			msg="Error validaciones. Los ficheros no contienen el mismo numero de registros";
			log.WriteMessage(msg, true);
			return false;
		}else {
			msg="Los ficheros contienen el mismo numero de registros";
			log.WriteMessage(msg, true);
		}
		if(ficX[0].length<2) {
			msg="Error validaciones. Insuficientes columnas en el fichero de inputs: "+ficX[0].length;
			log.WriteMessage(msg, true);
			return false;
		}else {
			msg="Numero de columnas valido para el fichero de inputs: "+ficX[0].length;
			log.WriteMessage(msg, true);
		}
		
		if(ficY[0].length<2) {
			msg="Error validaciones. Insuficientes columnas en el fichero de outputs: "+ficY[0].length;
			log.WriteMessage(msg, true);
			return false;
		}else {
			msg="Numero de columnas valido para el fichero de outputs: "+ficY[0].length;
			log.WriteMessage(msg, true);
		}
		
		
		//Verificacion de fechas
		
		String formatoFecha="yyyy-MM-dd hh:mm:ss";
		
		for(int i=1;i<ficX.length;i++) {
			String fecha=ficX[i][0];
			if(fecha.length()<=10) {
				fecha+=" 00:00:00";
			}
			if(FechaSimple.esFecha(fecha, formatoFecha)==false) {
				msg="Error validaciones. Error formato fecha en el fichero de inputs en la linea "+i+": '"+fecha+"'";
				log.WriteMessage(msg, true);
				return false;
			}
		}
		msg="Formatos de fecha correctos en el fichero de intups";
		log.WriteMessage(msg, true);
		
		for(int i=1;i<ficY.length;i++) {
			String fecha=ficX[i][0];
			if(fecha.length()<=10) {
				fecha+=" 00:00:00";
			}
			if(FechaSimple.esFecha(fecha, formatoFecha)==false) {
				msg="Error validaciones. Error formato fecha en el fichero de outputs en la linea "+i+": '"+fecha+"'";
				log.WriteMessage(msg, true);
				return false;
			}
		}
		msg="Formatos de fecha correctos en el fichero de outputs";
		log.WriteMessage(msg, true);
		
		
		//Verificacion de numeros
		
		double []mins=new double [ficX[0].length];
		double []maxs=new double [ficX[0].length];
		int []decimales=new int  [ficX[0].length];
		
		for(int i=0;i<mins.length;i++) {
			mins[i]=999999999;
			maxs[i]=-999999999;
			decimales[i]=0;
		}
		
		for(int i=1;i<ficX.length;i++) {
			for(int j=1;j<ficX[i].length;j++) {
				if(esDouble(ficX[i][j])==false) {
					msg="Error validaciones. Error formato numero en el fichero de inputs en la linea "+i+", columna "+j+": '"+ficX[i][j]+"'";
					log.WriteMessage(msg, true);
					return false;
				}else {
					double num=Double.parseDouble(ficX[i][j]);
					if(num<mins[j])	{
						mins[j]=num;
					}
					if(num>maxs[j])	{
						maxs[j]=num;
					}
					int decim=0;
					try {
						String [] arr=ficX[i][j].toString().replace(".", ",").split(",");
						decim=arr[1].toString().length();
					} catch (Exception e) {
						// TODO: handle exception
					}
					//System.out.println("Numero: "+ficX[i][j]+" - Decimales: "+decim);
					if(decim>decimales[j]) {
						decimales[j]=decim;
					}
				}
			}
			
		}
		msg="Formatos de numeros correctos en el fichero de intups";
		
		minimosX=mins;
		maximosX=maxs;
		decimalesX=decimales;
		
		log.WriteMessage(msg, true);
		
		
		mins=new double [ficY[0].length];
		maxs=new double [ficY[0].length];
		decimales=new int [ficX[0].length];
		
		for(int i=0;i<mins.length;i++) {
			mins[i]=999999999;
			maxs[i]=-999999999;
			decimales[i]=0;
		}
		
		for(int i=1;i<ficY.length;i++) {
			for(int j=1;j<ficY[i].length;j++) {
				if(esDouble(ficY[i][j])==false) {
					msg="Error validaciones. Error formato numero en el fichero de inputs en la linea "+i+", columna "+j+": '"+ficY[i][j]+"'";
					log.WriteMessage(msg, true);
					return false;
				}else {
					double num=Double.parseDouble(ficY[i][j]);
					if(num<mins[j])	{
						mins[j]=num;
					}
					if(num>maxs[j])	{
						maxs[j]=num;
					}
					int decim=0;
					try {
						String [] arr=ficY[i][j].toString().replace(".", ",").split(",");
						decim=arr[1].toString().length();
					} catch (Exception e) {
						// TODO: handle exception
					}
					//System.out.println("Numero: "+ficY[i][j]+" - Decimales: "+decim);
					if(decim>decimales[j]) {
						decimales[j]=decim;
					}
				}
			}
			
		}
		
		minimosY=mins;
		maximosY=maxs;
		decimalesY=decimales;
		
		msg="Formatos de numeros correctos en el fichero de outputs";
		log.WriteMessage(msg, true);
		
		//Extraccion de nombres de campos
		
		String [] campos=new String [ficX[0].length];
		campos[0]="FECHA";
		for(int i=1;i<campos.length;i++) {
			campos[i]=ficX[0][i];
		}
		camposX=campos;
		
		campos=new String [ficY[0].length];
		campos[0]="FECHA";
		for(int i=1;i<campos.length;i++) {
			campos[i]=ficY[0][i];
		}
		camposY=campos;
		
		//Extraccion de tipos de datos
		String []tipos=new String[camposX.length];
		String [] notnulles=new String[camposX.length];
		for(int i=0;i<notnulles.length;i++) {
			notnulles[i]="NOT NULL";
		}
		notNullX=notnulles;
		
		tipos[0]="DATETIME";
		for(int i=1;i<camposX.length;i++) {
			String dec=String.valueOf(decimalesX[i]);
			String [] arr1=String.valueOf(maximosX[i]).replace(".",",").split(",");
			String [] arr2=String.valueOf(minimosX[i]).replace(".",",").split(",");
			int posiciones=Math.max(arr1[0].length(), arr2[0].length())+decimalesX[i]+1;
			tipos[i]="NUMERIC("+posiciones+", "+dec+")";
		}
		tiposX=tipos;
		
		

		tipos=new String[camposY.length];
		notnulles=new String[camposY.length];
		for(int i=0;i<notnulles.length;i++) {
			notnulles[i]="NOT NULL";
		}
		notNullY=notnulles;
		
		tipos[0]="DATETIME";
		for(int i=1;i<camposY.length;i++) {
			String dec=String.valueOf(decimalesY[i]);
			String [] arr1=String.valueOf(maximosY[i]).replace(".",",").split(",");
			String [] arr2=String.valueOf(minimosY[i]).replace(".",",").split(",");
			int posiciones=Math.max(arr1[0].length(), arr2[0].length())+decimalesY[i]+1;
			tipos[i]="NUMERIC("+posiciones+", "+dec+")";
		}
		tiposY=tipos;
		
		
		
		return resultado;
	}
	
	private static boolean esDouble(String dob) {
		try {
			Double.parseDouble(dob);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean creaTabla(String nombre, String []campos, String []tiposDatos, String []nullNotNull) {
		String msg="";
		try {
			msg="Creando tabla "+nombre;
			log.WriteMessage(msg,true);
			
			String tabla="CREATE TABLE "+nombre+" (";
			int longitud=tabla.length();
			
			for(int i=0;i<campos.length;i++) {
				String t="";
				if(i>0) {
					t=anadeEspacios(t,longitud);
				}
				t+=anadeEspacios(campos[i],30)+" ";
				t+=anadeEspacios(tiposDatos[i],30)+" ";
				t+=anadeEspacios(nullNotNull[i],30);
				if(i<campos.length-1) {
					t+=", ";
				}
				t+=" \n";
				tabla+=t;
			}
			tabla+=")";
			msg="Imprimiendo query: ";
			log.WriteMessage(msg,true);
			log.WriteMessage(tabla,true);
			
			Statement sta=conexion.createStatement();
			sta.executeUpdate(tabla);
			msg="Tabla creada: "+nombre;
			log.WriteMessage(msg,true);
			
			msg="Creando PK...";
			log.WriteMessage(msg,true);
			String PK="ALTER TABLE "+nombre+" ADD CONSTRAINT PK_"+nombre+" PRIMARY KEY ("+campos[0]+")";
			log.WriteMessage(PK,true);
			sta.executeUpdate(PK);
			msg="PK Creada.";
			log.WriteMessage(msg,true);
			
			sta.close();
			
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			msg="Error al crear la tabla: "+nombre;
			log.WriteMessage(msg,true);
			msg=e.toString();
			log.WriteMessage(msg,true);
			
			return false;
		}
	}
	
	private static String anadeEspacios(String t, int num) {
		String res=t;
		while(res.length()<num) {
			res=res+" ";
		}
		return res;
	}

	private boolean realizaInserciones(String tabla, String [][]valores,String []campos) {
		
		String msg="Se realizaran las inserciones en la tabla "+tabla;
		String msg2="";
		log.WriteMessage(msg, true);
		msg="Total registros: "+valores.length;
		log.WriteMessage(msg, true);
		int insertadas=0;int updates=0;int errores=0;
		int registros=valores.length;
		try {
			Statement sta=conexion.createStatement();
			
			for(int i=1;i<registros;i++) {
				try {
					int coincidencia=cuentaCoincidenciasFecha(valores[i][0], tabla, sta);
					if (coincidencia==0) {
						
						msg="El registro con fecha '"+valores[i][0]+"' no existe en la tabla "+tabla+". Se insertara...";
						//log.WriteMessage(msg, true);
						
						String insert="INSERT INTO "+tabla+" VALUES(STR_TO_DATE('"+valores[i][0]+"', '%Y-%m-%d %T')";
						for(int j=1;j<valores[i].length;j++) {
							insert+=", "+valores[i][j];
						}
						insert+=")";
						msg2=insert;
						
						sta.executeUpdate(insert);
						insertadas++;
						
						msg="OK "+insert;
						//log.WriteMessage(msg, true);
						
					}else if (coincidencia>0) {
						msg="El registro con fecha '"+valores[i][0]+"' se encontro en la tabla "+tabla+". Se updateara...";
						//log.WriteMessage(msg, true);
						
						String update="UPDATE "+tabla+" SET ";
						for(int j=1;j<valores[i].length;j++) {
							update+=campos[j]+" = "+valores[i][j];
							if(j<valores[i].length-1) {
								update+=", ";
							}
						}
						update+=" WHERE FECHA = STR_TO_DATE('"+valores[i][0]+"', '%Y-%m-%d %T')";
						msg2=update;
						sta.executeUpdate(update);
						updates++;
						
						msg="OK "+update;
						//log.WriteMessage(msg, true);
						
					}else if (coincidencia<0) {
						
						msg="Error al contar las coincidencias de la fecha '"+valores[i][0]+"' en la tabla "+tabla;
						log.WriteMessage(msg, true);
						errores++;
						
					}
				} catch (Exception e) {
					
					// TODO: handle exception
					msg="Error al procesar el registro "+i+" de "+registros;
					log.WriteMessage(msg, true);
					msg="KO "+msg2;
					log.WriteMessage(msg, true);
					msg=e.toString();
					log.WriteMessage(msg, true);
					errores++;
					
				}
			}
			
			sta.close();
			
			msg="Finalizada las transacciones en la tabla "+tabla;
			log.WriteMessage(msg, true);
			msg="    "+insertadas+" registros insertados.";
			log.WriteMessage(msg, true);
			msg="    "+updates+" registros updateados.";
			log.WriteMessage(msg, true);
			msg="    "+errores+" errores.";
			log.WriteMessage(msg, true);
			msg="    "+registros+" Total registros.";
			log.WriteMessage(msg, true);
			
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al procesar las inserciones";
			log.WriteMessage(msg, true);
			msg=e.toString();
			log.WriteMessage(msg, true);
			msg=insertadas+" de "+registros+" filas insertadas antes del error.";
			log.WriteMessage(msg, true);
		}
		
		if(errores==0) {
			return true;
		}else {
			return false;
		}
		
	}

	private void rollBack() {
		
		String msg="Realizando rollback...";
		log.WriteMessage(msg,true);
		try {
			conexion.rollback();
			msg="Rollback realizado.";
			log.WriteMessage(msg,true);
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al realizar el rollback.";
			log.WriteMessage(msg,true);
		}
		
	}
	
	private void commit() {
		
		String msg="Realizando commit...";
		log.WriteMessage(msg,true);
		try {
			conexion.commit();
			msg="commit realizado.";
			log.WriteMessage(msg,true);
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al realizar el commit.";
			log.WriteMessage(msg,true);
		}
		
	}
	
	private boolean modificaParametroLlamada(String idParam, String valorNuevo) {
		String msg="Modificando parametro "+idParam+" valor nuevo "+valorNuevo;
		log.WriteMessage(msg,true);
		try {
			
			CallableStatement stat=conexion.prepareCall("{call IA_APP.MODIFICA_PARAM_ESCENARIO(?,?,?,?,?)}");
			stat.setInt(1, Integer.parseInt(IDEscenario));
			stat.setInt(2, Integer.parseInt(idParam));
			stat.setString(3, valorNuevo);
			stat.registerOutParameter(4, java.sql.Types.INTEGER);
			stat.registerOutParameter(5, java.sql.Types.VARCHAR);
			stat.executeUpdate();
			int res=stat.getInt(4);
			String resStr=stat.getString(5);
			
			msg=String.valueOf(res)+" - "+resStr;
			log.WriteMessage(msg,true);
			
			if (res==1) {
				msg="Se actualizo el parametro a '"+valorNuevo+"'";
				log.WriteMessage(msg,true);
				return true;
			}else if (res==0) {
				msg="No se actualizo el parametro '"+idParam+"'";
				log.WriteMessage(msg,true);
				return false;
			}else if(res==-1) {
				msg="Error al procesar el procedimiento. Param: '"+idParam+"'";
				log.WriteError(msg,true);
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al actualizar el valor";
			log.WriteMessage(msg,true);
			return false;
		}
		return false;
	}

	private boolean insertaAutocorrelacion(String campo, int autocorr) {
		String msg="Insertando autocorrelacion "+autocorr+" en el campo "+campo+" escenario "+IDEscenario;
		log.WriteMessage(msg, true);
		try {
			CallableStatement stat=conexion.prepareCall("{call IA_APP.MODIFICA_AUTOCORRELACION(?,?,?,?,?)}");
			stat.setInt(1, Integer.parseInt(IDEscenario));
			stat.setString(2, campo);
			stat.setInt(3, autocorr);
			stat.registerOutParameter(4, java.sql.Types.INTEGER);
			stat.registerOutParameter(5, java.sql.Types.VARCHAR);
			stat.executeUpdate();
			int res=stat.getInt(4);
			String resStr=stat.getString(5);
			msg=resStr;
			log.WriteMessage(msg, true);
			
			if(res<1) {
				return false;
			}else {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al insertar autocorrelacion "+autocorr+" en el campo "+campo+" escenario "+IDEscenario;
			log.WriteMessage(msg, true);
			return false;
		}
		
	}

	private static String getParamEscenario(String idesc, String idparam, Statement sta) {
		
		try {
			String q="SELECT a.VALOR FROM T_CONFIG_ESCENARIOS a WHERE a.ID_ESCENARIO = "+idesc+" AND a.ID_PARAM = "+idparam;
			String res="";
			ResultSet resu=sta.executeQuery(q);
			while(resu.next()) {
				res=resu.getString(1);
			}
			resu.close();
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		
	}

	private boolean actualiza_total_columnas() {
		String msg="Actualizando el total de columnas de inputs";
		log.WriteMessage(msg,true);
		try {
			CallableStatement stat=conexion.prepareCall("{call IA_APP.ACTUALIZA_TOTAL_COLULMAS(?,?,?)}");
			stat.setInt(1, Integer.parseInt(IDEscenario));
			stat.registerOutParameter(2, java.sql.Types.INTEGER);
			stat.registerOutParameter(3, java.sql.Types.VARCHAR);
			stat.executeUpdate();
			int res=stat.getInt(2);
			String resStr=stat.getString(3);
			msg=res+" "+resStr;
			log.WriteMessage(msg,true);
			stat.close();
			if (res==0) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="Error al actualizar el total de columnas";
			log.WriteMessage(msg,true);
			msg=e.toString();
			log.WriteMessage(msg,true);
			return false;
		}
		
	}
	
	
	/*
	private int cuentaAutocorrelaciones(String campo, Statement sta) {
		
		try {
			int cuenta=0;
			String q="SELECT COUNT(*) FROM T_ESCENARIOS_AUTOCORRELACION a WHERE a.ID_ESCENARIO = "+IDEscenario+" AND a.CAMPO = '"+campo+"'";
			ResultSet res=sta.executeQuery(q);
			while(res.next()) {
				cuenta=res.getInt(1);
			}
			return cuenta;
		}catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}*/
}
