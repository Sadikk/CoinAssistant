package coinAssistant.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.*;

public class BinanceConnector {
	private BinanceApi client;
	private BinanceInterval timeInterval;
	public BinanceConnector(){
		client = new BinanceApi();	
	}
	
	/**
	 * Récupère les prix du marché depuis la plateforme de trading binance.com
	 * @param symbol 		paire de currency à analyser
	 * @param interval		intervalle de temps par chandelier
	 */
	public Collection<CandleStick> getCandlesticks(String symbol, BinanceInterval interval){  //todo symbol selection
		BinanceSymbol binanceSymbol;
		if (interval == null)
			interval = BinanceInterval.FIVE_MIN;
		try {
		    timeInterval=interval;
			binanceSymbol = new BinanceSymbol(symbol);
		 
			List<BinanceCandlestick> data = client.klines(binanceSymbol, interval, 100, null);
			
			LinkedList<CandleStick> candle = new LinkedList<CandleStick>();
			for (BinanceCandlestick c : data)
			{
				candle.add(new CandleStick(c.getOpen().doubleValue(), 
						c.getClose().doubleValue(),
						c.getLow().doubleValue(), 
						c.getHigh().doubleValue()));
			}
			return candle;
		}
		catch (BinanceApiException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exception = sw.toString();
			JOptionPane.showMessageDialog(null, 
                    "Une erreur est survenue lors de la récupèration des données depuis le serveur de Binance. Error : " + e.getMessage() + "-- Stacktrace :" + exception, 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
		}
		return null;
		
	}
	
	/**
	 * Récupère la liste des paires actuellement commercées sur Binance
	 */
	public Set<String> getSymbols()
	{
		try {
			return client.allBookTickersMap().keySet();
		} catch (BinanceApiException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exception = sw.toString();
			JOptionPane.showMessageDialog(null, 
                    "Une erreur est survenue lors de la récupèration des symboles depuis le serveur de Binance. Error : " + e.getMessage() + "-- Stacktrace :" + exception, 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	public int getIntervalInMin() {
	    String s=timeInterval.getValue();
	    s=s.split("m")[0];
	    return Integer.valueOf(s);
	    }
	
}
