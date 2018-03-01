package coinAssistant.core;
import java.util.ArrayList;

public class CandleStick {

	public double start;
	public double end;
	public double mini;
	public double maxi;
	
	public CandleStick(ArrayList<Double>data) {
		this.start=data.get(0);
		this.end=data.get(data.size()-1);
		this.mini=data.get(0);
		this.maxi=data.get(0);
		for(double i:data) {
			if(i<mini) {
				mini=i;
			}
			if(i>maxi) {
				maxi=i;
			}
		}
	}
	
}
