package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardParticipant is a Querydsl query type for BoardParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardParticipant extends EntityPathBase<BoardParticipant> {

    private static final long serialVersionUID = -1113157219L;

    public static final QBoardParticipant boardParticipant = new QBoardParticipant("boardParticipant");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> boardParticipantBoardId = createNumber("boardParticipantBoardId", Integer.class);

    public final NumberPath<Integer> boardParticipantId = createNumber("boardParticipantId", Integer.class);

    public final NumberPath<Integer> boardParticipantUserId = createNumber("boardParticipantUserId", Integer.class);

    public QBoardParticipant(String variable) {
        super(BoardParticipant.class, forVariable(variable));
    }

    public QBoardParticipant(Path<? extends BoardParticipant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardParticipant(PathMetadata metadata) {
        super(BoardParticipant.class, metadata);
    }

}

