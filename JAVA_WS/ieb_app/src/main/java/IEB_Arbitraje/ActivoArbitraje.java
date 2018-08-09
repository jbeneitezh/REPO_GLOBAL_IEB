package IEB_Arbitraje;

import java.text.SimpleDateFormat;
import java.util.Date;

import DateAndTime.FechaSimple;
import IEB.OpcionCls;
import config.InfoIEB;

public class ActivoArbitraje {
	
	private double minPercStrike;
	private double maxPercStrike;
	
	private String activo="";
	private int id_activo;
	private double multiplicador;
	private Date fValora;
	private Date []vencimientos;
	private double []t;
	private double spot=0;
	private double []tasasR;
	private double [][]volatilidades;
	private double [][]betas;
	
	OpcionCls opcionesCall[][];
	OpcionCls opcionesPut[][];
	
	private double [][]matPricingCall;
	private double [][]matPricingPut;
	private double [][]matPricingPercCall;
	private double [][]matPricingPercPut;
	private double []  matStrikes;
	private double []  matPercStrikes;
	
	private String msgArbi="";
	private boolean oportunidades=false;
	
	private int MaxVtos;
	
	public ActivoArbitraje(String [][]matDatos) {
		///////////////////////PARAMETRIZAR MAX, MIN PERC STRIKE y MAXVTOS////////////////////////////
		minPercStrike=0.95;
		maxPercStrike=1.05;
		MaxVtos=3;
		activo=matDatos[1][0];
		multiplicador=Double.parseDouble(matDatos[1][1]);
		id_activo=Integer.parseInt(matDatos[1][2]);
		String fStr=matDatos[1][3];
		String vtosStr []=new String[matDatos.length-1];
		for (int i=0;i<vtosStr.length;i++) {
			vtosStr[i]=matDatos[i+1][4];
		}
		spot=Double.parseDouble(matDatos[1][5]);
		volatilidades=new double[matDatos.length-1][3];
		for(int i=0;i<volatilidades.length;i++) {
			volatilidades[i][0]=Double.parseDouble(matDatos[i+1][8])/100;//Columna 0: Vol 90%
			volatilidades[i][1]=Double.parseDouble(matDatos[i+1][10])/100;//Columna 1: Vol 100%
			volatilidades[i][2]=Double.parseDouble(matDatos[i+1][12])/100;//Columna 2: Vol 110%
		}
		tasasR=new double[matDatos.length-1];
		for(int i=0;i<tasasR.length;i++) {
			tasasR[i]=Double.parseDouble(matDatos[i+1][15])/100;
		}
		
		betas=new double[matDatos.length-1][2];
		for(int i=0;i<betas.length;i++) {
			betas[i][0]=Double.parseDouble(matDatos[i+1][17]);//Columna 0: Beta90100
			betas[i][1]=Double.parseDouble(matDatos[i+1][18]);//Columna 1: Beta100110
		}
		//tratamiento de fechas
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			fValora=dateFormat.parse(fStr);
			vencimientos=new Date[vtosStr.length];
			t=new double[vtosStr.length];
			for(int i=0;i<vencimientos.length;i++) {
				vencimientos[i]=dateFormat.parse(vtosStr[i]);
				t[i]=FechaSimple.diferenciaAnhosFechas(fValora, vencimientos[i], InfoIEB.DiasBaseAnho());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ActivoArbitraje::ActivoArbitraje::ERROR::No se pudo tratar las fechas. \n"+e.toString());
		}		
		/*
		System.out.println("activo: "+activo);
		System.out.println("id activo: "+id_activo);
		System.out.println("multiplicador: "+multiplicador);
		System.out.println("fStr: "+fStr);
		System.out.println("fValora: "+fValora);
		System.out.println("spot: "+spot);
		for (int i=0;i<vtosStr.length;i++) {
			System.out.println("vtoStr["+i+"] - "+vtosStr[i]);
		}
		for(int i=0;i<volatilidades.length;i++) {
			String volMsg="volatilidades: ";
			for(int j=0;j<volatilidades[i].length;j++) {
				volMsg+=" - ["+i+"]["+j+"]: "+volatilidades[i][j];
			}
			System.out.println(volMsg);
		}
		for(int i=0;i<betas.length;i++) {
			String betasMsg="betas: ";
			for(int j=0;j<betas[i].length;j++) {
				betasMsg+=" - ["+i+"]["+j+"]: "+betas[i][j];
			}
			System.out.println(betasMsg);
		}
		for (int i=0;i<tasasR.length;i++) {
			System.out.println("tasasR["+i+"] - "+tasasR[i]);
		}
		for (int i=0;i<vencimientos.length;i++) {
			System.out.println("vencimientos["+i+"] - "+vencimientos[i]+" - t["+i+"]: "+t[i]);
		}
		System.out.println("");
		*/
	}
	
