package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1308556754L;

    public static final QCategory category = new QCategory("category");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> categoryArea = createNumber("categoryArea", Integer.class);

    public final NumberPath<Integer> categoryBoardId = createNumber("categoryBoardId", Integer.class);

    public final NumberPath<Integer> categorySeason = createNumber("categorySeason", Integer.class);

    public final NumberPath<Integer> categoryTheme = createNumber("categoryTheme", Integer.class);

    public final NumberPath<Integer> categoryWithWhom = createNumber("categoryWithWhom", Integer.class);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

