package coinAssistant.core;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Pattern {
	
	protected ArrayList<CandleStick> data;
	protected int rank;
	
	/**
	 * Applique la reconnaissance de pattern sur une série de données
	 * @param data 		séries de candlestick
	 */
	public void applyPattern(ArrayList<CandleStick> data) {
		for(int i=0;i<data.size()-getPatternSize();i++) {
			if(isPatternPresent(data,i)){
				data.get(i).addPattern(buildInstance(data, i));
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
	
	/**
	 * Retourne la description du pattern
	 * @return description du pattern
	 */
	public abstract String getDescription();
	
	/**
	 * Retourne la couleur du rectangle de détection
	 * @return couleur associée au pattern
	 */
	public abstract Color getColor();
	
	/**
	 * Retourne la valeur de l'interprétation, entre +1 (haussier) et -1 (baissier).
	 * @return valeur d'interpretation
	 */
	public abstract double getInterpretation();
	
	/**
	 * Retourne une chaîne de caractère d'explications sur la manière d'interpreter un pattern
	 * @return explication de l'interpretation du pattern
	 */
	public abstract String getInterpretationText();
	
	/**
	 * Retourne une instance du pattern unique associée à un endroit dans les données
	 * @return instance du pattern
	 */
	public abstract Pattern buildInstance(ArrayList<CandleStick> data, int rank);
	
	public String toString()
	{
		return getName() + " : " + getDescription();
	}
	///source complémentaire pour les patterns: http://www.humbletraders.com/candlestick-patterns/
}
