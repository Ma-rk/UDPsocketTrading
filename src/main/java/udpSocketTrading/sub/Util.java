package udpSocketTrading.sub;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;
import udpSocketTrading.PriceInfoHandler;

public class Util {

	public static Queue<TradeInfoEty> TRADE_INFO_Q = new LinkedList<TradeInfoEty>();
	public static Queue<PriceInfoHandler> PRICE_STATE_Q = new LinkedList<PriceInfoHandler>();
	public static int ORDER_NO = 100000;
	public static int NUM_OF_SAMPLE = 15;
	public static int NUM_OF_SAMPLE_5 = 5;

	public static int getPriceId(DatagramPacket inPacket) {
		int i = 0;
		byte[] data = inPacket.getData();

		for (i = 0; i < data.length; i++)
			if (data[i] == 44)
				break;

		return Integer.parseInt(new String(data, 0, i));
	}

	public static String getStockName(DatagramPacket inPacket) {
		int startAt = 0;
		int i = 0;
		int commaCount = 0;
		byte[] data = inPacket.getData();

		for (i = 0; i < data.length; i++) {
			if (data[i] == 44) {
				commaCount++;
				if (commaCount == 9)
					startAt = i;
				if (commaCount == 10) {
					break;
				}
			}
		}
		return new String(data, startAt + 1, i - startAt - 1);
	}

	public static double getTradePrice(DatagramPacket inPacket) {
		int startAt = 0;
		int i = 0;
		int commaCount = 0;
		byte[] data = inPacket.getData();

		for (i = 0; i < data.length; i++) {
			if (data[i] == 44) {
				commaCount++;
				if (commaCount == 12)
					startAt = i;
				if (commaCount == 13) {
					break;
				}
			}
		}
		return Double.parseDouble(new String(data, startAt + 1, i - startAt - 1));
	}

	public static int getTradeQty(DatagramPacket inPacket) {
		int startAt = 0;
		int i = 0;
		int commaCount = 0;
		byte[] data = inPacket.getData();

		for (i = 0; i < data.length; i++) {
			if (data[i] == 44) {
				commaCount++;
				if (commaCount == 13)
					startAt = i;
				if (commaCount == 14) {
					break;
				}
			}
		}
		return Integer.parseInt(new String(data, startAt + 1, i - startAt - 1));
	}
}
