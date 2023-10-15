package com.dmtryii.internetshop.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than or equal to 0.01")
@Digits(integer = 7, fraction = 2, message = "Price must have at most 7 digits in total, with 2 digits allowed after the decimal point")
public @interface Price {
    String message() default "Invalid price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
