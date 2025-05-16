package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaCourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(long id);

    List<Course> findByUsers_Id(Long userId);
}
