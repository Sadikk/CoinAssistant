package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class DojiEveningStar extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.gray;
	final double RAPPORT_TAILLE=2;
	public DojiEveningStar() {}
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return (first.getBodySize()>second.getBodySize()*RAPPORT_TAILLE//corps du premier evenement suffisament grand
		&& first.isAscend() //premier montant
		&& first.getMaxBody()<second.getMinBody() //gap entre les corps de 1 et 2
		&& second.getMaxBody()<second.getMinBody() //gap entre les corps de 2 et 3
		&& !third.isAscend() //troisi�me descendant
		&& third.getBodySize()>second.getBodySize()*RAPPORT_TAILLE); //corps du trois�me suffisament grand
	
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	@Override
	public Color getColor(){return patternColor;}
}
