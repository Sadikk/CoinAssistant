package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Twin extends Pattern {
	static private Color patternColor=new Color(45,152,86);
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick first = data.get(index);
		CandleStick second = data.get(index+1);
		CandleStick third = data.get(index+2);
		
		if (first.isAscend())
		{
			return second.getLow() > first.getHigh()
					&& second.isAscend()
					&& third.isAscend()
					&& third.getBodySize() > 0.75 * second.getBodySize()
					&& third.getBodySize() < 1.25 * second.getBodySize();
		}
		else
		{
			return second.getHigh() < first.getLow()
					&& second.isAscend()
					&& third.isAscend()
					&& third.getBodySize() > 0.75 * second.getBodySize()
					&& third.getBodySize() < 1.25 * second.getBodySize();
		}
	}

	@Override
	public int getPatternSize() {
		return 3;
	}
	
	@Override
	public String getName() {return "Les jumeaux blancs";}
	
	@Override
	public String getDescription() {
		return "Les jumeaux blancs est une structure form�e de trois chandeliers japonais. "
				+ "Le premier est un chandelier haussier (resp. baissier) suivi de deux autres chandeliers "
				+ "haussiers (resp. baissiers) apr�s un gap haussier (resp. descendant). Les deux derniers "
				+ "chandeliers ouvrent et cl�turent approximativement au m�me niveau.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return data.get(rank).isAscend() ? 1 : -1;
	}

	@Override
	public String getInterpretationText() {
		return "Ce pattern est consid�r� comme haussier s'il apparait � la suie d'un gap ascendant"
				+ "dans un mouvement haussier. A l'inverse, les jumeaux blancs sont consid�r�s comme "
				+ "baissiers lorqu'ils apparaissent � la suite d'un gap descendant, au sein d'un mouvement"
				+ " baissier.";
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new Twin(data, rank);
	}
	
	public Twin()
	{
	}
	
	public Twin(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}

}
