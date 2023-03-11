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
public class UniqueValidator extends EntityManagerConstraintValidator<Object> implements ConstraintValidator<Unique, Object>{
    private Class<?> entity;

    @Override
    public void initialize(Unique annotation){
        this.entity = annotation.entity();
    }

    public boolean isValidInSession(Object value, ConstraintValidatorContext context){
        if(value == null) return true;

        Map<String, Object> fieldMap = sendQueryByValueUniqueFields(value);
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

    private Map<String, Object> sendQueryByValueUniqueFields(Object value) {
        Map<String, Object> searchField = getMapUniqueFieldFromDTO(value);
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> field : searchField.entrySet()) {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
            Root root = criteriaQuery.from(entity);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class, field.getKey());
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(field.getKey()), parameter));
            List resultQuery = getEntityManager()
                    .createQuery(criteriaQuery).setParameter(field.getKey(), field.getValue())
                    .getResultList();
            if (resultQuery != null && !resultQuery.isEmpty()) {
                result.put(field.getKey(), field.getValue());
            }
        }
        return result;
    }

    private Map<String, Object> getMapUniqueFieldFromDTO(Object value) {
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
