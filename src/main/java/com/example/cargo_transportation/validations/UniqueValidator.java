package com.example.cargo_transportation.validations;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.cargo_transportation.annotations.Unique;
import com.example.cargo_transportation.annotations.UniqueColumn;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UniqueValidator extends SessionAwareConstraintValidator implements ConstraintValidator<Unique, Object>, MessageSourceAware{

    private Class<?> entity;
    private MessageSource _messageSource;

    @Override
    public void initialize(Unique annotation){
        entity = annotation.entity();
    }

    public boolean isValidInSession(Object value, ConstraintValidatorContext context){
        if(value == null) return true;

        Map<String, Object> fieldMap = sendQueryByParam(value);
        if(fieldMap != null){
            String message = getMessageSource().getMessage(context.getDefaultConstraintMessageTemplate(), null, LocaleContextHolder.getLocale());
            Map.Entry<String, Object> field = fieldMap.entrySet().iterator().next();
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addExpressionVariable("name", value.getClass().getSimpleName())
                    .addExpressionVariable("fullName", value.getClass().getName())
                    .addExpressionVariable("field", field.getKey())
                    .addExpressionVariable("value", field.getValue())
                    .addExpressionVariable("allFields", StringUtils.join(fieldMap.keySet(), ", "))
                    .addExpressionVariable("values", StringUtils.join(fieldMap.values(), ", "))
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field.getKey())
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        return true;
    }

    private Map<String, Object> sendQueryByParam(Object value) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root root = criteriaQuery.from(entity);
        List<Predicate> predicates = new ArrayList<>();
        return new HashMap<>();
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

    @Override
    public void setMessageSource(MessageSource messageSource){
        this._messageSource = messageSource;
    }

    public MessageSource getMessageSource(){
        return this._messageSource;
    }

    private static <T> Collector<T, ?, List<T>> toListOrEmpty() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                l -> l.isEmpty() ? new ArrayList<>() : l
        );
    }

}
