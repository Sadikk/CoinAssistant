package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Hammer extends Pattern {
	static private Color patternColor=Color.lightGray;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick candle = data.get(index);
		return !candle.isAscend() 
				&& candle.getLowerShadowSize() > 3 * candle.getBodySize()
				&& candle.getUpperShadowSize() < 0.5 * candle.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "Le marteau";}
	
	@Override
	public String getDescription() {
		return "Le marteau est une structure formée d'un seul chandelier japonais. "
				+ "Le chandelier dispose d'un petit corps, haussier ou baissier, avec une "
				+ "grande mèche basse dont la taille doit être au moins égale à deux fois "
				+ "le corps. Le cours de clôture du chandelier suivant doit être supérieur "
				+ "au point haut du marteau.";}
	
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return 1;
	}

	@Override
	public String getInterpretationText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new Hammer(data, rank);
	}
	
	public Hammer()
	{
	}
	
	public Hammer(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}

}
