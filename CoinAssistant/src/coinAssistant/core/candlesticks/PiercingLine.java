package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class PiercingLine extends Pattern {
	static private Color patternColor=Color.red;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick first = data.get(index);
		CandleStick second = data.get(index+1);
		return !first.isAscend() 
				&& second.isAscend()
				&& second.getOpen() < first.getLow()
				&& second.getClose() > (first.getClose() + first.getBodySize() / 2);
	}

	@Override
	public int getPatternSize() {
		return 2;
	}
	
	@Override
	public String getName() {return "La P�n�trante";}
	
	@Override
	public String getDescription() {
		return "La structure en p�n�trante baissi�re (resp. haussi�re) est form�e de deux chandeliers "
				+ "japonais. Le premier chandelier est un long haussier (resp. baissier) suivi d'un "
				+ "deuxi�me grand chandelier baissier (resp. haussier). L'ouverture du 2�me chandelier "
				+ "se fait sur un gap haussier (resp. baissier) et la cl�ture intervient en dessous "
				+ "(resp. au dessus) du point m�dian du corps du premier chandelier.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return 0.8;
	}

	@Override
	public String getInterpretationText() {
		return "La p�n�trante baissi�re (resp. haussi�re) est une configuration de retournement de "
				+ "tendance ou une consolidation � la baisse (resp. � la hausse).";
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new PiercingLine(data, rank);
	}
	
	public PiercingLine()
	{
	}
	
	public PiercingLine(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}

}
