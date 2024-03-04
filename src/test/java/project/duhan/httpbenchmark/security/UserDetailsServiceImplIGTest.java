package project.duhan.httpbenchmark.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import project.duhan.httpbenchmark.domain.User;
import project.duhan.httpbenchmark.domain.UserRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserDetailsServiceImplIGTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @DisplayName("로그인하지 않은 경우 Unauthorized를 응답한다")
    @Test
    void returnUnauthorized_whenNotLogin() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /*
     * @WithUserDetails는 실제 구현체를 통해 user@example.com 유저를 찾는다
     * */
    @DisplayName("@WithUserDetails로 인증처리한다")
    @Test
    @WithUserDetails("user@example.com")
    void authenticate_WithUserDetails() throws Exception {
        User user = User.builder().email("user@example.com").password("password").build();
        userRepository.save(user);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("user@example.com")));
    }

    /*
     * @WithUser는 실제 구현체를 통해 user@example.com 유저를 찾는다
     * */
    @DisplayName("@WithUser로 인증처리한다")
    @Test
    @WithUser
    void authenticate_WithUser() throws Exception {
        User user = User.builder().email("user@example.com").password("password").build();
        userRepository.save(user);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("user@example.com")));
    }


    @DisplayName("@WithMockCustomUser로 인증처리한다")
    @Test
    @WithMockCustomUser(email = "mockUser@example.com")
    void authenticate_WithMockCustomUser() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("mockUser@example.com")));
    }

    @DisplayName("@WithMockCustomAdmin으로 인증처리한다")
    @Test
    @WithMockCustomAdmin
    void authenticate_WithMockCustomAdmin() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("admin@example.com")));
    }

}