package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -908838986L;

    public static final QBoard board = new QBoard("board");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> boardId = createNumber("boardId", Integer.class);

    public final BooleanPath boardIsActive = createBoolean("boardIsActive");

    public final StringPath boardName = createString("boardName");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

