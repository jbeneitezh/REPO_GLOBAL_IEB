package IEB;

import java.util.*;

import DateAndTime.FechaSimple;
import config.InfoIEB;

public class OpcionCls {
	
	private String nombre="";
	private String tipo="";
	private Date   vencimiento;
	private Date   fValora;
	private double r;
	private double y;
	private double spot;
	private double strike;
	private double volatilidad;
	private double t;
	private double precio;
	private double volaSimula;
	private double rSimula;
	private double ySimula;
	private double spotSimula;
	private Date   fSimula;
	private double precioSimula;
	private double tSimula;
	
	public OpcionCls(String nombreSubyacente, String Tipo, Date vto, Date fvaloracion, double tasaR, double tasaY, double Spot, double Strike, double vola) {
		nombre=nombreSubyacente; 
		tipo=Tipo;
		vencimiento=vto;
		fValora=fvaloracion;    
		r=tasaR;          
		y=tasaY;          
		spot=Spot;       
		strike=Strike;     
		volatilidad=vola;       
		t=FechaSimple.diferenciaAnhosFechas(fValora, vencimiento, InfoIEB.DiasBaseAnho());   
		precio=IEB_OptionValuation.PriceGarmanKohlhagen(tipo, spot, strike, r, y, t, volatilidad);
		
		volaSimula=volatilidad;
		rSimula=r;
		ySimula=y;
		spotSimula=spot;
		fSimula=fValora;
		precioSimula=precio;
		
		
	}
	
	public void simulaOpcion() {
		tSimula=diferenciaAnhos(fSimula, vencimiento);
		precioSimula=IEB_OptionValuation.PriceGarmanKohlhagen(tipo, spotSimula, strike, rSimula, ySimula, tSimula, volaSimula);
	}
	
	private static double diferenciaAnhos(Date ini, Date fin) {
		/*
		double fecIniDbl=(double) (ini.getTime());
		double fecFinDbl=(double) (fin.getTime());
		*/
		double dif=(double) (fin.getTime()-ini.getTime());
		double anhos=(dif)/(86400000*365);
		return anhos;
	}
	

	public double getVolaSimula() {
		return volaSimula;
	}

	public void setVolaSimula(double volaSimula) {
		this.volaSimula = volaSimula;
	}

	public double getrSimula() {
		return rSimula;
	}

	public void setrSimula(double rSimula) {
		this.rSimula = rSimula;
	}

	public double getySimula() {
		return ySimula;
	}

	public void setySimula(double ySimula) {
		this.ySimula = ySimula;
	}

	public double getSpotSimula() {
		return spotSimula;
	}

	public void setSpotSimula(double spotSimula) {
		this.spotSimula = spotSimula;
	}

	public Date getfSimula() {
		return fSimula;
	}

	public void setfSimula(Date fSimula) {
		this.fSimula = fSimula;
	}

	public double gettSimula() {
		return tSimula;
	}

	public void settSimula(double tSimula) {
		this.tSimula = tSimula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public Date getfValora() {
		return fValora;
	}

	public double getR() {
		return r;
	}

	public double getY() {
		return y;
	}

	public double getSpot() {
		return spot;
	}

	public double getStrike() {
		return strike;
	}

	public double getVolatilidad() {
		return volatilidad;
	}

	public double getT() {
		return t;
	}

	public double getPrecio() {
		return precio;
	}

	public double getPrecioSimula() {
		return precioSimula;
	}
	
}
