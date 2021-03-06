package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Engulfing extends Pattern {
	static private Color patternColor=Color.magenta;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		if (index > 1)
		{
			CandleStick first = data.get(index);
			CandleStick second = data.get(index-1);
			if (first.getClose()>second.getHigh() && first.getOpen()<second.getLow())
			{
				return first.isAscend()
						&& !second.isAscend();
			}
			else if (first.getClose()<second.getLow() && first.getOpen()>second.getHigh())
			{
				return !first.isAscend() 
						&& second.isAscend();
			}
		}
		return false;
	}
	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "L'avalement";}
	
	@Override
	public String getDescription() {
		return "Un avalement est compos� d�un petit chandelier suivie d�un grand chandelier "
				+ "qui doit l�englober dans sa totalit�. Quel que soit le mouvement de march�, "
				+ "il doit �tre compos� de deux chandelier de couleurs diff�rentes.";
	}
	
	@Override
	public Color getColor(){return patternColor;}
	@Override
	public double getInterpretation() {
		return data.get(rank).isAscend() ?  0.5 : -0.5;
	}
	@Override
	public String getInterpretationText() {
		return "L'avalement haussier (resp. baissier) se forme suite � un mouvement baissier (resp. haussier)"
				+ "significatif. En r�gle g�n�rale, vous arrivez � un creux (resp. sommet) du march�.";
	
	}
	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new Engulfing(data, rank);
	}
	
	public Engulfing()
	{
	}
	
	public Engulfing(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}

	
}
