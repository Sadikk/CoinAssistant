package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Engulfing extends Pattern {
	static private Color patternColor=Color.magenta;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		if (index > 1)
		{
			CandleStick first = data.get(index);
			CandleStick second = data.get(index-1);
			if (first.getClose()>second.getHigh() && first.getOpen()<second.getLow())
			{
				return first.isAscend()
						&& !second.isAscend();
			}
			else if (first.getClose()<second.getLow() && first.getOpen()>second.getHigh())
			{
				return !first.isAscend() 
						&& second.isAscend();
			}
		}
		return false;
	}
	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "L'avalement";}
	
	@Override
	public String getDescription() {
		return "Un avalement est composé d’un petit chandelier suivie d’un grand chandelier "
				+ "qui doit l’englober dans sa totalité. Quel que soit le mouvement de marché, "
				+ "il doit être composé de deux chandelier de couleurs différentes.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

}
