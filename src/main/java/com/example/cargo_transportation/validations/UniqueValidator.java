package com.example.cargo_transportation.validations;

import java.util.*;
import java.util.stream.Collectors;

import com.example.cargo_transportation.annotations.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class UniqueValidator extends SessionAwareConstraintValidator<Object> implements ConstraintValidator<Unique, Object>{

    private Class<?> entity;

    @Override
    public void initialize(Unique annotation){
        this.entity = annotation.entity();
    }

    public boolean isValidInSession(Object value, ConstraintValidatorContext context){
        if(value == null) return true;

        Map<String, Object> fieldMap = sendQueryByParam(value);
        if(fieldMap != null && !fieldMap.isEmpty()){
            setConstraintValidatorContext(fieldMap, context);
            return false;
        }

        return true;
    }

    private void setConstraintValidatorContext(Map<String, Object> fieldMap, ConstraintValidatorContext context) {
        for (Map.Entry<String, Object> field : fieldMap.entrySet()) {
            HibernateConstraintValidatorContext constraintValidator = context.unwrap(HibernateConstraintValidatorContext.class);
            constraintValidator.disableDefaultConstraintViolation();

            constraintValidator.buildConstraintViolationWithTemplate(
                    "Already " + entity.getSimpleName().toLowerCase(Locale.ROOT) +
                    " with field: " + field.getKey() + " = " + field.getValue())
                    .addPropertyNode(field.getKey())
                    .addConstraintViolation();
        }
    }

    private Map<String, Object> sendQueryByParam(Object value) {
        Map<String, Object> searchField = getMapFieldFromDTO(value);
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> field : searchField.entrySet()) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root r = cq.from(entity);
            ParameterExpression<String> pe = cb.parameter(String.class, field.getKey());
            cq.select(r).where(cb.equal(r.get(field.getKey()), pe));
            if (getEntityManager().createQuery(cq).setParameter(field.getKey(), field.getValue()).getSingleResult() != null) {
                result.put(field.getKey(), field.getValue());
            }
        }
        return result;
    }

    private Map<String, Object> getMapFieldFromDTO(Object value) {
        List<String> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).unique())
                .map(field -> field.getName())
                .collect(Collectors.toList());

        return Arrays.stream(value.getClass().getDeclaredFields())
                .filter(field -> fields.contains(field.getName()))
                .collect(Collectors.toMap(
                        field -> field.getName(),
                        field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                );
    }
}
