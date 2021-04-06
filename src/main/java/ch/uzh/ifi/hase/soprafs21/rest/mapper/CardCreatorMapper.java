package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import org.mapstruct.Qualifier;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Qualifier
@Target(ElementType.METHOD)
public @interface CardCreatorMapper{}
