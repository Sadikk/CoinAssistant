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
	
	public void setInitialPossesion() {
		//initialiser le trader avec de quoi trader en rapport de l'ordre de grandeur du cours
		this.cashInit=data.get(0).getOpen()*100;
		this.cryptoInit=100;
	}
	
	public void simulateOnAll() {
		cashCurrent=cashInit;
		cryptoCurrent=cryptoInit;
		for(int i=0;i<data.size();i++) {
			int localSum=0;
			double price=0.5*(data.get(i).getOpen()+data.get(i).getClose());
			for( Pattern p : getPatternsAtRank(data,i)) {
				localSum+=p.getInterpretation();
			}
			
			if(localSum>0) {
				this.buy(price,localSum);
			}
			if(localSum<0) {
				this.sell(price, localSum);
			}
		}
		printBilan(data.get(data.size()-1).getOpen());
		
	}
	private void printBilan( double price) {
		String toPrint="au début : "+cashInit+" $ et "+cryptoInit+" crypto"+"\n"
						+"à la fin : "+cashCurrent+" $ et "+cryptoCurrent+" crypto"+"\n"
						+"avec un prix de "+price;
		
		System.out.println(toPrint);
		double cashTotInit=cashInit+price*cryptoInit;
		double cashTotEnd=cashCurrent+price*cryptoInit;
		double diffRelative=100*(cashTotEnd-cashTotInit)/(cashTotInit);
		System.out.println("soit, tout converti en $ : "+diffRelative+"%");
	}
	private void sell(double price, double numberToSell) {
		if(cryptoCurrent>numberToSell) { //acheter le nombre prévu
			cashCurrent+=price*numberToSell;
			cryptoCurrent-=numberToSell;
		}
		else {// si pas assez de stocks, tout liquider
			cashCurrent+=cryptoCurrent*price;
			cryptoCurrent=0;
			
		}
	}
	private void buy(double price, double numberToSell) {
		if(cashCurrent>numberToSell*price) { //acheter le nombre prévu
			cashCurrent-=price*numberToSell;
			cryptoCurrent+=numberToSell;
		}
		else {// si pas assez de stocks, tout liquider
			cryptoCurrent+=cashCurrent*price;
			cashCurrent=0;
		}
	}
	
	private List<Pattern> getPatternsAtRank(List<CandleStick> data,int rank){
		List<Pattern> result=new LinkedList<Pattern>();
		final int MAX_SIZE_PATTERN=10;//on cherche dans le passé jusqu'à n evenements plus tôt
		for(int i=rank; (i>rank-MAX_SIZE_PATTERN)&&(i>=0);i--) {//protection i>=0 pour eviter sortie de tableau
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
