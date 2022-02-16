package com.ssafy.tourtogether.api.response;

import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.ScheduleDB;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 ID로 보드 불러오기 API ([GET] /board/searchByBoardID) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("ScheduleSearchByBoardIdPostRes")
public class ScheduleSearchByBoardIdPostRes extends BaseResponseBody {
	List<ScheduleDB> schedule = null;

	public static ScheduleSearchByBoardIdPostRes of(Integer statusCode, String message, List<ScheduleDB> schedule) {
		ScheduleSearchByBoardIdPostRes res = new ScheduleSearchByBoardIdPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setSchedule(schedule);
		return res;
	}
}
