package coinAssistant.core;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Pattern {
	/**
	 * Applique la reconnaissance de pattern sur une série de données
	 * @param data 		séries de candlestick
	 */
	public void applyPattern(ArrayList<CandleStick> data) {
		for(int i=0;i<data.size()-getPatternSize();i++) {
			if(isPatternPresent(data,i)){
				data.get(i).addPattern(this);
			}
		}
	}
	
	/**
	 * Vérifie si un pattern est présent à un certain rang dans une série de données
	 * @param data		séries de candlestick
	 * @param data 		rang auquel vérifier
	 * @return true si le pattern est présent, false sinon
	 */
	public abstract boolean isPatternPresent(ArrayList<CandleStick> data, int index);
	
	
	/**
	 * Retourne la taille du pattern (en nombre de candlesticks)
	 * @return taille du pattern
	 */
	public abstract int getPatternSize();
	
	/**
	 * Retourne le nom du pattern
	 * @return nom du pattern
	 */
	public abstract String getName();
	
	//public abstract String getDescription();
	
	public abstract Color getColor();
	///source complémentaire pour les patterns: http://www.humbletraders.com/candlestick-patterns/
}
