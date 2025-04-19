package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.Exception.CustomException;
import Deepin.TripPlus.Exception.ErrorCode;
import Deepin.TripPlus.auth.dto.CourseDto;
import Deepin.TripPlus.auth.dto.HomeDto;
import Deepin.TripPlus.auth.dto.OnboardingDto;
import Deepin.TripPlus.auth.dto.RegisterDto;
import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.repository.SpringDataJpaUserRepository;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SpringDataJpaUserRepository jpaUserRepository;

    private final JWTUtil jwtUtil;

    @Transactional
    @Override
    public void registerProcess(RegisterDto registerDto) {

        String email = registerDto.getEmail();
        String password = registerDto.getPassword();
        String name = registerDto.getName();

        Boolean isExist = jpaUserRepository.existsByEmail(email);

        if(isExist) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User data = new User();
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_CLIENT");
        data.setName(name);
        data.setFirst(true);
        data.setSuspended(false);


        jpaUserRepository.save(data);

    }

    @Transactional
    @Override
    public void onboardingProcess(OnboardingDto onboardingDto, HttpServletRequest request) {

        String gender = onboardingDto.getGender();
        String userTripType = onboardingDto.getUserTripType();
        Date birth = onboardingDto.getBirth();

        String email = emailProcess(request);

        User data = jpaUserRepository.findByEmail(email);
        if(data != null) {
            data.setGender(gender);
            data.setTripType(userTripType);
            data.setBirth(birth);
            data.setFirst(false);
        }else{
            throw new CustomException(ErrorCode.NON_EXIST_USER);
        }
    }

    @Override
    public HomeDto homeProcess(HttpServletRequest request) {

        String email = emailProcess(request);
        Date today = new Date();

        User data = jpaUserRepository.findByEmailWithCourses(email, today).orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_USER));
        boolean isFirst = data.isFirst();
        List<CourseDto> courseDto =
                data.getCourses().stream()
                .map(course-> new CourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getArea(),
                        course.getStartDate().toString(),
                        course.getEndDate().toString(),
                        course.getMeansTp()))
                .collect(Collectors.toList());

        HomeDto homeDto = new HomeDto();
        homeDto.setIsFirst(isFirst);
        homeDto.setCourse(courseDto);

        return homeDto;
    }

    public String emailProcess(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        String token = authorization.split(" ")[1];
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");

            //토큰 만료
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }
        return jwtUtil.getEmail(token);
    }

}
