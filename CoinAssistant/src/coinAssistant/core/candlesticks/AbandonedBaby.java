package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class AbandonedBaby extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.blue;
	public AbandonedBaby()
	{
		
	}
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		// deux parents au dessus du milieu
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (first.getLow()>second.getHigh()//parent avant
		&& third.getLow()>second.getHigh()); //parent apr�s
			
		
	
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "Le B�b� abandonn�";}
	
	@Override
	public String getDescription() {
		return ("B�b� abandonn� intervient soir apr�s une phase haussi�re ou une pahe baissi�re."
				+ " C'est une figure d'arret qui est form� de 3 chandeliers");
	}
	
	@Override
	public Color getColor(){return patternColor;}
}
