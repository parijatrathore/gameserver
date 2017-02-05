package com.junglee.assignment.api.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationContext {
	private final List<WSError>   errors   = Collections.synchronizedList(new ArrayList<WSError>());
	private final List<WSWarning> warnings = Collections.synchronizedList(new ArrayList<WSWarning>());

	public void addError(ResponseCode responseCode) {
		this.errors.add(new WSError(responseCode.code(), responseCode.message(), responseCode.message()));
	}

	public void addErrors(List<WSError> errors) {
		this.errors.addAll(errors);
	}

	public void addError(ResponseCode responseCode, String description) {
		this.errors.add(new WSError(responseCode.code(), responseCode.message(), description));
	}

	public void addError(ResponseCode responseCode, String fieldName, String description) {
		this.errors.add(new WSError(responseCode.code(), responseCode.message(), fieldName, description));
	}

	public void addError(ResponseCode responseCode, String description, Map<String, Object> errorParams) {
		this.errors.add(new WSError(responseCode.code(), responseCode.message(), description, errorParams));
	}

	public void addError(ResponseCode responseCode, String fieldName, String description, Map<String, Object> errorParams) {
		this.errors.add(new WSError(responseCode.code(), responseCode.message(), fieldName, description, errorParams));
	}

	public List<WSError> getErrors() {
		return errors;
	}

	public void addWarning(ResponseCode responseCode) {
		this.warnings.add(new WSWarning(responseCode.code(), responseCode.message()));
	}

	public void addWarning(ResponseCode responseCode, String description) {
		this.warnings.add(new WSWarning(responseCode.code(), responseCode.message(), description));
	}

	public List<WSWarning> getWarnings() {
		return warnings;
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	/**
	 * @return
	 */
	public boolean hasWarnings() {
		return warnings.size() > 0;
	}
}
