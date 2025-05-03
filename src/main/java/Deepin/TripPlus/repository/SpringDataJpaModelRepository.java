package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaModelRepository extends JpaRepository<Model, Long> {

    List<Model> findByModelType(String type);

    Optional<Model> findByModelTypeAndApplyDateIsNotNull(String modelType);
}
