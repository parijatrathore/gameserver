package com.junglee.assignment.api.validation;

/**
 * Have response codes for a particular functionality grouped at one place and
 * do not scatter them all over in the class. Start a new series of number if
 * its a new feature. Generic ones should are grouped at the start
 *
 * @author parijatrathore
 * @author ParijatRathore
 *
 */
public class ResponseCode {

	/**
	 * Generic
	 */
	public static final ResponseCode	MISSING_REQUIRED_PARAMETERS		= new ResponseCode(1001, "MISSING_REQUIRED_PARAMETERS");
	public static final ResponseCode	INVALID_FORMAT					= new ResponseCode(1002, "INVALID_FORMAT");
	public static final ResponseCode	ALPHANUMERIC_FORMAT				= new ResponseCode(1003, "Only alpha numeric characters allowed.");
	public static final ResponseCode	INVALID_REQUEST					= new ResponseCode(1000, "INVALID_REQUEST");
	public static final ResponseCode	INTERNAL_ERROR					= new ResponseCode(1004, "Internal Error");
	public static final ResponseCode	INVALID_API_REQUEST				= new ResponseCode(1005, "INVALID_API_REQUEST");

	private final int					code;
	private final String				message;

	public ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

}
