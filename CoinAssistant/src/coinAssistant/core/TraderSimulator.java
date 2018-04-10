package coinAssistant.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TraderSimulator {
	double cashInit;
	double cashCurrent;
	
	double cryptoInit;
	double cryptoCurrent;
	List<CandleStick> data;
	
	public TraderSimulator(List <CandleStick> d) {
		data=d;
		this.setInitialPossesion();
	}
	
	/**
	 * initialise la quantité de cash et de cryptoMonnaie disponible au début
	 */
	public void setInitialPossesion() {
		//initialiser le trader avec de quoi trader en rapport de l'ordre de grandeur du cours
		this.cashInit=data.get(0).getOpen()*100;
		this.cryptoInit=100;
	}
	
	/**
	 * simule l'action d'un trader suivant les conseils fournis
	 * @return le pourcentage de gain/perte en dollar par rapport à l'apport initial
	 */
	public double simulateOnAll() {
		cashCurrent=cashInit;
		cryptoCurrent=cryptoInit;
		for(int i=0;i<data.size();i++) {
			int localSum=0;
			double price=0.5*(data.get(i).getOpen()+data.get(i).getClose());
			for( Pattern p : getPatternsAtRank(data,i)) {
				localSum+=p.getInterpretation();
			}
			
			if(localSum>0) {
				this.sell(price,localSum);
			}
			if(localSum<0) {
				this.buy(price, localSum);
			}
		}
		//printBilan(data.get(data.size()-1).getOpen());
		
		return processBilan(data.get(data.size()-1).getOpen());
	}
	
	private void printBilan( double price) {
		String toPrint="au début : "+cashInit+" $ et "+cryptoInit+" crypto"+"\n"
						+"à la fin : "+cashCurrent+" $ et "+cryptoCurrent+" crypto"+"\n"
						+"avec un prix de "+price;
		
		System.out.println(toPrint);
		
		System.out.println("soit, tout converti en $ : "+processBilan(price)+"%");
	}
	
	/**
	 * calcule la différence entre l'argent au départ et à la fin en prenant en compte les cryptomonnaies
	 * @param price le prix utilisé pour la correspondance
	 * @return le pourcentage de gain/perte
	 */
	private double processBilan(double price) {
		double cashTotInit=cashInit+price*cryptoInit;
		double cashTotEnd=cashCurrent+price*cryptoInit;
		return 100*(cashTotEnd-cashTotInit)/(cashTotInit);
	}
	
	/**
	 * vend le nombre de crypto voulu
	 * @param price le prix de vente
	 * @param numberToSell 
	 */
	private void sell(double price, double numberToSell) {
		if(cryptoCurrent>numberToSell) { //vendre le nombre prévu
			cashCurrent+=price*numberToSell;
			cryptoCurrent-=numberToSell;
		}
		else {// si pas assez de stocks, tout liquider
			cashCurrent+=cryptoCurrent*price;
			cryptoCurrent=0;
			
		}
	}
	/**
	 * achete le nombre de crypto voulu
	 * @param price  le prix d'achat
	 * @param numberToSell 
	 */
	private void buy(double price, double numberToSell) {
		if(cashCurrent>numberToSell*price) { //acheter le nombre prévu
			cashCurrent-=price*numberToSell;
			cryptoCurrent+=numberToSell;
		}
		else {// si pas assez de stocks, tout depenser
			cryptoCurrent+=cashCurrent*price;
			cashCurrent=0;
		}
	}
	
	
	/**
	 * donne tous les patterns actifs à un rang et pas seulement ceux qui y débutent
	 * @param data la série de données
	 * @param rank le rang à chercher
	 * @return les patterns actifs
	 */
	private List<Pattern> getPatternsAtRank(List<CandleStick> data,int rank){
		List<Pattern> result=new LinkedList<Pattern>();
		final int MAX_SIZE_PATTERN=10;//on cherche dans le passé jusqu'à n evenements plus tôt
		for(int i=rank; (i>rank-MAX_SIZE_PATTERN)&&(i>=0);i--) {//protection i>=0 pour eviter sortie de liste
			List<Pattern> listPatterns= data.get(i).getPatterns();
			for(Pattern p:listPatterns) {
				if(p.getPatternSize()>(rank-i)) {
					result.add(p);
				}
			}
		}
		return result;
		
	}
}
