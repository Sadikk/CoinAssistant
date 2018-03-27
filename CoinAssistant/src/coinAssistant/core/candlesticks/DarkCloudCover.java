package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class DarkCloudCover extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.darkGray;
	
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		
		// deux parents au dessus du milieu
		return(first.isAscend()//premier ascendant
		&& !second.isAscend() //second descendant
		&& second.getOpen()>first.getClose() //gap up entre 1 et 2
		&& second.getClose()<(first.getOpen()+first.getClose()/2)); //perte du 2 compense la moitié des gains de 1

	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "La Couverture en Nuage Noir";}
	
	@Override
	public String getDescription() {
		return "La couverture en nuage noir est une structure formée de deux chandeliers "
				+ "japonais. Le premier chandelier est un long haussier suivi d'un deuxième"
				+ " grand chandelier baissier. L'ouverture du 2ème chandelier se fait sur "
				+ "un gap haussier et la clôture intervient en dessous du point médian du"
				+ " corps du premier chandelier";
	}
	
	@Override
	public Color getColor(){return patternColor;}

	@Override
	public double getInterpretation() {
		return -1;
	}

	@Override
	public String getInterpretationText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new DarkCloudCover(data, rank);
	}
	
	public DarkCloudCover()
	{
	}
	
	public DarkCloudCover(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}
}

//source : http://www.onlinetradingconcepts.com/TechnicalAnalysis/Candlesticks/DarkCloudCover.html