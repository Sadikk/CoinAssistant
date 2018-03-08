package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class AbandonedBaby extends Pattern{
	static private int taillePattern=3;
	public AbandonedBaby() {}
	
	//identifie une sequence pr�cise comme correspondante au pattern ou non
	public boolean identifie(ArrayList<CandleStick> data, int rg) {
		// deux parents au dessus du milieu
		if(data.get(rg).getLow()>data.get(rg+1).getHigh()//parent avant
		&& data.get(rg+2).getLow()>data.get(rg+1).getHigh()) {//parent apr�s
			return true;
		}
		
		return false;
	}
	
	//retourne la taille de l'�venement consid�r�
	public int getTaillePattern() {return taillePattern;}
}
