package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class Harami extends Pattern{
	static private int taillePattern=2;
	static private Color patternColor=Color.pink;
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		// deuxieme candlestick inclu dans le [open:close] du premier	
		return(first.getMinBody()<second.getLow() //inclusion du minimum
		&& first.getMaxBody()>second.getHigh() && first.getBodySize() > 3 * second.getBodySize()); //inclusion du maximum
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "Harami";}
	
	@Override
	public String getDescription() {
		return "Le harami est un chandelier dont le corps s'est constitué à l'intérieur du corps d'un"
				+ "chandelier antérieur. Quel que soit le mouvement de marché, il doit être composé "
				+ "de deux bougies de couleurs différentes.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return data.get(rank).isAscend() ? 0.3 : -0.3;
	}

	@Override
	public String getInterpretationText() {
		return "Le harami haussier (resp. baissier) se forme suite à une mouvement baissier (resp. haussier)"
				+ "important. En règle général, il vous indique que la baisse (resp. la hausse) de l'action"
				+ "arrive à sa fin.";
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new Harami(data, rank);
	}
	
	public Harami()
	{
	}
	
	public Harami(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}
}
