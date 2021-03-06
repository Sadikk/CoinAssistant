package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class AbandonedBaby extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.blue;
		
	public AbandonedBaby()
	{
	}
	
	public AbandonedBaby(ArrayList<CandleStick> dat, int r)
	{
		this.data = dat;
		this.rank = r;
	}
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		// deux parents au dessus du milieu
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (first.getLow()>second.getHigh() && third.getLow()>second.getHigh()) //morning baby
				|| (first.getHigh() < second.getLow() && third.getHigh()> second.getLow())
				&& first.getBodySize() > 10 * second.getBodySize()
				&& third.getBodySize() > 10 * second.getBodySize(); //evening baby
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "Le B�b� abandonn�";}
	
	@Override
	public String getDescription() {
		return ("B�b� abandonn� intervient soir apr�s une phase haussi�re ou une phase baissi�re."
				+ " C'est une figure rare d'arret qui est form� de 3 chandeliers");
	}
	
	@Override
	public Color getColor(){return patternColor;}
	
	@Override
	public double getInterpretation() {
		CandleStick first=data.get(rank);
		CandleStick second=data.get(rank+1);
		CandleStick third=data.get(rank+2);
		
		if (first.isAscend())
			return -1;
		else
			return 1;
	}
	@Override
	public String getInterpretationText() {
		return "Le b�b� abandonn� est une figure de retournement, elle indique un "
		+ "retournement � la hausse (resp. � la baisse). Cela traduit d'un tour de force "
		+ "des acheteurs pour contenir la pression baissi�re (resp. haussi�re) puis pour "
		+ "faire capituler les vendeurs (resp. les acheteurs) dans un deuxi�me temps.";
	}
	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new AbandonedBaby(data, rank);
	}
}
