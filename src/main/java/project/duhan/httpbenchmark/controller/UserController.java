package project.duhan.httpbenchmark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.duhan.httpbenchmark.domain.User;
import project.duhan.httpbenchmark.security.CurrentUser;

@RestController
public class UserController {

    @GetMapping("/users")
    public User userDetails(@CurrentUser User user) {
        return user;
    }
}
