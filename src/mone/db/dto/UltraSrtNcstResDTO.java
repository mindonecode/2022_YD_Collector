package mone.db.dto;

import lombok.Data;

/**
 * 초단기실황 응답메시지
 * @author iby
 *
 */
@Data
public class UltraSrtNcstResDTO {
	// 요청일자
	private String reqDate;
	// 요청시각
	private String reqTime;
	// 발표일자
	private String baseDate;
	// 발표시각
	private String baseTime;
	// 행정구역코드 (WZ_ID)
	private String regionAreaCd;
	// 행정구역명_1단계
	private String districtNm1;
	// 행정구역명_2단계
	private String districtNm2;
	// 예보지점 x좌표
	private String nx;
	// 예보지점 y좌표
	private String ny;
	// 경도(x)
	private double lng;
	// 위도(y)
	private double lat;

	// 기온(℃)
	private String t1h;
	// 1시간 강수량(mm)
	private String rn1;
	// 동서바람성분(m/s)
	private String uuu;
	// 남북바람성분(m/s)
	private String vvv;
	// 습도(%)
	private String reh;
	// 강수형태(0:없음, 1:비, 2:비/눈, 3:눈, 4:소나기, 5:빗방울, 6:빗방울/눈날림, 7:눈날림)
	private String pty;
	// 풍향(m/s)
	private String vec;
	// 풍속(1)
	private String wsd;

	private String fcstTime;
	private String fcstDate;
	private String fcstValue;

	private String fcstClct;
	private String flag;

	private String sky;
	private String lgt;

}
