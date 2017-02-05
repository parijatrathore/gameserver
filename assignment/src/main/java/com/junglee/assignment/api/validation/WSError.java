package com.junglee.assignment.api.validation;

import java.util.Map;

public class WSError {
	private int                 code;
	private String              fieldName;
	private String              description;
	private String              message;
	private Map<String, Object> errorParams;

	public WSError() {

	}

	/**
	 * @param desc
	 */
	public WSError(String description) {
		this.description = description;
	}

	/**
	 * @param code
	 * @param message
	 */
	public WSError(int code, String message) {
		this.code = code;
		this.message = message;
		this.description = message;
	}

	/**
	 * @param code
	 * @param message
	 */
	public WSError(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	/**
	 * @param code
	 * @param message
	 */
	public WSError(int code, String message, String fieldName, String description) {
		this.code = code;
		this.message = message;
		this.fieldName = fieldName;
		this.description = description;
	}

	public WSError(int code, String message, String description, Map<String, Object> errorParams) {
		this.code = code;
		this.message = message;
		this.description = description;
		this.errorParams = errorParams;
	}

	public WSError(int code, String message, String fieldName, String description, Map<String, Object> errorParams) {
		this.code = code;
		this.message = message;
		this.fieldName = fieldName;
		this.description = description;
		this.errorParams = errorParams;
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

	public Map<String, Object> getErrorParams() {
		return errorParams;
	}

	public void setErrorParams(Map<String, Object> errorParams) {
		this.errorParams = errorParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WsError [code=" + code + ", description=" + description + ", message=" + message + "]";
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
