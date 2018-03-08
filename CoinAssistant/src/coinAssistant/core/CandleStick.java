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
	
	
	public double getOpen() {return this.open;}
	public double getClose() {return this.close;}
	public double getLow() {return this.low;}
	public double getHigh() {return this.high;}
	
	public void setPattern(Pattern p) {
		motif=p;
	}
	
	
}
