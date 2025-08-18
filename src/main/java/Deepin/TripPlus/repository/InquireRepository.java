package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Inquire;
import Deepin.TripPlus.entity.QInquire;
import Deepin.TripPlus.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class InquireRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public InquireRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Inquire> findByTitleOrUserName(String title, String userName) {

        QInquire inquire = QInquire.inquire;
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(title)){
            builder.and(inquire.title.containsIgnoreCase(title));
        }

        if(StringUtils.hasText(userName)){
            builder.and(user.name.containsIgnoreCase(userName));
        }

        return query.selectFrom(inquire)
                .join(inquire.users, user)
                .where(builder)
                .fetch();

    }


}
