package udpSocketTradingTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import udpSocketTrading.OrderIssuer;
import udpSocketTrading.TradeInfoCarrier;
import udpSocketTrading.TradeInfoReceiver;

public class UdpSocketTraderTest {

	private static final Logger lgr = LoggerFactory.getLogger(UdpSocketTraderTest.class);

	@Test
	public void test() {
		List<Thread> threadList = new ArrayList<Thread>();
		threadList.add(new Thread(new TradeInfoReceiver(20001, "s_1")));
		// threadList.add(new Thread(new TradeInfoReceiver(20002, "s_2")));
		// threadList.add(new Thread(new TradeInfoReceiver(20003, "s_3")));
		// threadList.add(new Thread(new TradeInfoReceiver(20004, "s_4")));
		// threadList.add(new Thread(new TradeInfoReceiver(20005, "s_5")));
		threadList.add(new Thread(new TradeInfoCarrier()));
		// threadList.add(new Thread(new TradeInfoCarrier()));
		threadList.add(new Thread(new OrderIssuer()));
		// threadList.add(new Thread(new OrderIssuer()));

		for (Thread t : threadList)
			t.start();

		try {
			for (Thread t : threadList)
				t.join();
		} catch (InterruptedException e) {
			lgr.error("Thread join Interrupted!!!");
			e.printStackTrace();
		}
	}

}
