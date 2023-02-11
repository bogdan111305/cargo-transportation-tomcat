package com.example.cargo_transportation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({})
@Retention(RUNTIME)
public @interface UniqueColumn{

    String[] fields() default {};

    String[] orValue() default {};
}
