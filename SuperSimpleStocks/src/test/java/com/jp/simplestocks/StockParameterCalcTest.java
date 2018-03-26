package com.jp.simplestocks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jp.simplestocks.domain.Stock;
import com.jp.simplestocks.domain.Stock.Type;
import com.jp.simplestocks.domain.Trade;
import com.jp.simplestocks.domain.Trade.Indicator;
import com.jp.simplestocks.service.StockParameterCalc;

public class StockParameterCalcTest {
	private double delta = 0.001;

	@Test
	public void testCalculateDividendYield() {
		Stock stock1 = new Stock(Type.COMMON, "POP", 8.0, 0, 100.0);
		Stock stock3 = new Stock(Type.PREFERRED, "GIN", 8.0, 2, 100.0);
		assertEquals(StockParameterCalc.calculateDividendYield(stock1, 8.0), 1.0, delta);
		assertEquals(StockParameterCalc.calculateDividendYield(stock3, 2.0), 1.0, delta);
	}

	@Test
	public void testCalculatePERatio() {
		Stock stock1 = new Stock(Type.COMMON, "POP", 8.0, 0, 100.0);
		Stock stock3 = new Stock(Type.PREFERRED, "GIN", 8.0, 2, 100.0);
		assertEquals(StockParameterCalc.calculatePERatio(stock1, 8.0), 1.0, delta);
		assertEquals(StockParameterCalc.calculatePERatio(stock3, 2.0), 1.0, delta);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwExceptionForZeroDividend() {
		Stock stock1 = new Stock(Type.COMMON, "TEA", 0.0, 0, 100.0);
		StockParameterCalc.calculatePERatio(stock1, 2.0);
	}

	@Test
	public void testCalculateVolumeWeightedStockPrice() {
		Stock stock = new Stock(Type.COMMON,"TEA", 0.0, 0, 1.0);
		List<Trade> trades = new ArrayList<>();
		trades.add(new Trade(stock, Indicator.BUY, 0.10, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.09, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.08, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.09, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.1, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.11, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.12, 10, System.currentTimeMillis()));
		assertEquals(0.0985, StockParameterCalc.calculateVolumeWeightedStockPrice(trades), delta);
	}

	@Test
	public void testCalculateGeometricMean() {
		Stock stock = new Stock(Type.COMMON,"TEA",  0.0, 0, 1.0);
		List<Trade> trades = new ArrayList<>();
		trades.add(new Trade(stock, Indicator.BUY, 0.10, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.09, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.08, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.09, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.1, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.11, 10, System.currentTimeMillis()));
		trades.add(new Trade(stock, Indicator.BUY, 0.12, 10, System.currentTimeMillis()));
		assertEquals(1.0, StockParameterCalc.calculateGeometricMean(trades), delta);
	}

}
