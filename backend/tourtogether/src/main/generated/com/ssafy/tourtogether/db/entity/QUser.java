package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -167294725L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath userClientId = createString("userClientId");

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath userJoindate = createString("userJoindate");

    public final NumberPath<Integer> userLoginPlatform = createNumber("userLoginPlatform", Integer.class);

    public final StringPath userName = createString("userName");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPhone = createString("userPhone");

    public final StringPath userProfileImage = createString("userProfileImage");

    public final NumberPath<Integer> userScore = createNumber("userScore", Integer.class);

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

