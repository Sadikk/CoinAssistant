package coinAssistant.core;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Pattern {
	
	protected ArrayList<CandleStick> data;
	protected int rank;
	
	/**
	 * Applique la reconnaissance de pattern sur une s�rie de donn�es
	 * @param data 		s�ries de candlestick
	 */
	public void applyPattern(ArrayList<CandleStick> data) {
		for(int i=0;i<data.size()-getPatternSize();i++) {
			if(isPatternPresent(data,i)){
				data.get(i).addPattern(buildInstance(data, i));
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
	
	/**
	 * Retourne la description du pattern
	 * @return description du pattern
	 */
	public abstract String getDescription();
	
	/**
	 * Retourne la couleur du rectangle de d�tection
	 * @return couleur associ�e au pattern
	 */
	public abstract Color getColor();
	
	/**
	 * Retourne la valeur de l'interpr�tation, entre +1 (haussier) et -1 (baissier).
	 * @return valeur d'interpretation
	 */
	public abstract double getInterpretation();
	
	/**
	 * Retourne une cha�ne de caract�re d'explications sur la mani�re d'interpreter un pattern
	 * @return explication de l'interpretation du pattern
	 */
	public abstract String getInterpretationText();
	
	/**
	 * Retourne une instance du pattern unique associ�e � un endroit dans les donn�es
	 * @return instance du pattern
	 */
	public abstract Pattern buildInstance(ArrayList<CandleStick> data, int rank);
	
	public String toString()
	{
		return getName() + " : " + getDescription();
	}
	///source compl�mentaire pour les patterns: http://www.humbletraders.com/candlestick-patterns/
}
