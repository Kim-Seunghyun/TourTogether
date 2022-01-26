package com.ssafy.tourtogether.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTourSpot is a Querydsl query type for TourSpot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTourSpot extends EntityPathBase<TourSpot> {

    private static final long serialVersionUID = 2129604778L;

    public static final QTourSpot tourSpot = new QTourSpot("tourSpot");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath tourSpotAddress = createString("tourSpotAddress");

    public final StringPath tourSpotFee = createString("tourSpotFee");

    public final StringPath tourSpotHoliday = createString("tourSpotHoliday");

    public final NumberPath<Integer> tourSpotId = createNumber("tourSpotId", Integer.class);

    public final BooleanPath tourSpotIsTop100 = createBoolean("tourSpotIsTop100");

    public final NumberPath<Double> tourSpotLatitude = createNumber("tourSpotLatitude", Double.class);

    public final NumberPath<Double> tourSpotLongitude = createNumber("tourSpotLongitude", Double.class);

    public final StringPath tourSpotName = createString("tourSpotName");

    public final StringPath tourSpotParking = createString("tourSpotParking");

    public final StringPath tourSpotPhotoUrl = createString("tourSpotPhotoUrl");

    public final StringPath tourSpotSummary = createString("tourSpotSummary");

    public final StringPath tourSpotTime = createString("tourSpotTime");

    public final NumberPath<Integer> tourSpotTwoDepthCode = createNumber("tourSpotTwoDepthCode", Integer.class);

    public QTourSpot(String variable) {
        super(TourSpot.class, forVariable(variable));
    }

    public QTourSpot(Path<? extends TourSpot> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTourSpot(PathMetadata metadata) {
        super(TourSpot.class, metadata);
    }

}