	public void getMatPricing() {
		matPricingCall=new double[401][vencimientos.length];
		matPricingPut=new double[401][vencimientos.length];
		matPricingPercCall=new double[401][vencimientos.length];
		matPricingPercPut=new double[401][vencimientos.length];
		matStrikes=new double[401];
		matPercStrikes=new double[401];
		opcionesCall=new OpcionCls[401][vencimientos.length];
		opcionesPut=new OpcionCls[401][vencimientos.length];
		for(int i=0;i<matPricingCall.length;i++) {
			double percStrike=0.90+i*0.0005;
			double Strike=percStrike*spot;
			
			matPercStrikes[i]=percStrike;
			matStrikes[i]=Strike;
			
			//System.out.println(i+" - percStrike: "+percStrike+" - Strike: "+Strike);
			for(int j=0;j<vencimientos.length;j++) {
				double vola=0;
				if (percStrike>=1) {
					vola=volatilidades[j][1]+(percStrike-1)*betas[j][1]/100;
				}else if (percStrike<1) {
					vola=volatilidades[j][0]+(percStrike-0.9)*betas[j][0]/100;
				}
				
				opcionesCall[i][j]=new OpcionCls(activo,
												 "call", 
												 vencimientos[j], 
												 fValora, 
												 tasasR[j], 
												 0.0, 
												 spot, 
												 Strike, 
												 vola);
				matPricingCall[i][j]=opcionesCall[i][j].getPrecio();
				matPricingPercCall[i][j]=matPricingCall[i][j]/spot;
				opcionesPut[i][j] =new OpcionCls(activo,
												 "put", 
												 vencimientos[j], 
												 fValora, 
												 tasasR[j], 
												 0.0, 
												 spot, 
												 Strike, 
												 vola);
				matPricingPut[i][j]=opcionesPut[i][j].getPrecio();
				matPricingPercPut[i][j]=matPricingPut[i][j]/spot;
			}
		}
		/*
		System.out.println("\nImprimiendo matriz call:");
		for(int k=0;k<matPricingCall.length;k++) {
			String msgOp="";
			for(int h=0;h<matPricingCall[0].length;h++) {
				//msgOp+=" - ["+k+"]["+h+"]: "+matPricingCall[k][h];
				msgOp+=matPricingCall[k][h]+";";
			}
			System.out.println(msgOp);
		}
		System.out.println("\nImprimiendo matriz put:");
		for(int k=0;k<matPricingPut.length;k++) {
			String msgOp="";
			for(int h=0;h<matPricingPut[0].length;h++) {
				//msgOp+=" - ["+k+"]["+h+"]: "+matPricingPut[k][h];
				msgOp+=matPricingPut[k][h]+";";
			}
			System.out.println(msgOp);
		}*/
	}
	
	public void arbitrajeAnalisis() {
		oportunidades=false;
		msgArbi="Analizando arbitraje para el activo: "+activo+" - "+id_activo+"\n\n";
		arbitrajeDirectoPrecio();
		if(oportunidades) {
			System.out.println(msgArbi);
		}
	}
	
