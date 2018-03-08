package coinAssistant.core;

import java.util.ArrayList;

public abstract class Pattern {
	public void chercherPattern(ArrayList<CandleStick> data) {
		for(int i=0;i<data.size()-getTaillePattern();i++) {
			if(identifie(data,i)){
				data.get(i).setPattern(this);
			}
		}
	}
	public abstract boolean identifie(ArrayList<CandleStick> data, int rang );
	
	public abstract int getTaillePattern();
	
	
	///source complémentaire pour les patterns: http://www.humbletraders.com/candlestick-patterns/
}
