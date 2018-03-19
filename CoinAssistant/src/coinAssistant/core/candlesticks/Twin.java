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
	public String getName() {return "Twin";}
	
	@Override
	public Color getColor(){return patternColor;}

}
