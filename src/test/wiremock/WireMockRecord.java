package fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface WireMockRecord {

	boolean record() default false;

	boolean trace() default false;

}