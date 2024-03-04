package project.duhan.httpbenchmark.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithMockCustomUser(email = "admin@example.com")
@Retention(RetentionPolicy.RUNTIME)
public @interface WithMockCustomAdmin {
}
