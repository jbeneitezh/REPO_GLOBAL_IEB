package DateAndTime;

import java.util.Calendar;

public class CronometroCLS extends Thread {
	boolean continuar;
	long millisStart = 0;
	public CronometroCLS(int delayedTime){
		super();
		delay=delayedTime;
		/*millisStart = Calendar.getInstance().getTimeInMillis();
		System.out.println("Cronometro inicializado:");
		System.out.println("Tiempo en milisegundos:   "+millisStart);*/
		millisStart=Calendar.getInstance().getTimeInMillis();
		/*try {
			Thread.sleep(delayedTime);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("CronometroCLS::ERROR - No se pudo hacer la pausa al inicializar.");
		}*/
		
	}
	
	private int delay=0;
	private long timeRunning=0;
	
	
	public double getSeconds(){
		return ((double)timeRunning)/1000;
	}
	public long getMiliseconds(){
		return timeRunning;
	}
	
	public void run(){
		continuar=true;
		while (timeRunning<999999999 && continuar) {
			try{
				Thread.sleep(delay);
				//timeRunning+=delay;
				timeRunning=Calendar.getInstance().getTimeInMillis()-millisStart;
				
			}catch(Exception e){
				System.out.println("CronometroCLS::run::No se pudo hacer la pausa");
			}			
		}	
	}
	public void parar(){
		continuar=false;
	}
}
