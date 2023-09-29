package com.dmtryii.usersystem.annotation.validator;

import com.dmtryii.usersystem.annotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private Pattern pattern;

    @Override
    public void initialize(PhoneNumber phoneNumber) {
        pattern = Pattern.compile(phoneNumber.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return pattern.matcher(value).matches();
    }
}
