package com.product.config.anotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // This annotation can be applied to methods
@Retention(RetentionPolicy.RUNTIME) // This annotation will be retained at runtime
public @interface ValidateToken {
    String value() default ""; // Optional value if needed
}