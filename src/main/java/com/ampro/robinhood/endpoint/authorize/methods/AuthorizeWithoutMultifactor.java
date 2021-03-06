package com.ampro.robinhood.endpoint.authorize.methods;

import com.ampro.robinhood.Configuration;
import com.ampro.robinhood.endpoint.authorize.data.Token;
import com.ampro.robinhood.net.request.RequestMethod;
import io.github.openunirest.http.exceptions.UnirestException;

/**
 * An {@link com.ampro.robinhood.net.ApiMethod} to log the user in. This sends
 * the username and password, and returns the token needed to authorize any more
 * account-specific requests.
 *
 * @author Jonathan Augustine
 */
public class AuthorizeWithoutMultifactor extends Authorize {

    /**
     * An {@link com.ampro.robinhood.net.ApiMethod} to log the user in. This
     * sends the email and password, and returns the token needed to
     * authorize any more account-specific requests.
     *
     * @param username The username or email used to log into Robinhood
     * @param password The password
     *
     * @author Jonathan Augustine
     */
    public AuthorizeWithoutMultifactor(String username, String password)
    throws UnirestException {
        super(Configuration.getDefault());

        setUrlBase("https://api.robinhood.com/api-token-auth/");
        //Add the parameters into the request
        this.addFieldParameter("username", username);
        this.addFieldParameter("password", password);

        this.addHeaderParameter("Content-Type",
                                "application/x-www-form-urlencoded");

        //This needs to be ran as POST
        this.setMethodType(RequestMethod.POST);
        //Declare what the response should look like
        this.setReturnType(Token.class);

    }
}
