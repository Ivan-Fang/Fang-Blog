package com.ivanfang.fangblog.validation;

import com.ivanfang.fangblog.annotation.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (!value.equals("draft") && !value.equals("released")) {
            return false;
        }

        return true;
    }

}
