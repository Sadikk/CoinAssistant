package coinAssistant.core.candlesticks;

import java.awt.Color;
import java.util.ArrayList;

import coinAssistant.core.CandleStick;
import coinAssistant.core.Pattern;

public class DojiMorningStar extends Pattern{
	static private int taillePattern=3;
	static private Color patternColor=Color.green;
	final double RAPPORT_TAILLE=2;
	
	@Override
	public boolean isPatternPresent(ArrayList<CandleStick> data, int rg) {
		CandleStick first=data.get(rg);
		CandleStick second=data.get(rg+1);
		CandleStick third=data.get(rg+2);
		
		return(first.getBodySize()>second.getBodySize()*RAPPORT_TAILLE//corps du premier evenement suffisament grand
		&& !first.isAscend() //premier montant
		&& first.getMinBody()>second.getMaxBody() //gap entre les corps de 1 et 2
		&& second.getMinBody()>second.getMaxBody() //gap entre les corps de 2 et 3
		&& third.isAscend() //troisième descendant
		&& third.getBodySize()>second.getBodySize()*RAPPORT_TAILLE); //corps du troisème suffisament grand
		
		
	}
	
	@Override
	public int getPatternSize() {return taillePattern;}
	
	@Override
	public String getName() {return "Etoile du Matin Doji";}
	
	@Override
	public String getDescription() {
		return "Structure de renversement de creux, c'est une configuration rare formée "
				+ "de trois chandeliers : le premier est un grand chandelier baissier plein,"
				+ " le second un marteau noir marquant un nouveau plus-bas et qui clôture "
				+ "au-dessus de la clôture de la première bougie, et le troisième est un "
				+ "très petit chandelier blanc.";
	}
	
	@Override
	public Color getColor(){return patternColor;}
	
	@Override
	public double getInterpretation() {
		return 1;
	}

	@Override
	public String getInterpretationText() {
		return "L’étoile du matin prend forme suite à un mouvement baissier. "
				+ "En effet, ce pattern indique un retournement baissier de l’action : "
				+ "l’étoile signifie que les vendeurs hésitent d’enfoncer le clou à la baisse."
				+ "Enfin, ce sont les acheteurs qui l'emportent et le cours entame une phase "
				+ "de hausse.";
	}

	@Override
	public Pattern buildInstance(ArrayList<CandleStick> data, int rank) {
		return new DojiMorningStar(data, rank);
	}
	
	public DojiMorningStar()
	{
	}
	
	public DojiMorningStar(ArrayList<CandleStick> data, int rank)
	{
		this.data = data;
		this.rank = rank;
	}
}
