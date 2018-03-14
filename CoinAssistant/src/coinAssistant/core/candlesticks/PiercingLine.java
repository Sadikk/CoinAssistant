package coinAssistant.core.candlesticks;

import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class PiercingLine extends Pattern {

	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick first = data.get(index);
		CandleStick second = data.get(index+1);
		return !first.isAscend() 
				&& second.isAscend()
				&& second.getOpen() < first.getLow()
				&& second.getClose() > (first.getClose() + first.getBodySize() / 2);
	}

	@Override
	public int getPatternSize() {
		return 2;
	}

}
