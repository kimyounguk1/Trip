package Deepin.TripPlus.rating.service;

import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.entity.Rating;
import Deepin.TripPlus.entity.User;
import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
import Deepin.TripPlus.rating.ratingDto.RatingDto;
import Deepin.TripPlus.repository.SpringDataJpaCourseRepository;
import Deepin.TripPlus.repository.SpringDataJpaRatingRepository;
import Deepin.TripPlus.repository.SpringDataJpaUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final JWTUtil jwtUtil;
    private final SpringDataJpaUserRepository userRepository;
    private final SpringDataJpaCourseRepository courseRepository;
    private final SpringDataJpaRatingRepository ratingRepository;


    @Override
    @Transactional
    public Rating ratingProcess(HttpServletRequest request, RatingDto ratingDto) {

        User user = extracted(request);

        Long courseId = ratingDto.getCourseId();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_COURSE));
        String modelName = course.getModelName();

        Double score = ratingDto.getScore();

        Rating rating = new Rating();
        rating.setScore(score);
        rating.setModelName(modelName);

        course.addRating(rating);
        user.addRating(rating);

        ratingRepository.save(rating);

        return rating;
    }

    private User extracted(HttpServletRequest request) {
        String email = emailProcess(request);
        User user = userRepository.findByEmail(email);
        return user;
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
