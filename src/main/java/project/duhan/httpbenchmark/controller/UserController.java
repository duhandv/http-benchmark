package project.duhan.httpbenchmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.duhan.httpbenchmark.controller.dto.UserRegisterRequest;
import project.duhan.httpbenchmark.domain.User;
import project.duhan.httpbenchmark.security.CurrentUser;
import project.duhan.httpbenchmark.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User userDetails(@CurrentUser User user) {
        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest request) {
        userService.register(request.email(), request.password());
        return ResponseEntity.ok().build();
    }

}
