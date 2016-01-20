package udpSocketTrading.sub;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import udpSocketTrading.PriceInfoHandler;

public class PriceInfoHashFactory {

	private static final Logger lgr = LoggerFactory.getLogger(PriceInfoHashFactory.class);
	private static HashMap<String, PriceInfoHandler> h = generateStockHash(
			readStockNameList("src/main/resources/stock_names.txt"));

	private static String[] readStockNameList(String filePath) {
		String[] result = null;
		String thisLine = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(filePath));

			while ((thisLine = br.readLine()) != null) {
				lgr.info(thisLine);
				sb.append(thisLine);
				sb.append(",");
			}
			result = sb.toString().split(",");

		} catch (FileNotFoundException e) {
			lgr.error("FILE READ ERROR!!! File [{}] does not exist!!!");
			e.printStackTrace();
		} catch (IOException e) {
			lgr.error("FILE READ ERROR!!! Error occured when read [{}]");
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static HashMap<String, PriceInfoHandler> generateStockHash(String[] stockNameList) {
		HashMap<String, PriceInfoHandler> h = new HashMap<String, PriceInfoHandler>(100);
		for (String stockName : stockNameList) {
			if (h.get(stockName) == null)
				h.put(stockName, new PriceInfoHandler(stockName));
		}
		return h;
	}

	public static HashMap<String, PriceInfoHandler> getStockHash() {
		return h;
	}
}
