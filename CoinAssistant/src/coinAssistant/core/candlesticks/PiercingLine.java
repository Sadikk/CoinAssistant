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
	public String getName() {return "La Pénétrante";}
	
	@Override
	public String getDescription() {
		return "La structure en pénétrante haussière est formée de deux chandeliers japonais. "
				+ "Le premier chandelier est un long baissier suivi d'un deuxième grand chandelier "
				+ "haussier. L'ouverture du 2ème chandelier se fait sur un gap baissier et la "
				+ "clôture intervient au dessus du point médian du corps du premier chandelier.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

}
