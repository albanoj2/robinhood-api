package com.ampro.robinhood.net.request;

import com.ampro.robinhood.net.ApiMethod;
import com.ampro.robinhood.throwables.RobinhoodApiException;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import static java.lang.Void.*;

/**
 * Singleton for making HTTP(S) requests with {@link ApiMethod}
 * @author Conrad Weise, modified by Jonathan Augustine
 */
public class RequestManager {

    private static Gson gson = new Gson();

	/**
	 * Singleton instance of this class.
	 * Only one instance is used for future ratelimiting support
	 */
	private static RequestManager instance = null;

	/**
	 * Method to get the active instance of the RequestManager.
	 * If one does not exist, it creates one
	 */
	public static RequestManager getInstance() {
		if(RequestManager.instance == null) {
            //All methods get json responses
		    Unirest.setDefaultHeader("Accept", "appliation/json");
			RequestManager.instance = new RequestManager();
		}
		return RequestManager.instance;
	}

	public <T> T makeApiRequest(ApiMethod method) throws RobinhoodApiException {

		T response = null;

		//Which request type are we using? Delegate it to the proper method
		switch(method.getMethodType()) {
		case DELETE:
			break;
		case GET: response = this.makeGetRequest(method);
			break;
		case HEAD:
			break;
		case OPTIONS:
			break;
		case POST: response = this.makePostRequest(method);
			break;
		case PUT:
			break;
		case TRACE:
			break;
		default:
			break;
		}

		return response;
	}

	/**
	 * Method which uses OKHTTP to send a POST request to the specified URL saved
	 * within the APIMethod class
	 * @throws UnirestException
	 */
	@SuppressWarnings("unchecked")
	private <T> T makePostRequest(ApiMethod method)
            throws RobinhoodApiException {


        HttpRequest request = Unirest.post(method.getBaseUrl())
                                         .headers(method.getHeaderParameters())
                                         .queryString(method.getQueryParameters())
                                         .fields(method.getFieldParameters())
                                         .getHttpRequest();
        method.getRouteParameters().forEach(request::routeParam);

        return makeRequest(request, method);
    }

	/**
	 * Method which uses Unirest to send a GET request to the specified URL saved
	 * within the ApiMethod class
	 * @throws UnirestException
	 */
	private <T> T makeGetRequest(ApiMethod method) throws RobinhoodApiException {

        HttpRequest request =
                Unirest.get(method.getBaseUrl())
                       .headers(method.getHeaderParameters())
                       .queryString(method.getQueryParameters())
                       .getHttpRequest();
        method.getRouteParameters().forEach(request::routeParam);

        return makeRequest(request, method);
	}

	private <T> T makeRequest(HttpRequest request, ApiMethod method)
    throws RobinhoodApiException {
        try {
            //Make the request
            String responseJson = request.asJson().getBody().toString();

            //If the response type for this is VOID (
            //Meaning we are not expecting a response) do not
            //try to use Gson
            if(method.getReturnType() == TYPE) {
                return (T) TYPE;
            }

            return gson.fromJson(responseJson, method.getReturnType());

        } catch (UnirestException ex) {
            System.err.println(
                    "[RobinhoodApi] Failed to communicate with endpoint"
            );
            ex.printStackTrace();
        }
        throw new RobinhoodApiException("Failed to communicate with endpoint");
    }

}
