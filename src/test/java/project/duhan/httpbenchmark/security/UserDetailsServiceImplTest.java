package project.duhan.httpbenchmark.security;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import project.duhan.httpbenchmark.domain.User;
import project.duhan.httpbenchmark.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @DisplayName("이메일로 아이디를 찾은 경우 UserDetails를 반환한다")
    @Test
    void returnUserDetails_whenFoundUser_throughEmail() {
        User user = User.builder().email("user@example.com").password("password").build();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        UserDetails actual = userDetailsServiceImpl.loadUserByUsername("user@example.com");

        assertThat(actual).isInstanceOf(UserDetails.class);
        assertThat(actual.getUsername()).isEqualTo("user@example.com");
        assertThat(actual.getPassword()).isEqualTo("password");
    }


}