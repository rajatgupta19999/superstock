package com.jp.simplestocks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jp.simplestocks.domain.Stock;
import com.jp.simplestocks.domain.Stock.Type;
import com.jp.simplestocks.domain.Trade;
import com.jp.simplestocks.domain.Trade.Indicator;
import com.jp.simplestocks.service.TradeService;

public class TradeServiceTest {
	private double delta = 0.001;

	@Test
	public void testRecordBuyTrade() {
		Stock stock = new Stock(Type.COMMON, "POP", 8.0, 0, 100.0);
		long timeStamp = System.currentTimeMillis();
		Trade trade = TradeService.recordBuyTrade(stock, 3.0, 3, timeStamp);
		assertEquals(trade.indicator, Indicator.BUY);
		assertEquals(trade.stock.symbol, "POP");
		assertEquals(trade.timestamp, timeStamp);
		assertEquals(trade.price, 3.0, delta);
		assertEquals(trade.quantity, 3);
	}

	@Test
	public void testRecordSELLTrade() {
		Stock stock = new Stock(Type.COMMON, "POP", 8.0, 0, 100.0);
		long timeStamp = System.currentTimeMillis();
		Trade trade = TradeService.recordSellTrade(stock, 3.0, 3, timeStamp);
		assertEquals(trade.indicator, Indicator.SELL);
		assertEquals(trade.stock.symbol, "POP");
		assertEquals(trade.timestamp, timeStamp);
		assertEquals(trade.price, 3.0, delta);
		assertEquals(trade.quantity, 3);
	}
	
	@Test
	public void testFilterTradesByTimestamp() {
		Stock stock = new Stock(Type.COMMON, "POP", 8.0, 0, 100.0);
		Stock stock1 = new Stock(Type.COMMON, "TEA", 8.0, 0, 100.0);
		long timeStamp = System.currentTimeMillis();
		Trade trade = TradeService.recordBuyTrade(stock, 3.0, 3, timeStamp);
		Trade trade1 = TradeService.recordSellTrade(stock, 3.0, 3, timeStamp-2000);
		Trade trade2 = TradeService.recordBuyTrade(stock, 5.0, 300, timeStamp-1000);
		Trade past6minTrade = TradeService.recordBuyTrade(stock1, 5.0, 300, timeStamp-6*60*1000);
		List<Trade> trades = new ArrayList<>();
		trades.add(trade);
		trades.add(trade1);
		trades.add(trade2);
		trades.add(past6minTrade);
		List<Trade> filterpast5minTrades = TradeService.filterTradesByTimestamp(trades, timeStamp, 5*60*1000);
		assertEquals(filterpast5minTrades.size(),3);
		assertEquals(filterpast5minTrades.contains(past6minTrade),false);
	}
}
