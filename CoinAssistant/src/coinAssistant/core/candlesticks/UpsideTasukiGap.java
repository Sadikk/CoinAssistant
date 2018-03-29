package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;
import coinAssistant.core.*;

public class UpsideTasukiGap extends Pattern{
	static private Color patternColor=new Color(93,242,23);
	static private int taillePattern=3;
	
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
	public String getName() {return "Le Gap Tasuki Ascendant";}
	
	@Override
	public String getDescription() {
		return "Structure rare de continuation form�e de trois chandeliers. Dans le cadre "
				+ "d'un march� haussier, le tasuki gap ascendant se forme lorsqu'un chandelier "
				+ "haussier creuse un foss� ascendant au-dessus du haussier pr�c�dent. "
				+ "Cette deuxi�me bougie est suivie d'un chandelier baissier, approximativement "
				+ "de la m�me taille, qui ouvre dans le corps du chandelier pr�c�dent et qui "
				+ "cl�ture ensuite au-dessous de ce corps.";
	}
	
	@Override
	public Color getColor(){return patternColor;}
	@Override
	public double getInterpretation() {
		return 0.75;
	}
	@Override
	public String getInterpretationText() {
		return "C'est une figure de continuation haussi�re. Ce pattern est d'autant plus significatif qu'il"
				+ "constitue le premier gap d'un mouvement haussier";
	}
	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new UpsideTasukiGap(data, rank);
	}
	
	public UpsideTasukiGap()
	{
	}
	
	public UpsideTasukiGap(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}
}

//source : https://hitandruncandlesticks.com/downside-tasuki-gap/