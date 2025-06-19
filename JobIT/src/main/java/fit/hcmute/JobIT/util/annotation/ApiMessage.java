package fit.hcmute.JobIT.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This annotation is used to mark methods that return API messages
@Retention(RetentionPolicy.RUNTIME)
// This annotation can be used to annotate methods that return API messages
@Target(ElementType.METHOD)
public @interface ApiMessage {
    // The value of the annotation is the message that will be returned by the API
    String value();
}
