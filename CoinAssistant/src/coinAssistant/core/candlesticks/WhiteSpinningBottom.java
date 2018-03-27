package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class WhiteSpinningBottom extends Pattern {
	static private Color patternColor=new Color(182,12,223);
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int index) {
		if (index < 2)
			return false;
		CandleStick first = data.get(index);
		CandleStick firstBefore = data.get(index - 1);
		CandleStick secondBefore = data.get(index - 2); // doit s'inscrire dans une tendance baissière
		return !firstBefore.isAscend() &&
				!secondBefore.isAscend() &&
				first.isAscend() &&
				first.getLow() > firstBefore.getLow() &&
				first.getLowerShadowSize() > 2 * first.getBodySize() &&
				first.getUpperShadowSize() > 2 * first.getBodySize();
	}

	@Override
	public int getPatternSize() {
		return 1;
	}
	
	@Override
	public String getName() {return "Le creux blanc en Toupie";}
	
	@Override
	public String getDescription() {
		return "Ligne de chandelier unique qui apparait après une tendance baissière "
				+ "significative. Structure qui possède un pet corps blanc, deux ombres "
				+ "dont chacune est plus longue que le corps, et un plus bas inférieur à "
				+ "celui du chandelier antérieur.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return 0.5;
	}

	@Override
	public String getInterpretationText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new WhiteSpinningBottom(data, rank);
	}
	
	public WhiteSpinningBottom()
	{
	}
	
	public WhiteSpinningBottom(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}
}
