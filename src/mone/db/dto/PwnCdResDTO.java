package mone.db.dto;

import lombok.Data;

/**
 * 특보코드조회 응답
 * @author iby
 *
 */
@Data
public class PwnCdResDTO {

	// 요청일자
	private String reqDate;
	// 요청시각
	private String reqTime;
	// 특보구역코드
	private String areaCd;
	// 특보구역(대분류)
	private String area;
	// 특보구역명
	private String areaNm;
	// (요청)발표시각(from)
	private String fromTmFc;
	// (요청)발표시각(to)
	private String toTmFc;
	// 특보종류 - 1:강풍, 2:호우, 3:한파, 4:건조, 5:폭풍해일, 6:풍량, 7:태풍, 8:대설, 9:황사, 12:폭염
	private String warnType;
	// 지점코드
	private String stnId;

	// 응답 메시지코드
	private String resultCode;
	// 응답 메시지 설명
	private String resultMsg;
	// 발표시각(년월일시분)
	private String tmFc;
	// 발표번호(월별)
	private String tmSeq;
	// 특보 강도(0:주의보, 1:경보)
	private String warnStress;
	// 특보발표코드(1:발표, 2:해제, 3:연장, 6:정정, 7:변경발표, 8:변경해제)
	private String command;
	// 발표발효시각(특보발표시에만 제공)
	private String startTime;
	// 헤제발효시각(특보발표시에만 제공)
	private String endTime;
	// 전체특보해제시각
	private String allEndTime;
	// 취소구분(0:정상, 1:취소된 특보)
	private String cancel;


}
