package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataJpaModelRepository extends JpaRepository<Model, Long> {

    List<Model> findByModelType(String type);

}
