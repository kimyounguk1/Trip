package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
import Deepin.TripPlus.auth.dto.CustomUserDetails;
import Deepin.TripPlus.repository.SpringDataJpaUserRepository;

import Deepin.TripPlus.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final SpringDataJpaUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("입력받은 email = {}", email);
        User user = userRepository.findByEmail(email);
        log.info("찾은 user = {}", user);
        if (user == null) {
            throw new CustomException(ErrorCode.NON_EXIST_USER);
        }
        return new CustomUserDetails(user);
    }
}
