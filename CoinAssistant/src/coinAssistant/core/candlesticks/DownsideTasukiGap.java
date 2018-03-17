package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class DownsideTasukiGap extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.lightGray;
	public DownsideTasukiGap() {}
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (!first.isAscend() //first down
		&& first.getClose()>second.getOpen() //gap down
		&& !second.isAscend()// second down
		&& third.isAscend() //third up
		&& third.getOpen()<second.getOpen() //third opens in body of second
		&& third.getClose()>second.getOpen() //third closes in the gap down
		&& third.getClose()<first.getClose());
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	@Override
	public Color getColor(){return patternColor;}
}

//source : https://hitandruncandlesticks.com/downside-tasuki-gap/