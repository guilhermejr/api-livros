package net.guilhermejr.apilivros.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.guilhermejr.apilivros.model.validador.ISBNUnicoValidador;

@Constraint(validatedBy = ISBNUnicoValidador.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBNUnico {
    String message() default "ISBN já está cadastrado.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
