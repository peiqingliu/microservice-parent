package com.liupeiqing.spring.cloud.utils;


import com.liupeiqing.spring.cloud.exception.CheckedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author liupeiqing
 */
public class Assert {

	private static Validator	validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static void validateEntity(Object object, Class<?>... groups) throws CheckedException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				msg.append(constraint.getMessage()).append("<br>");
			}
			throw new CheckedException(msg.toString());
		}
	}

	public static void isBlank(String str, String message) {
		if (StringHelper.isBlank(str)) { throw new CheckedException(message); }
	}

	public static void isNull(Object object, String message) {
		if (object == null) { throw new CheckedException(message); }
	}
}
