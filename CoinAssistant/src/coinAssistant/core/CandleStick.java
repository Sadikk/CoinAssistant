package coinAssistant.core;
import java.util.ArrayList;

public class CandleStick {

	private double open;
	private double close;
	private double low; 
	private double high;
	
	public Pattern motif;
	
	public CandleStick(ArrayList<Double>data) {
		//parametres numériques depuis les données
		this.open=data.get(0);
		this.close=data.get(data.size()-1);
		this.low=data.get(0);
		this.high=data.get(0);
		for(double i:data) {
			if(i<low) {
				low=i;
			}
			if(i>high) {
				high=i;
			}
		}
		
		//initialisation du pattern
		motif=null;
	}
	public CandleStick(double o,double c,double l,double h) {
		this.open=o;
		this.close=c;
		this.low=l;
		this.high=h;
	}
	
	public double getOpen() {return this.open;}
	public double getClose() {return this.close;}
	public double getLow() {return this.low;}
	public double getHigh() {return this.high;}
	
	public void setPattern(Pattern p) {
		motif=p;
	}
	
	//renvoie true si le candlestick est ascendant
	public boolean isAscend() {
		if(this.close>this.open) {return true;}
		return false;
	}
	
	//renvoie la taille du corps
	public double tailleCorps() {
		return Math.abs(open-close);
	}
	//renvoie le plus bas point {open;close}	
	public double getMinCorps() {
		return Math.min(open, close);
	}
	//renvoie le plus haut point {open;close}
	public double getMaxCorps() {
		return Math.max(open,close);
	}
	
}
