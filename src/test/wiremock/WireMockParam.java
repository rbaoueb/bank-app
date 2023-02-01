package fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Repeatable(WireMockParams.class)
public @interface WireMockParam {

	String DEFAULT_SERVER_NAME = "default";

	String testUrl();

	boolean record() default false;

	String pathPattern() default "/.*";

	String name() default DEFAULT_SERVER_NAME;

}
