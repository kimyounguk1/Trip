package Deepin.TripPlus.course.service;

import Deepin.TripPlus.auth.dto.CourseDto;
import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.repository.SpringDataJpaCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final SpringDataJpaCourseRepository courseRepository;

    public List<CourseDto> getCoursesByUserId(Long userId) {
        List<Course> courseList = courseRepository.findByUser_Id(userId);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return courseList.stream()
                .map(course -> new CourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getArea(),
                        course.getTripType(),
                        formatter.format(course.getStartDate()),
                        formatter.format(course.getEndDate())
                ))
                .collect(Collectors.toList());
    }
}
