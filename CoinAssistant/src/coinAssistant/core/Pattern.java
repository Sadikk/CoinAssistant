package coinAssistant.core;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Pattern {
	/**
	 * Applique la reconnaissance de pattern sur une s�rie de donn�es
	 * @param data 		s�ries de candlestick
	 */
	public void applyPattern(ArrayList<CandleStick> data) {
		for(int i=0;i<data.size()-getPatternSize();i++) {
			if(isPatternPresent(data,i)){
				data.get(i).addPattern(this);
			}
		}
	}
	
	/**
	 * V�rifie si un pattern est pr�sent � un certain rang dans une s�rie de donn�es
	 * @param data		s�ries de candlestick
	 * @param data 		rang auquel v�rifier
	 * @return true si le pattern est pr�sent, false sinon
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
	///source compl�mentaire pour les patterns: http://www.humbletraders.com/candlestick-patterns/
}
