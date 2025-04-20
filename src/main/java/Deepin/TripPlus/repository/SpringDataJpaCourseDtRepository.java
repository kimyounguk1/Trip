package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaCourseDtRepository extends JpaRepository<CourseDetail, Long> {

}
