package com.ssafy.tourtogether.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.response.TourSpotGetRes;
import com.ssafy.tourtogether.api.service.TourSpotService;
import com.ssafy.tourtogether.db.entity.TourSpot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 관광지 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "관광지 API", tags = { "TourSpot" })
@RestController
@RequestMapping("/api/tourspot")
public class TourSpotController {
	@Autowired
	TourSpotService tourSpotService;

	@GetMapping
	@ApiOperation(value = "모든 관광지 정보", notes = "DB에 저장된 모든 관광지 정보를 불러온다.")
//	@ApiResponse({
//		// TODO 필요한 response 종류와 메세지 작성 
//	})
	public ResponseEntity<TourSpotGetRes> getAllTourSpot() throws Exception {
		List<TourSpot> tourSpotList = tourSpotService.getAllTourSpot();
		return ResponseEntity.ok(TourSpotGetRes.of(200, "Success", tourSpotList));
	}
}
