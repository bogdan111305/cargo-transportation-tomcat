package com.example.cargo_transportation.dto.mapper;

import jakarta.persistence.Entity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.CharUtils;
import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

@Log4j2
public class CustomMapper implements Mapper{

    private static final String IDENTIFICATION_PARAM = "Id";
    private ModelMapper modelMapper;

    public CustomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <D> D defaultMap(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public <D> D mapToDTOWithSpecificFields(Object source, Class<D> destinationType) {
        D d = modelMapper.map(source, destinationType);
        mapSpecificFields(source, d);
        return d;
    }

    private <D> void mapSpecificFields(Object source, D d) {
        if (d.getClass().getAnnotation(Entity.class) == null) {
            Arrays.stream(d.getClass().getDeclaredFields())
                .filter(field -> isSpecificField(field, d))
                .forEach(field -> setSpecificField(field, source, d));
        }
    }

    private <D> boolean isSpecificField(Field field, D d) {
        try {
            return field.getName().contains(IDENTIFICATION_PARAM) &&
                        !StringUtils.capitalize(field.getName()).equals(IDENTIFICATION_PARAM) &&
                            d.getClass().getDeclaredMethod("get" + StringUtils.capitalize(field.getName())).invoke(d) == null;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("An error occurred searching for specific fields: " + e.getLocalizedMessage(), e);
            return false;
        }
    }

    private <D> void setSpecificField(Field field, Object source, D d) {
        try {
            String searchName = field.getName().replace(IDENTIFICATION_PARAM, "");
            Object model = source.getClass()
                    .getDeclaredMethod("get" + StringUtils.capitalize(searchName))
                    .invoke(source);
            if (model != null) {
                Method method = d.getClass().getDeclaredMethod("set" + StringUtils.capitalize(field.getName()));
                Object parameter = model.getClass().getDeclaredMethod("get" + IDENTIFICATION_PARAM).invoke(model);
                method.invoke(d, parameter);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("An error occurred while getting fields: " + e.getLocalizedMessage(), e);
        }
    }
}
