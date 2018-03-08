package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class Harami extends Pattern{
	static private int taillePattern=2;
	public Harami() {}
	
	//identifie une sequence précise comme correspondante au pattern ou non
	public boolean identifie(ArrayList<CandleStick> data, int rg) {
		// deuxieme candlestick inclu dans le [open:close] du premier
		double bas=Math.min(data.get(rg).getOpen(),data.get(rg).getClose());
		double haut=Math.max(data.get(rg).getOpen(),data.get(rg).getClose());
		
		if(bas<data.get(rg+1).getLow() //inclusion du minimum
		&& haut>data.get(rg+1).getHigh()) {//inclusion du maximum
			return true;
		}
		
		return false;
	}
	
	//retourne la taille de l'évenement considéré
	public int getTaillePattern() {return taillePattern;}
}
