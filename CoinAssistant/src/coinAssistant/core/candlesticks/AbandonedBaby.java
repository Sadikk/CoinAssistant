package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class AbandonedBaby extends Pattern{
	static private int taillePattern=3;
	public AbandonedBaby() {}
	
	//identifie une sequence pr�cise comme correspondante au pattern ou non
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		// deux parents au dessus du milieu
		return (data.get(rg).getLow()>data.get(rg+1).getHigh()//parent avant
		&& data.get(rg+2).getLow()>data.get(rg+1).getHigh()); //parent apr�s
			
		
	
	}
	
	//retourne la taille de l'�venement consid�r�
	public int getPatternSize() {return taillePattern;}
}
