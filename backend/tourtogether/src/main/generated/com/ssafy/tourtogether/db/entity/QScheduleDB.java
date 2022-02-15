package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleDB is a Querydsl query type for ScheduleDB
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleDB extends EntityPathBase<ScheduleDB> {

    private static final long serialVersionUID = -1081210235L;

    public static final QScheduleDB scheduleDB = new QScheduleDB("scheduleDB");

    public final StringPath scheduleAdditional = createString("scheduleAdditional");

    public final NumberPath<Integer> scheduleBoardId = createNumber("scheduleBoardId", Integer.class);

    public final NumberPath<Integer> scheduleDay = createNumber("scheduleDay", Integer.class);

    public final NumberPath<Integer> scheduleId = createNumber("scheduleId", Integer.class);

    public final NumberPath<Integer> scheduleOrd = createNumber("scheduleOrd", Integer.class);

    public final NumberPath<Integer> scheduleTourSpotId = createNumber("scheduleTourSpotId", Integer.class);

    public QScheduleDB(String variable) {
        super(ScheduleDB.class, forVariable(variable));
    }

    public QScheduleDB(Path<? extends ScheduleDB> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleDB(PathMetadata metadata) {
        super(ScheduleDB.class, metadata);
    }

}

