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
		return "Structure rare de continuation formée de trois chandeliers. Dans le cadre "
				+ "d'un marché haussier, le tasuki gap ascendant se forme lorsqu'un chandelier "
				+ "haussier creuse un fossé ascendant au-dessus du haussier précédent. "
				+ "Cette deuxième bougie est suivie d'un chandelier baissier, approximativement "
				+ "de la même taille, qui ouvre dans le corps du chandelier précédent et qui "
				+ "clôture ensuite au-dessous de ce corps.";
	}
	
	@Override
	public Color getColor(){return patternColor;}
	@Override
	public double getInterpretation() {
		return 0.75;
	}
	@Override
	public String getInterpretationText() {
		return "C'est une figure de continuation haussière. Ce pattern est d'autant plus significatif qu'il"
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