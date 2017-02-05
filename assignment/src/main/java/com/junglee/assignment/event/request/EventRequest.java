package com.junglee.assignment.event.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import com.junglee.assignment.api.validation.ResponseCode;
import com.junglee.assignment.api.validation.ValidationContext;

public class EventRequest implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1717528547953351414L;

	private Integer				event;

	public Integer getEvent() {
		return event;
	}

	public void setEvent(Integer event) {
		this.event = event;
	}

	public ValidationContext validate(boolean validateOptionalBlanks) {
		ValidationContext context = new ValidationContext();
		validate(this, context, validateOptionalBlanks);
		return context;

	}

	public ValidationContext validate() {
		return validate(true);
	}

	public static <T> ValidationContext validate(T input, ValidationContext context, boolean validateOptionalBlanks) {
		Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(input);
		if (violations.size() > 0) {
			for (ConstraintViolation<T> violation : violations) {
				if (validateOptionalBlanks /*
											 * ||
											 * !violation.getConstraintDescriptor
											 * (
											 * ).getAnnotation().annotationType(
											 * ).equals(OptionalBlank.class) &&
											 * !
											 * violation.getConstraintDescriptor
											 * (
											 * ).getAnnotation().annotationType(
											 * ).equals(OptionalNull.class)
											 */) {
					context.addError(ResponseCode.MISSING_REQUIRED_PARAMETERS, violation.getPropertyPath().toString(),
							violation.getPropertyPath() + " " + violation.getMessage());
				}
			}
		}
		return context;
	}

}
