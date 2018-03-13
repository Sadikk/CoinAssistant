package coinAssistant.core.candlesticks;

import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class ShootingStar extends Pattern {

	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick candle = data.get(index);
		return candle.getUpperShadowSize() > 3 * candle.getBodySize()
				&& candle.getLowerShadowSize() < 0.5 * candle.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}

}
