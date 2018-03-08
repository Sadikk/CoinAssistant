package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class DojiMorningStar extends Pattern{
	static private int taillePattern=3;
	final double RAPPORT_TAILLE=2;
	public DojiMorningStar() {}
	
	//identifie une sequence précise comme correspondante au pattern ou non
	public boolean identifie(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		if(first.tailleCorps()>second.tailleCorps()*RAPPORT_TAILLE//corps du premier evenement suffisament grand
		&& !first.isAscend() //premier montant
		&& first.getMinCorps()>second.getMaxCorps() //gap entre les corps de 1 et 2
		&& second.getMinCorps()>second.getMaxCorps() //gap entre les corps de 2 et 3
		&& third.isAscend() //troisième descendant
		&& third.tailleCorps()>second.tailleCorps()*RAPPORT_TAILLE) //corps du troisème suffisament grand
		{
			return true;
		}
		
		return false;
		
	}
	
	//retourne la taille de l'évenement considéré
	public int getTaillePattern() {return taillePattern;}
}
