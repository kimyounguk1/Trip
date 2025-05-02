package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        List<String> userTripTypeList = onboardingDto.getUserTripType();

        Date birth = onboardingDto.getBirth();

        String email = emailProcess(request);

        User data = jpaUserRepository.findByEmail(email);
        if (data != null) {
            data.setGender(gender);

            // List<String>을 "힐링","모험" 형식으로 변환
            String userTripTypeString = userTripTypeList.stream()
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(","));

            data.setTripType(userTripTypeString);

            data.setBirth(onboardingDto.getBirth());
            data.setFirst(false);
        } else {
            throw new CustomException(ErrorCode.NON_EXIST_USER);
        }
    }

    @Override
    public HomeDto homeProcess(HttpServletRequest request) {
        String email = emailProcess(request);
        Date today = new Date();

        User user = jpaUserRepository.findByEmailWithCourses(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXIST_USER));

        boolean isFirst = user.isFirst();

        // 오늘 날짜 기준, 종료일이 오늘 이후거나 같은 course들 중에서 가장 빠른 종료일 1개 선택
        Optional<Course> closestCourse = user.getCourses().stream()
                .filter(course -> !course.getEndDate().before(today)) // 종료일이 오늘보다 같거나 나중인 것
                .min(Comparator.comparing(Course::getEndDate)); // 종료일이 가장 가까운 하나

        List<CourseDto> courseDto = closestCourse
                .map(course -> List.of(new CourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getArea(),
                        course.getStartDate().toString(),
                        course.getEndDate().toString(),
                        course.getMeansTp())))
                .orElse(List.of()); // 없으면 빈 리스트

        return new HomeDto(isFirst, courseDto);
    }

    @Override
    public Boolean checkEmailProcess(String email) {
        Boolean value = jpaUserRepository.existsByEmail(email);
        return value;
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
