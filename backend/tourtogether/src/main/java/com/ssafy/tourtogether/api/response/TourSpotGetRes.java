package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

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
	public static TourSpotGetRes of(Integer statusCode, String message) {
		TourSpotGetRes res = new TourSpotGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		return res;
	}
}
