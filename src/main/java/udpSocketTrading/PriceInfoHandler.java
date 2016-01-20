package udpSocketTrading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import udpSocketTrading.sub.TradeInfoEty;

public class PriceInfoHandler {

	private static final Logger lgr = LoggerFactory.getLogger(PriceInfoHandler.class);

	private String stockName;
	private int stockQty;
	private int idxPointer = 15;
	private double tradePrice[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	private int tradeQty[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private double multiplied[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

	public PriceInfoHandler(String stockName) {
		this.stockName = stockName;
	}

	public void putTradeInfo(TradeInfoEty tiEty) {

		if (tiEty.getCurrentPrice() < 1.0d || tiEty.getCurrentPrice() > 2.0d) {
			// 가격이 1.0 미만이거나 2.0 초과면
			// - 보유수량 모두 SELL
			// - 거래내역을 수집하지 않는다

		} else {
			// 가격이 1~2의 범위에 있으면 해당 내역을 수집한다.
			// - 거래 내역은 15개까지만 수집한다.
			// - 15개의 내역이 넘으면 가장 오래 된 내역을 버린다.
			// 최근 1~5번째 내역의 가중평균 가격을 계산한다.
			// 최근 1~15번째 내역의 가중평균 가격을 계산한다.
			// - 1~5번째 내역의 가중평균이 1~15의 가중평균 초과시 매도가격1에 BUY
			// - 1~5번째 내역의 가중평균이 1~15의 가중평균 미달시 매수가격1에 SELL
			if (idxPointer == 15)
				idxPointer = 0;
			else
				idxPointer++;

			tradePrice[idxPointer] = tiEty.getCurrentPrice();
			tradeQty[idxPointer] = tiEty.getTradeQty();
			multiplied[idxPointer] = tradePrice[idxPointer] * tradeQty[idxPointer];

			if (getWeightedAvg5() > getWeightedAvg15()) {
				// 1~5번째 내역의 가중평균이 1~15의 가중평균 초과시 매도가격1에 BUY
			} else {
				// 1~5번째 내역의 가중평균이 1~15의 가중평균 이하시 매수가격1에 SELL
			}

		}
	}

	private double getWeightedAvg5() {
		// 최근 5개 거래이력의 가중평균을 반환
		double weightSum = 0.0d;
		int tradeQtySum = 0;
		int idx = idxPointer;
		for (int i = 0; i < 5; i++) {
			if (idx < 0) {
				idx = 15;
			}
			weightSum += multiplied[idx];
			tradeQtySum += tradeQty[idx];
		}
		return weightSum / tradeQtySum;

	}

	private double getWeightedAvg15() {
		// 최근 15개 거래이력(=배열 전체)의 가중평균을 반환
		double weightSum = 0.0d;
		int tradeQtySum = 0;
		for (double d : multiplied)
			weightSum += d;
		for (double d : tradeQty)
			tradeQtySum += d;
		return weightSum / tradeQtySum;
	}
}
