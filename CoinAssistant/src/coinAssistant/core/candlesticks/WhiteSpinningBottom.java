package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class WhiteSpinningBottom extends Pattern {
	static private Color patternColor=new Color(182,12,223);
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		if (index < 2)
			return false;
		CandleStick first = data.get(index);
		CandleStick firstBefore = data.get(index - 1);
		CandleStick secondBefore = data.get(index - 2); // doit s'inscrire dans une tendance baissière
		return !firstBefore.isAscend() &&
				!secondBefore.isAscend() &&
				first.isAscend() &&
				first.getLow() > firstBefore.getLow() &&
				first.getLowerShadowSize() > 2 * first.getBodySize() &&
				first.getUpperShadowSize() > 2 * first.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "WhiteSpinningBottom";}
	
	@Override
	public Color getColor(){return patternColor;}

}
