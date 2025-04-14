package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.auth.dto.LoginDto;
import Deepin.TripPlus.auth.repository.SpringDataJpaUserRepository;
import Deepin.TripPlus.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SpringDataJpaUserRepository jpaUserRepository;

    @Override
    public void joinProcess(LoginDto loginDto) {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Boolean isExist = jpaUserRepository.existsByEmail(email);

        if(isExist) {
            return;
        }

        User data = new User();
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        jpaUserRepository.save(data);


    }
}
