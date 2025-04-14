package Deepin.TripPlus.auth.repository;

import Deepin.TripPlus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

}
