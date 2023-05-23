package com.example.cargo_transportation.validations;

import com.example.cargo_transportation.annotations.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UniqueValidator extends EntityManagerConstraintValidator<Object> implements ConstraintValidator<Unique, Object> {
    private Class<?> entity;
    private List<Field> uniqueFields;

    @Override
    public void initialize(Unique annotation) {
        this.entity = annotation.entity();
        this.uniqueFields = getUniqueFields(entity);
    }

    public boolean isValidInSession(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Map<String, Object> fieldMap = sendQueryByValueUniqueFields(value);
        if (!fieldMap.isEmpty()) {
            setConstraintValidatorContext(fieldMap, context);
            return false;
        }

        return true;
    }

    private void setConstraintValidatorContext(Map<String, Object> fieldMap, ConstraintValidatorContext context) {
        HibernateConstraintValidatorContext constraintValidator = context.unwrap(HibernateConstraintValidatorContext.class);
        constraintValidator.disableDefaultConstraintViolation();

        for (Map.Entry<String, Object> fieldEntry : fieldMap.entrySet()) {
            String fieldName = fieldEntry.getKey();
            Object fieldValue = fieldEntry.getValue();

            constraintValidator.buildConstraintViolationWithTemplate(
                            "Already " + entity.getSimpleName().toLowerCase() +
                                    " with field: " + fieldName + " = " + fieldValue)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
    }

    private Map<String, Object> sendQueryByValueUniqueFields(Object value) {
        Map<String, Object> searchField = getMapUniqueFieldFromDTO(value);
        Map<String, Object> result = new HashMap<>();

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> root = criteriaQuery.from(entity);
        ParameterExpression<Object> parameter = criteriaBuilder.parameter(Object.class);
        Predicate predicate = criteriaBuilder.equal(root.get(""), parameter);

        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

        for (Map.Entry<String, Object> fieldEntry : searchField.entrySet()) {
            String fieldName = fieldEntry.getKey();
            Object fieldValue = fieldEntry.getValue();

            Field field = uniqueFields.stream()
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst()
                    .orElse(null);

            if (field != null) {
                predicate = criteriaBuilder.equal(root.get(field.getName()), parameter);
                criteriaQuery.where(predicate);
                Long count = getEntityManager()
                        .createQuery(criteriaQuery)
                        .setParameter(parameter, fieldValue)
                        .getSingleResult();

                if (count > 0) {
                    result.put(fieldName, fieldValue);
                }
            }
        }

        return result;
    }

    private Map<String, Object> getMapUniqueFieldFromDTO(Object value) {
        return uniqueFields.stream()
                .collect(Collectors.toMap(
                        Field::getName,
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

    private List<Field> getUniqueFields(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).unique())
                .collect(Collectors.toList());
    }
}
