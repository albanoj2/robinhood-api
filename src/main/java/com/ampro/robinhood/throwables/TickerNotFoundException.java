package com.ampro.robinhood.throwables;

/**
 * An exception to be thrown when a ticker is not found after polling Robinhood
 * @author Jonathan Augustine
 */
public class TickerNotFoundException extends RobinhoodApiException {
    
	private static final long serialVersionUID = -4401894268654476314L;
	private String ticker;

    public TickerNotFoundException() {
        super();
        this.ticker = "";
    }

    public TickerNotFoundException(String message) {
        super(message);
        this.ticker = "";
    }

    public TickerNotFoundException(String ticker, String message) {
        super(message);
        this.ticker = ticker;
    }

    public TickerNotFoundException with(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public String getTicker() {
        return ticker;
    }
}
