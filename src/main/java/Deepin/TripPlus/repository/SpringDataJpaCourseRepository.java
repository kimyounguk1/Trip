package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.entity.Model;
import Deepin.TripPlus.entity.QModel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataJpaCourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(long id);


}
