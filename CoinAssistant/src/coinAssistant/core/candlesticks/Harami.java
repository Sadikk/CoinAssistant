package coinAssistant.core.candlesticks;

import java.util.ArrayList;
import coinAssistant.core.*;

public class Harami extends Pattern{
	static private int taillePattern=2;
	public Harami() {}
	
	//identifie une sequence précise comme correspondante au pattern ou non
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		// deuxieme candlestick inclu dans le [open:close] du premier	
		return(first.getMinBody()<second.getLow() //inclusion du minimum
		&& first.getMaxBody()>second.getHigh()); {//inclusion du maximum
		
	}
	
	//retourne la taille de l'évenement considéré
	public int getPatternSize() {return taillePattern;}
}
