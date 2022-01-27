package com.ssafy.tourtogether.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 관광지 모델 정의.
 */
@Entity
@Getter
@Setter
public class TourSpot extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int tourSpotId;
	String tourSpotName;
	String tourSpotSummary;
	String tourSpotAddress;
	int tourSpotTwoDepthCode;
	String tourSpotPhotoUrl;
	double tourSpotLatitude;
	double tourSpotLongitude;
	String tourSpotTime;
	String tourSpotFee;
	String tourSpotHoliday;
	String tourSpotParking;
	Boolean tourSpotIsTop100;
}
