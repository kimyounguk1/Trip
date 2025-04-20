package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaRatingRepository extends JpaRepository<Rating, Long> {

}
