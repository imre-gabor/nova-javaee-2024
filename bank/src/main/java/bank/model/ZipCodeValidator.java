package bank.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ZipCodeValidator implements ConstraintValidator<ZipCode, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || !value.startsWith("9");
	}

}
