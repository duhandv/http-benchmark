package project.duhan.httpbenchmark;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.duhan.httpbenchmark.domain.User;
import project.duhan.httpbenchmark.domain.UserRepository;

@Profile("local")
@Component
public class ApplicationListener {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ApplicationListener(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void handle() {
        User user = User.builder()
                .email("user@example.com")
                .password(passwordEncoder.encode("password"))
                .build();
        userRepository.save(user);
    }

}
