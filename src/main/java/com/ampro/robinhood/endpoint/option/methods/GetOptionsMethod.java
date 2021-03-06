package com.ampro.robinhood.endpoint.option.methods;

import com.ampro.robinhood.Configuration;
import com.ampro.robinhood.endpoint.option.data.Options;
import com.ampro.robinhood.net.ApiMethod;
import com.ampro.robinhood.net.request.RequestMethod;

/**
 * API method that represents a request to obtain the {@link Option} objects
 * associated with a Robinhood account.
 * 
 * @author <a href="https://github.com/albanoj2">Justin Albano</a>
 * 
 * @since 0.8.2
 */
public class GetOptionsMethod extends ApiMethod {

	/**
	 * Create an API method using the Robinhood API as a default host.
	 * 
	 * @param config
	 *            The configuration associated with this method.
	 */
	public GetOptionsMethod(Configuration config) {
		this(config, "https://api.robinhood.com");
	}

	/**
	 * Create an API method using the specific host as the target. This constructor
	 * should not be used directly, unless for testing (i.e. using a mock host as an
	 * endpoint).
	 * 
	 * @param config
	 *            The configuration associated with this method.
	 * @param host
	 *            The target of the method call (i.e.
	 *            {@code http://localhost:8080}).
	 */
	protected GetOptionsMethod(Configuration config, String host) {
		super(config);

		this.setMethodType(RequestMethod.GET);
		this.setUrlBase(host + "/options/aggregate_positions/");
		this.setReturnType(Options.class);
	}
}
