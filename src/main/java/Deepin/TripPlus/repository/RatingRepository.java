package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Model;
import Deepin.TripPlus.entity.QModel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
class RatingRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public RatingRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Model findApplyModel(String modelType, LocalDateTime createDate){

        QModel model = QModel.model;

        Model result = query
                .select(model)
                .from(model)
                .where(
                        model.modelType.eq(modelType)
                                .and(model.applyDate.loe(createDate))
                )
                .orderBy(model.applyDate.desc())
                .limit(1)
                .fetchOne();

        return result;

    }

}
