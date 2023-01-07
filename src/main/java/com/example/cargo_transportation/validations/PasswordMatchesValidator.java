package com.example.cargo_transportation.validations;

import com.example.cargo_transportation.annotations.PasswordMatches;
import com.example.cargo_transportation.payload.request.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) object;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}