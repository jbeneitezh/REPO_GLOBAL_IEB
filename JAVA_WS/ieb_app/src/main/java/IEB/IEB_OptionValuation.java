package IEB;


import org.apache.commons.math3.distribution.NormalDistribution;

public class IEB_OptionValuation {
	
	
	
	public static double PriceGarmanKohlhagen(String tipo, double Spot, double Strike, double tasaR, double tasaD, double tiempo, double vola) {
		try {
			double d1=(Math.log(Spot/Strike)+(tasaR-tasaD+Math.pow(vola, 2)/2)*tiempo)/(vola*Math.sqrt(tiempo));
			
			double d2=d1-vola*Math.sqrt(tiempo);
			//System.out.println("d1: "+d1);
			//System.out.println("d2: "+d2);
			NormalDistribution normal=new NormalDistribution();
			/*
			double ND1=normal.cumulativeProbability(d1);
			double ND2=normal.cumulativeProbability(d2);
			System.out.println("Nd1: "+ND1);
			System.out.println("Nd2: "+ND2);
			*/
			double valorPrima=0;
			if(tipo=="call") {
				valorPrima=Spot*Math.exp(-tasaD*tiempo)*normal.cumulativeProbability(d1)-Strike*Math.exp(-tasaR*tiempo)*normal.cumulativeProbability(d2);
			}else if (tipo=="put") {
				valorPrima=Strike*Math.exp(-tasaR*tiempo)*normal.cumulativeProbability(-d2)-Spot*Math.exp(-tasaD*tiempo)*normal.cumulativeProbability(-d1);
			}
			return valorPrima;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_OptionValuation::PriceGarmanKohlhagen::ERROR::No se pudo calcular el valor de la prima\n"+e.toString();
			System.out.println(msg);
			return 0;
		}
	}
	
	public static double DeltaGarmanKohlhagen(String tipo, double Spot, double Strike, double tasaR, double tasaD, double tiempo, double vola) {
		try {
			double d1=(Math.log(Spot/Strike)+(tasaR-tasaD+Math.pow(vola, 2)/2)*tiempo)/(vola*Math.sqrt(tiempo));

			NormalDistribution normal=new NormalDistribution();
			double valorDelta=0;
			
			if(tipo=="call") {
				valorDelta=normal.cumulativeProbability(d1)*Math.exp(-tasaR*tiempo);
			}else if (tipo=="put") {
				valorDelta=-normal.cumulativeProbability(-d1)*Math.exp(-tasaR*tiempo);
			}
			return valorDelta;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_OptionValuation::DeltaGarmanKohlhagen::ERROR::No se pudo calcular el valor de la delta\n"+e.toString();
			System.out.println(msg);
			return 0;
		}
	}
	
	public static double GammaGarmanKohlhagen(double Spot, double Strike, double tasaR, double tasaD, double tiempo, double vola) {
		try {
			double d1=(Math.log(Spot/Strike)+(tasaR-tasaD+Math.pow(vola, 2)/2)*tiempo)/(vola*Math.sqrt(tiempo));
			double valorGamma=NPrimaX(d1)*Math.exp(-tasaR*tiempo)/(Spot*vola*Math.sqrt(tiempo));
			return valorGamma;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_OptionValuation::GammaGarmanKohlhagen::ERROR::No se pudo calcular el valor de la gamma\n"+e.toString();
			System.out.println(msg);
			return 0;
		}
	}
	
	public static double VegaGarmanKohlhagen(double Spot, double Strike, double tasaR, double tasaD, double tiempo, double vola) {
		try {
			double d1=(Math.log(Spot/Strike)+(tasaR-tasaD+Math.pow(vola, 2)/2)*tiempo)/(vola*Math.sqrt(tiempo));
			double valorVega=Spot*Math.exp(-tasaR*tiempo)*NPrimaX(d1)*Math.sqrt(tiempo);
			return valorVega;
		} catch (Exception e) {
			// TODO: handle exception
			String msg="IEB_OptionValuation::GammaGarmanKohlhagen::ERROR::No se pudo calcular el valor de la gamma\n"+e.toString();
			System.out.println(msg);
			return 0;
		}
	}
	
	private static double NPrimaX(double x) {
		return Math.exp(-0.5*Math.pow(x, 2))/(Math.sqrt(2*Math.PI));
	}
}
