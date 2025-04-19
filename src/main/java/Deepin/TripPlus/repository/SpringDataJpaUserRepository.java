package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;


public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.courses c " +
            "WHERE u.email = :email " +
            "AND :today BETWEEN c.startDate AND c.endDate")
    Optional<User> findByEmailWithCourses(@Param("email") String email, @Param("today") Date today);

    @Query("SELECT u From User u LEFT JOIN FETCH u.inquires WHERE u.email = :email")
    User findByEmailWithInquires(@Param("email") String email);
}
