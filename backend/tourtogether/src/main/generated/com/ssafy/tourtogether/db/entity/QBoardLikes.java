package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardLikes is a Querydsl query type for BoardLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardLikes extends EntityPathBase<BoardLikes> {

    private static final long serialVersionUID = 1344830886L;

    public static final QBoardLikes boardLikes = new QBoardLikes("boardLikes");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> boardLikesBoardId = createNumber("boardLikesBoardId", Integer.class);

    public final NumberPath<Integer> boardLikesId = createNumber("boardLikesId", Integer.class);

    public final NumberPath<Integer> boardLikesUserId = createNumber("boardLikesUserId", Integer.class);

    public QBoardLikes(String variable) {
        super(BoardLikes.class, forVariable(variable));
    }

    public QBoardLikes(Path<? extends BoardLikes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardLikes(PathMetadata metadata) {
        super(BoardLikes.class, metadata);
    }

}

