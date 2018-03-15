package coinAssistant.core.candlesticks;

import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Engulfing extends Pattern {
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
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
		return false;
	}
	@Override
	public int getPatternSize() {
		return 1;
	}

}
