package coinAssistant.ui;

import coinAssistant.core.Pattern;

public interface PatternListener {
	/*
	 * Méthode appellée lorsqu'un pattern est sélectionné
	 */
	public void patternHovered(Pattern pattern);
}
