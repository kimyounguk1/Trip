package Deepin.TripPlus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -849500732L;

    public static final QUser user = new QUser("user");

    public final DatePath<java.util.Date> birth = createDate("birth", java.util.Date.class);

    public final ListPath<Course, QCourse> courses = this.<Course, QCourse>createList("courses", Course.class, QCourse.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Inquire, QInquire> inquires = this.<Inquire, QInquire>createList("inquires", Inquire.class, QInquire.class, PathInits.DIRECT2);

    public final BooleanPath isFirst = createBoolean("isFirst");

    public final BooleanPath isSuspended = createBoolean("isSuspended");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<Rating, QRating> ratings = this.<Rating, QRating>createList("ratings", Rating.class, QRating.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final StringPath tripType = createString("tripType");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

