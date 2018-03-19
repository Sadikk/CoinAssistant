package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Twin extends Pattern {
	static private Color patternColor=new Color(45,152,86);
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick first = data.get(index);
		CandleStick second = data.get(index+1);
		CandleStick third = data.get(index+2);
		
		if (first.isAscend())
		{
			return second.getLow() > first.getHigh()
					&& second.isAscend()
					&& third.isAscend()
					&& third.getBodySize() > 0.75 * second.getBodySize()
					&& third.getBodySize() < 1.25 * second.getBodySize();
		}
		else
		{
			return second.getHigh() < first.getLow()
					&& second.isAscend()
					&& third.isAscend()
					&& third.getBodySize() > 0.75 * second.getBodySize()
					&& third.getBodySize() < 1.25 * second.getBodySize();
		}
	}

	@Override
	public int getPatternSize() {
		return 3;
	}
	
	@Override
	public String getName() {return "Les jumeaux blancs";}
	
	@Override
	public String getDescription() {
		return "Les jumeaux blancs est une structure formée de trois chandeliers japonais. "
				+ "Le premier est un chandelier haussier suivi de deux autres chandeliers "
				+ "haussiers après un gap haussier. Les deux derniers chandeliers ouvrent et "
				+ "clôturent approximativement au même niveau.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

}
