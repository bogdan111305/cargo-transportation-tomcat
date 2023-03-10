package com.example.cargo_transportation.validations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public abstract class EntityManagerConstraintValidator<T>{

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManagerConstraintValidator(){}

    public abstract boolean isValidInSession(T value, ConstraintValidatorContext context);

    public boolean isValid(T value, ConstraintValidatorContext context){
        return isValidInSession(value, context);
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

}
