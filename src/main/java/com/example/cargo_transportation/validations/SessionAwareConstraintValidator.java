package com.example.cargo_transportation.validations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public abstract class SessionAwareConstraintValidator<T>{

    @PersistenceContext
    private EntityManager entityManager;

    public SessionAwareConstraintValidator(){}

    public abstract boolean isValidInSession(T value, ConstraintValidatorContext context);

    public boolean isValid(T value, ConstraintValidatorContext context){
        getEntityManager().getTransaction().begin();
        boolean result = isValidInSession(value, context);
        getEntityManager().getTransaction().commit();
        return result;
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

}
