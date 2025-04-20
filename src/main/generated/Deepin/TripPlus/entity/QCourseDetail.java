package Deepin.TripPlus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseDetail is a Querydsl query type for CourseDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseDetail extends EntityPathBase<CourseDetail> {

    private static final long serialVersionUID = 698340677L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseDetail courseDetail = new QCourseDetail("courseDetail");

    public final QCourse course;

    public final DatePath<java.util.Date> day = createDate("day", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath placeAddress = createString("placeAddress");

    public final StringPath placeLat = createString("placeLat");

    public final StringPath placeLon = createString("placeLon");

    public final StringPath placeName = createString("placeName");

    public final StringPath placeType = createString("placeType");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QCourseDetail(String variable) {
        this(CourseDetail.class, forVariable(variable), INITS);
    }

    public QCourseDetail(Path<? extends CourseDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseDetail(PathMetadata metadata, PathInits inits) {
        this(CourseDetail.class, metadata, inits);
    }

    public QCourseDetail(Class<? extends CourseDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
    }

}

