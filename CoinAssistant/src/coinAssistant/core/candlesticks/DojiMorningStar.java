package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class DojiMorningStar extends Pattern{
	static private int taillePattern=3;
	final double RAPPORT_TAILLE=2;
	public DojiMorningStar() {}
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return(first.getBodySize()>second.getBodySize()*RAPPORT_TAILLE//corps du premier evenement suffisament grand
		&& !first.isAscend() //premier montant
		&& first.getMinBody()>second.getMaxBody() //gap entre les corps de 1 et 2
		&& second.getMinBody()>second.getMaxBody() //gap entre les corps de 2 et 3
		&& third.isAscend() //troisième descendant
		&& third.getBodySize()>second.getBodySize()*RAPPORT_TAILLE); //corps du troisème suffisament grand
		
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
}
