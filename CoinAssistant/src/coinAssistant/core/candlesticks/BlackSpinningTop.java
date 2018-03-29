package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class BlackSpinningTop extends Pattern {
	static private Color patternColor=Color.cyan;
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		if (index < 2)
			return false;
		CandleStick first = data.get(index);
		CandleStick firstBefore = data.get(index - 1);
		CandleStick secondBefore = data.get(index - 2); // doit s'inscrire dans une tendance haussière
		return firstBefore.isAscend() &&
				secondBefore.isAscend() &&
				!first.isAscend() &&
				first.getHigh() > firstBefore.getHigh() &&
				first.getLowerShadowSize() > 2 * first.getBodySize() &&
				first.getUpperShadowSize() > 2 * first.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "Le Sommet Noir en Toupie";}
	
	@Override
	public String getDescription() {
		return "Structure possédant un petit corps noir, deux ombres dont "
				+ "chacune est plus longue que le corps, et un plus haut supérieur à celui "
				+ "du chandelier antérieur.";
	} 
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return -0.5;
	}

	@Override
	public String getInterpretationText() {
		return "C'est une ligne de chandelier unique qui apparait après une tendance haussière. "
				+ "Ce pattern indique qu'il y a une indécision entre acheteurs et vendeurs et est considéré"
				+ "comme baissier si la séane suivante clôture à un plus bat niveau." ;
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new BlackSpinningTop(data, rank);
	}
	public BlackSpinningTop()
	{
	}
	
	public BlackSpinningTop(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}

}
