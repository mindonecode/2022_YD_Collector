package mone.db.dto;

import lombok.Data;

/**
 * 초단기실황 요청 파라미터
 * @author iby
 *
 */
@Data
public class UltraSrtFcstReqDTO {

	// 예보지점 x좌표
	private String nx;
	// 예보지점 y좌표
	private String ny;
	// 행정구역코드
	private String regionAreaCd;

	private String  t1h;
	private String  rn1;
	private String  reh;
	private String  vec;
	private String  wsd;
	private String  pty;
	private String  clctDt;
	private String  sky;
	private String  lgt;
	private String  fcstDt;

}
