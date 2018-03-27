package com.jp.simplestocks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jp.simplestocks.domain.Stock;
import com.jp.simplestocks.domain.Stock.Type;
import com.jp.simplestocks.domain.Trade;
import com.jp.simplestocks.service.StockParameterCalc;
import com.jp.simplestocks.service.TradeService;

public class StockTradingIntegrationTest {

	public final List<Stock> stocks = new ArrayList<Stock>();
	public final List<Trade> trades = new ArrayList<Trade>();
	private static final double DELTA = 0.001;
	double[] marketPrice = { 1.0, 1.0, 1.0, 1.0, 1.0 };
	double[] dividendYield = { 1.0, 8.0, 23.0, 2.0, 13.0 };
	double[] PERatio = { 1.0, 0.125, 0.043, 0.5, 0.076 };
	double[] vwsp = { 1.0, 1.0,1.0,1.0,1.0 };
	long timeStamp;

	public void buildStockList() {
		stocks.add(new Stock(Type.COMMON, "TEA", 1.0, 0, 100.0));
		stocks.add(new Stock(Type.COMMON, "POP", 8.0, 0, 100.0));
		stocks.add(new Stock(Type.COMMON, "ALE", 23.0, 0, 60.0));
		stocks.add(new Stock(Type.PREFERRED, "GIN", 8.0, 2, 100.0));
		stocks.add(new Stock(Type.COMMON, "JOE", 13.0, 0, 250.0));
	}

	public void buildTradeList() {
		int timeInterval = 30;
		for (long i = 0; i < timeInterval * 60 * 1000; i = i + 30 * 1000) {
			for (Stock stock : stocks) {
				if (i < 15 * 60 * 1000)
					trades.add(TradeService.recordBuyTrade(stock, 1.0, 1, timeStamp - i));
				else
					trades.add(TradeService.recordSellTrade(stock, 1.0, 1, timeStamp - i));

			}
		}
	}

	@Before
	public void setup() {
		timeStamp = System.currentTimeMillis();
		buildStockList();
		buildTradeList();
	}

	@Test
	public void testSuperStocks() {
		int count = 0;
		for (Stock stock : stocks) {
			assertEquals(StockParameterCalc.calculateDividendYield(stock, marketPrice[count]), dividendYield[count],
					DELTA);
			assertEquals(StockParameterCalc.calculatePERatio(stock, marketPrice[count]), PERatio[count], DELTA);
			assertEquals(StockParameterCalc.calculateVolumeWeightedStockPrice(trades, stock),vwsp[count],DELTA);
			++count;
		}
		assertEquals(StockParameterCalc.calculateGeometricMean(trades),1.0,DELTA);
	}
}
