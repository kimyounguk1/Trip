package Deepin.TripPlus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = -844955692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourse course = new QCourse("course");

    public final StringPath area = createString("area");

    public final QCourseDetail courseDt;

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DatePath<java.util.Date> endDate = createDate("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath meansTp = createString("meansTp");

    public final StringPath modelName = createString("modelName");

    public final StringPath modelType = createString("modelType");

    public final StringPath person = createString("person");

    public final BooleanPath rated = createBoolean("rated");

    public final ListPath<Rating, QRating> ratings = this.<Rating, QRating>createList("ratings", Rating.class, QRating.class, PathInits.DIRECT2);

    public final DatePath<java.util.Date> startDate = createDate("startDate", java.util.Date.class);

    public final StringPath title = createString("title");

    public final StringPath tripType = createString("tripType");

    public final QUser user;

    public QCourse(String variable) {
        this(Course.class, forVariable(variable), INITS);
    }

    public QCourse(Path<? extends Course> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourse(PathMetadata metadata, PathInits inits) {
        this(Course.class, metadata, inits);
    }

    public QCourse(Class<? extends Course> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.courseDt = inits.isInitialized("courseDt") ? new QCourseDetail(forProperty("courseDt"), inits.get("courseDt")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

