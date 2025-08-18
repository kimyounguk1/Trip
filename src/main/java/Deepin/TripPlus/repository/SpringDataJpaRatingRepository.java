package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaRatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.modelName = :modelName")
    Double findAverageScoreByModelName(@Param("modelName") String modelName);
}