	private void arbitrajeDirectoPrecio() {
		
		//Analizando precios entre filas
		for(int i=1;i<matPricingCall.length;i++) {
			if(matPercStrikes[i]>=minPercStrike && matPercStrikes[i]<=maxPercStrike) {
				for(int j=0;j<matPricingCall[i].length;j++) {
					if(j<MaxVtos) { //Comprobamos que no nos pasemos de los 3 vencimientos
						if(matPricingCall[i][j]>=matPricingCall[i-1][j]) {
							oportunidades=true;
							msgArbi+="Arbitraje dentro del vencimiento: "+vencimientos[j]+" - "+activo+"\n"
									+"   Compra Call "+matStrikes[i-1]+" ("+(matPercStrikes[i-1]*100)+"% - Precio: "+matPricingCall[i-1][j]+"\n"
									+"   Venta  Call "+matStrikes[i]+" ("+(matPercStrikes[i]*100)+"% - Precio: "+matPricingCall[i][j]+"\n"
									+"\n";
						}
					}
				}
			}
		}
		
		for(int i=1;i<matPricingPut.length;i++) {
			if(matPercStrikes[i]>=minPercStrike && matPercStrikes[i]<=maxPercStrike) {
				for(int j=0;j<matPricingPut[i].length;j++) {
					if(j<MaxVtos) { //Comprobamos que no nos pasemos de los 3 vencimientos
						if(matPricingPut[i][j]<=matPricingPut[i-1][j]) {
							oportunidades=true;
							msgArbi+="Arbitraje dentro del vencimiento: "+vencimientos[j]+" - "+activo+"\n"
									+"   Compra Put  "+matStrikes[i-1]+" ("+(matPercStrikes[i-1]*100)+"% - Precio: "+matPricingPut[i-1][j]+"\n"
									+"   Venta  Put  "+matStrikes[i]+" ("+(matPercStrikes[i]*100)+"% - Precio: "+matPricingPut[i][j]+"\n"
									+"\n";
						}
					}
				}
			}
		}
		
		//Analizando precios entre vencimientos
		for(int j=1;j<matPricingCall[0].length;j++) {
			if(j<MaxVtos) { //Comprobamos que no nos pasemos de los 3 vencimientos
				for(int i=0;i<matPricingCall.length;i++) {
					if(matPercStrikes[i]>=minPercStrike && matPercStrikes[i]<=maxPercStrike) {	
						if(matPricingCall[i][j]<matPricingCall[i][j-1]) {
							oportunidades=true;
							msgArbi+="Arbitraje entre los vencimientos en el strike: "+matStrikes[i]+" ("+(matPercStrikes[i]*100)+") - "+activo+"\n"
									+"   Venta  Call "+vencimientos[j-1]+" - Precio: "+matPricingCall[i][j-1]+"\n"
									+"   Compra Call "+vencimientos[j]  +" - Precio: "+matPricingCall[i][j]  +"\n"
									+"\n";
						}
					}
				}
			}
		}
		for(int j=1;j<matPricingPut[0].length;j++) {
			if(j<MaxVtos) { //Comprobamos que no nos pasemos de los 3 vencimientos
				for(int i=0;i<matPricingPut.length;i++) {
					if(matPercStrikes[i]>=minPercStrike && matPercStrikes[i]<=maxPercStrike) {
						if(matPricingPut[i][j]<matPricingPut[i][j-1]) {
							oportunidades=true;
							msgArbi+="Arbitraje entre los vencimientos en el strike: "+matStrikes[i]+" ("+(matPercStrikes[i]*100)+") - "+activo+"\n"
									+"   Venta  Put "+vencimientos[j-1]+" - Precio: "+matPricingPut[i][j-1]+"\n"
									+"   Compra Put "+vencimientos[j]  +" - Precio: "+matPricingPut[i][j]  +"\n"
									+"\n";
						}
					}
				}
			}
		}
		
		//Análisis de betas
		for(int i=0;i<betas.length;i++) {
			if(betas[i][0]>0 && betas[i][1]<0) {
				msgArbi+="Volatilidad es máxima en ATM - "+activo+" - "+vencimientos[i]+"\n";
				oportunidades=true;
			}else if(betas[i][0]<0 && betas[i][1]>0) {
				msgArbi+="Volatilidad es mínima en ATM - "+activo+" - "+vencimientos[i]+"\n";
				oportunidades=true;
			}
		}
		
		//Análisis de cóndors
		int centro=(matPricingPut.length-1)/2;
		for(int j=0;j<matPricingPut[0].length;j++) {
			if(j<MaxVtos) { //Comprobamos que no nos pasemos de los 3 vencimientos
				double maximo=0;
				String msgMaxim="";
				for(int i=0;i<centro-2;i++) {
					for(int k=i+1;k<centro;k++) {
						double ingreso=matPricingPercCall[centro+i][j]+matPricingPercPut[centro-i][j];
						double gasto=matPricingPercCall[centro+k][j]+matPricingPercPut[centro-k][j];
						double exposicion=Math.max(matPercStrikes[centro-i]-matPercStrikes[centro-k], matPercStrikes[centro+k]-matPercStrikes[centro+i]);
						double perdidapotencial=ingreso-gasto-exposicion;
						double embolsado=ingreso-gasto;
						if(perdidapotencial>maximo) {
							oportunidades=true;
							msgMaxim="Arbitraje Condor "+vencimientos[j].toString()+" - Pérdida max: "+perdidapotencial*100+"% - Ingreso: "+embolsado*100+"% \n"
								    +"    +Put  "+matStrikes[centro-k]+" -"+matPricingPercPut[centro-k][j]*100+"% \n"
								    +"    -Put  "+matStrikes[centro-i]+" +"+matPricingPercPut[centro-i][j]*100+"% \n"
								    +"    -Call "+matStrikes[centro+i]+" +"+matPricingPercCall[centro+i][j]*100+"% \n"
								    +"    +Call "+matStrikes[centro+k]+" -"+matPricingPercCall[centro+k][j]*100+"% \n\n";
						}
					}
				}
				msgArbi+=msgMaxim;
			}
		}
				
	}
}
