package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Inquire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataJpaInquireRepository extends JpaRepository<Inquire, Long> {

    @Query("select i from Inquire i LEFT JOIN FETCH i.user")
    List<Inquire> findAllWithUser();

}
