package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class ShootingStar extends Pattern {
	static private Color patternColor=Color.yellow;
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
	@Override
	public Color getColor(){return patternColor;}

}
