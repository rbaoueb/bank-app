package fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Inherited
public @interface WireMockParams {

	WireMockParam[] value();

}