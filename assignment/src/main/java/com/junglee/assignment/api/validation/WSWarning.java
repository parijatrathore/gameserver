package com.junglee.assignment.api.validation;


public class WSWarning {
	private int    code;
	private String message;
	private String description;

	public WSWarning() {

	}

	/**
	 * @param code
	 * @param message
	 */
	public WSWarning(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @param code
	 * @param message
	 * @param description
	 */
	public WSWarning(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.setDescription(description);
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
