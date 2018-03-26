package com.jp.simplestocks.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Stock {

	private enum Type{
		COMMON,
		PREFERED
	}
	private final String symbol;
	private double lastDividend;
	private double fixedDividend;
	private double parValue;
	
	
}
