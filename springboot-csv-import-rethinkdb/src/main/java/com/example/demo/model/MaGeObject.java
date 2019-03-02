package com.example.demo.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { "isin", "totalSharesPrice", "price", "currency", "timestamp" })
public class MaGeObject {

	private String isin;
	private BigDecimal totalSharesPrice;
	private double price;
	private ZonedDateTime timestamp = ZonedDateTime.now();
	private String currency;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public double getTotalSharesPrice() {
		return totalSharesPrice.doubleValue();
	}

	public void setTotalSharesPrice(BigDecimal totalSharesPrice) {
		this.totalSharesPrice = totalSharesPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public MaGeObject() {
	}

	public MaGeObject(String isin, BigDecimal totalSharesPrice, double price, ZonedDateTime timestamp,
			String currency) {
		super();
		this.isin = isin;
		this.totalSharesPrice = totalSharesPrice;
		this.price = price;
		this.timestamp = timestamp;
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "MaGeObject [isin=" + isin + ", totalSharesPrice=" + totalSharesPrice + ", price=" + price
				+ ", timestamp=" + timestamp + ", currency=" + currency + "]";
	}

}
