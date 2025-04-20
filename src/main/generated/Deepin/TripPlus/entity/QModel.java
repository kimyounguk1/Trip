package Deepin.TripPlus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModel is a Querydsl query type for Model
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModel extends EntityPathBase<Model> {

    private static final long serialVersionUID = -572227504L;

    public static final QModel model = new QModel("model");

    public final DateTimePath<java.time.LocalDateTime> applyDate = createDateTime("applyDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath information = createString("information");

    public final StringPath modelType = createString("modelType");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> parama = createNumber("parama", Integer.class);

    public QModel(String variable) {
        super(Model.class, forVariable(variable));
    }

    public QModel(Path<? extends Model> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModel(PathMetadata metadata) {
        super(Model.class, metadata);
    }

}

