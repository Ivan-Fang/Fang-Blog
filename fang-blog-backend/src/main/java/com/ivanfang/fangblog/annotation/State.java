package com.ivanfang.fangblog.annotation;

import com.ivanfang.fangblog.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// used for parameter validation
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StateValidation.class)    // constraint is defined in "StateValidation"
public @interface State {

    String message() default "status can only be 'draft' or 'released'";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
