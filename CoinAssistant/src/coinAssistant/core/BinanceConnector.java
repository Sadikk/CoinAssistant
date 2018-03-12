package coinAssistant.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.*;

public class BinanceConnector {
	private BinanceApi client;
	
	public BinanceConnector(){
		client = new BinanceApi();	
	}
	
	//todo : replace ethbtc by symbol once work done
	public Collection<CandleStick> getCandlesticks(String symbol){
		BinanceSymbol binanceSymbol;
		try {
			binanceSymbol = new BinanceSymbol("ETHBTC");
		 
			List<BinanceCandlestick> data = client.klines(binanceSymbol, BinanceInterval.ONE_HOUR, 5, null);
			//BinanceCandlestick binanceCandlestick = data.get(0);
		   // System.out.println("KLINE=" + binanceCandlestick.toString() );
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		//candle.add(new CandleStick())
		
	}
	
	
}
