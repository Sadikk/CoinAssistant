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
		return "Le harami haussier est un chandelier baissier engloutissant un petit "
				+ "chandelier haussier, donnant un signe d'un renversement de la tendance à "
				+ "la baisse.";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return data.get(rank).isAscend() ? 0.3 : -0.3;
	}

	@Override
	public String getInterpretationText() {
		// TODO Auto-generated method stub
		return null;
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
