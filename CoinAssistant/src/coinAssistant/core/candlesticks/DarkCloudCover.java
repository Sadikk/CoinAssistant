package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class DarkCloudCover extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.darkGray;
	public DarkCloudCover() {}
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		
		// deux parents au dessus du milieu
		return(first.isAscend()//premier ascendant
		&& !second.isAscend() //second descendant
		&& second.getOpen()>first.getClose() //gap up entre 1 et 2
		&& second.getClose()<(first.getOpen()+first.getClose()/2)); //perte du 2 compense la moitié des gains de 1

	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	@Override
	public Color getColor(){return patternColor;}
}

//source : http://www.onlinetradingconcepts.com/TechnicalAnalysis/Candlesticks/DarkCloudCover.html