package project.duhan.httpbenchmark.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@AuthenticationPrincipal
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
