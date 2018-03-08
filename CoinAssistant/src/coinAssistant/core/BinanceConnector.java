package coinAssistant.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

public class BinanceConnector {
	private BinanceApiRestClient client;
	
	public BinanceConnector(){
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
		client = factory.newRestClient();
	}
	
	public Collection<CandleStick> getCandlesticks(String symbol){
		List<Candlestick> data = client.getCandlestickBars("NEOETH", CandlestickInterval.FIVE_MINUTES);
		LinkedList<CandleStick> candle = new LinkedList<CandleStick>();
		for (Candlestick c : data)
		{
			candle.add(new CandleStick(Double.parseDouble(c.getOpen()), 
					Double.parseDouble(c.getClose()),
					Double.parseDouble(c.getLow()), 
					Double.parseDouble(c.getHigh())));
		}
		return candle;
		//candle.add(new CandleStick())
		
	}
	
	
}
