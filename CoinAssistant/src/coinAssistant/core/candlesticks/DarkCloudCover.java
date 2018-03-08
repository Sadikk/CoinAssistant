package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class DarkCloudCover extends Pattern{
	static private int taillePattern=3;
	public DarkCloudCover() {}
	
	//identifie une sequence précise comme correspondante au pattern ou non
	public boolean identifie(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		
		// deux parents au dessus du milieu
		if(first.isAscend()//premier ascendant
		&& !second.isAscend() //second descendant
		&& second.getOpen()>first.getClose() //gap up entre 1 et 2
		&& second.getClose()<(first.getOpen()+first.getClose()/2)) //perte du 2 compense la moitié des gains de 1
		{
			return true;
		}
		
		return false;
	}
	
	//retourne la taille de l'évenement considéré
	public int getTaillePattern() {return taillePattern;}
}

//source : http://www.onlinetradingconcepts.com/TechnicalAnalysis/Candlesticks/DarkCloudCover.html