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
		return "Le marteau est une structure form�e d'un seul chandelier japonais. "
				+ "Le chandelier dispose d'un petit corps, haussier ou baissier, avec une "
				+ "grande m�che basse dont la taille doit �tre au moins �gale � deux fois "
				+ "le corps. Le cours de cl�ture du chandelier suivant doit �tre sup�rieur "
				+ "au point haut du marteau.";}
	
	
	@Override
	public Color getColor(){return patternColor;}

}
