package robinhood.api.throwables;

@SuppressWarnings("serial")
public class RobinhoodApiException extends Exception {
	
	private String error = "A problem has occured within the Robinhood API library."
			+ " Please consider filing a big report if the issue is not obvious!";
	
	public RobinhoodApiException() {
		
		//Do nothing. Merely allow the object to be created using the default message
	}
	
	public RobinhoodApiException(String errorMessage) {
		
		this.error = errorMessage;
		
	}
	
	@Override public String getMessage() {
		
		return this.error;
		
	}
	
	

}
