package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class ShootingStar extends Pattern {
	static private Color patternColor=Color.yellow;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		CandleStick candle = data.get(index);
		return candle.getUpperShadowSize() > 3 * candle.getBodySize()
				&& candle.getLowerShadowSize() < 0.5 * candle.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "Etoile Filante";}
	
	@Override
	public String getDescription() {
		return "L'étoile filante est une structure formée d'un seul chandelier japonais. "
				+ "Le chandelier dispose d'un petit corps, haussier ou baissier, avec une "
				+ "grande mèche haute dont la taille doit être au moins égale à deux fois le "
				+ "corps. Le cours de clôture du chandelier suivant doit être inférieur au "
				+ "point bas du l'étoile filante.";
	}
	
	
	@Override
	public Color getColor(){return patternColor;}

}
