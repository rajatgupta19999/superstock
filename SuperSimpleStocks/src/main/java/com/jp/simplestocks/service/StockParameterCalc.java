package com.jp.simplestocks.service;

import java.util.List;

import com.jp.simplestocks.domain.Stock;
import com.jp.simplestocks.domain.Stock.Type;
import com.jp.simplestocks.domain.Trade;

public class StockParameterCalc {

	/**
	 * Calculate dividend from fixed dividend.
	 *
	 * @param fixedDividend
	 * @param parValue
	 * @return
	 */

	protected static double ComputeDividendFromLastDividend(double lastDividend) {
		return lastDividend;
	}

	/**
	 * Calculate dividend from fixed dividend.
	 *
	 * @param fixedDividend
	 * @param parValue
	 * @return
	 */
	protected static double ComputeDividendFromFixedDividend(double fixedDividend, double parValue) {
		return fixedDividend * parValue / 100;
	}

	/**
	 * Calculate dividend based on Type as Common or Preferred.
	 *
	 * @param stock
	 * @return
	 */
	protected static double calculateDividend(Stock stock) {
		double dividend;
		if (stock.type == Type.PREFERRED) {
			dividend = ComputeDividendFromFixedDividend(stock.fixedDividend, stock.parValue);
		} else {
			dividend = ComputeDividendFromLastDividend(stock.lastDividend);
		}
		return dividend;
	}

	/**
	 * Calculate dividend yield.
	 *
	 * @param stock
	 * @param marketprice
	 * @return
	 */
	public static double calculateDividendYield(Stock stock, Double marketPrice) {
		return calculateDividend(stock) / marketPrice;
	}

	/**
	 * Calculate PE Ratio.
	 *
	 * @param stock
	 * @param marketprice
	 * @return
	 */
	public static double calculatePERatio(Stock stock, Double marketPrice) {
		double dividend = calculateDividend(stock);
		if (dividend != 0)
			return marketPrice / dividend;
		else
			throw new IllegalArgumentException("Dividend for the stock is zero");
	}

	/**
	 * Calculate the Volume Weighted Stock Price based on trades in past 15 minutes
	 * 
	 * @param trades
	 * @return
	 */
	public static double calculateVolumeWeightedStockPrice(List<Trade> trades) {
		List<Trade> past15minTrades = TradeService.filterTradesByTimestamp(trades, System.currentTimeMillis(),
				15 * 60 * 1000);
		double allTradesValue = 0.0;
		double totalQuantity = 0.0;
		for (Trade trade : past15minTrades) {
			allTradesValue += (trade.price * trade.quantity);
			totalQuantity += trade.quantity;
		}
		return allTradesValue / totalQuantity;
	}

	/**
	 * Calculate the GBCE All Share Index using the geometric mean of prices for all
	 * stocks
	 * 
	 * @param trades
	 * @return
	 */
	public static double calculateGeometricMean(List<Trade> trades) {
		long totalPrice = 1;
		for (Trade trade : trades) {
			totalPrice *= trade.price;
		}
		long power = 1 / trades.size();
		return Math.pow(totalPrice, power);
	}

}
