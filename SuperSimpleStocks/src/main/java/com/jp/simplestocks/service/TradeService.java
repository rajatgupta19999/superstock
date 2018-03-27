package com.jp.simplestocks.service;

import java.util.List;
import java.util.stream.Collectors;

import com.jp.simplestocks.domain.Stock;
import com.jp.simplestocks.domain.Trade;
import com.jp.simplestocks.domain.Trade.Indicator;

public class TradeService {

	/**
	 * Record a Buy Trade
	 * 
	 * @param stock
	 * @param price
	 * @param quantity
	 * @param timestamp
	 * @return
	 */
	public static Trade recordBuyTrade(Stock stock, double price, int quantity, long timestamp) {
		return new Trade(stock, Indicator.BUY, price, quantity, timestamp);
	}

	/**
	 * Record a Sell Trade
	 * 
	 * @param stock
	 * @param price
	 * @param quantity
	 * @param timestamp
	 * @return
	 */
	public static Trade recordSellTrade(Stock stock, double price, int quantity, long timestamp) {
		return new Trade(stock, Indicator.SELL, price, quantity, timestamp);
	}

	/**
	 * Filter Trades based on trades in past 15 minutes
	 * 
	 * @param trades
	 * @param timeInMillis
	 * @param intervalInMillis
	 * @return
	 */
	public static List<Trade> filterTradesByTimestamp(List<Trade> trades, long timeInMillis, long intervalInMillis) {
		return trades.stream().filter(
				trade -> (timeInMillis - intervalInMillis <= trade.timestamp && trade.timestamp <= timeInMillis))
				.collect(Collectors.toList());
	}

	/**
	 * Filter Trades based on stocks
	 * 
	 * @param trades
	 * @param timeInMillis
	 * @param intervalInMillis
	 * @return
	 */
	public static List<Trade> filterTradesByStocks(List<Trade> trades, Stock stock) {
		return trades.stream().filter(trade -> trade.stock == stock).collect(Collectors.toList());
	}

}
