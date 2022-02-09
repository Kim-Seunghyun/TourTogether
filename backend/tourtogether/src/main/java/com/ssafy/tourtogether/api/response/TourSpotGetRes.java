package com.ssafy.tourtogether.api.response;

import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.TourSpot;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 모든 관광지 정보 불러오는 API ([GET] /tourspot) 요청에 대한 응답값 정의
 */
@Getter
@Setter
@ApiModel("TourSpotGetResponse")
public class TourSpotGetRes extends BaseResponseBody {
//	@ApiModelProperty(name = "응답 코드", example = "200")
	List<TourSpot> tourSpotList = null;

	public static TourSpotGetRes of(Integer statusCode, String message, List<TourSpot> tourSpotList) {
		TourSpotGetRes res = new TourSpotGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setTourSpotList(tourSpotList);
		return res;
	}
}
