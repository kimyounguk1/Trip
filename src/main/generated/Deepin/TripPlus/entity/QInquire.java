package Deepin.TripPlus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquire is a Querydsl query type for Inquire
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquire extends EntityPathBase<Inquire> {

    private static final long serialVersionUID = 573989146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquire inquire = new QInquire("inquire");

    public final StringPath answer = createString("answer");

    public final DateTimePath<java.time.LocalDateTime> answeredDate = createDateTime("answeredDate", java.time.LocalDateTime.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAnswered = createBoolean("isAnswered");

    public final StringPath title = createString("title");

    public final QUser user;

    public QInquire(String variable) {
        this(Inquire.class, forVariable(variable), INITS);
    }

    public QInquire(Path<? extends Inquire> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquire(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquire(PathMetadata metadata, PathInits inits) {
        this(Inquire.class, metadata, inits);
    }

    public QInquire(Class<? extends Inquire> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

