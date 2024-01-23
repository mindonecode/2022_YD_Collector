package mone.db.dto;

import lombok.Data;

/**
 * 초단기실황 응답메시지
 * @author iby
 *
 */
@Data
public class UltraSrtFcstResDTO {
	private String reqDate;
	private String reqTime;
	private String baseDate;
	private String baseTime;
	private String regionAreaCd;
	private String districtNm1;
	private String districtNm2;
	private String nx;
	private String ny;
	private double lng;
	private double lat;
	private String t1h;
	private String rn1;
	private String uuu;
	private String vvv;
	private String reh;
	private String pty;
	private String vec;
	private String wsd;
	private String fcstTime;
	private String fcstDate;
	private String fcstValue;
	private String fcstClct;
	private String flag;
	private String sky;
	private String lgt;

}
