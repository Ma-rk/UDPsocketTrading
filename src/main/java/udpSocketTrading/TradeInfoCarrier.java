package udpSocketTrading;

import java.util.HashMap;

import udpSocketTrading.PriceInfoHandler;
import udpSocketTrading.sub.PriceInfoHashFactory;
import udpSocketTrading.sub.TradeInfoEty;
import udpSocketTrading.sub.Util;

public class TradeInfoCarrier implements Runnable {

	private HashMap<String, PriceInfoHandler> h = PriceInfoHashFactory.getStockHash();

	public void run() {
		// TradeInfoEty tiEty = Util.TRADE_INFO_Q.poll();
		// h.get(tiEty.getStockName());

	}

}
