package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class AbandonedBaby extends Pattern{
	static private int taillePattern=3;
	public AbandonedBaby() {}
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		// deux parents au dessus du milieu
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (first.getLow()>second.getHigh()//parent avant
		&& third.getLow()>second.getHigh()); //parent après
			
		
	
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
}
