package com.jp.simplestocks.domain;

public class Stock {

	public enum Type {
		COMMON, PREFERRED
	}

	public final Type type;
	public final String symbol;
	public double lastDividend;
	public int fixedDividend;
	public double parValue;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param symbol
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 */

	public Stock(Type type, String symbol, double lastDividend, int fixedDividend, double parValue) {
		super();
		this.type = type;
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

}
