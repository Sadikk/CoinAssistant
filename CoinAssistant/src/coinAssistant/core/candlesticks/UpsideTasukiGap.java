package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;
import coinAssistant.core.*;

public class UpsideTasukiGap extends Pattern{
	static private Color patternColor=new Color(93,242,23);
	static private int taillePattern=3;
	public UpsideTasukiGap() {}
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (first.isAscend() //first up
		&& first.getClose()<second.getOpen() //gap up
		&& second.isAscend()// second up
		&& !third.isAscend() //third down
		&& third.getOpen()>second.getOpen() //third opens in body of second
		&& third.getClose()<second.getOpen() //third closes in the gap up
		&& third.getClose()>first.getClose());
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "UpsideTasukiGap";}
	
	@Override
	public Color getColor(){return patternColor;}
}

//source : https://hitandruncandlesticks.com/downside-tasuki-gap/