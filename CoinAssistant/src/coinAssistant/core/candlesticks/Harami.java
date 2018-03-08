package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class Harami extends Pattern{
	static private int taillePattern=2;
	public Harami() {}
	
	//identifie une sequence précise comme correspondante au pattern ou non
	public boolean identifie(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		// deuxieme candlestick inclu dans le [open:close] du premier	
		if(first.getMinCorps()<second.getLow() //inclusion du minimum
		&& first.getMaxCorps()>second.getHigh()) {//inclusion du maximum
			return true;
		}
		
		return false;
	}
	
	//retourne la taille de l'évenement considéré
	public int getTaillePattern() {return taillePattern;}
}
