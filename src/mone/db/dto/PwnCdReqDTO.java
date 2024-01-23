package mone.db.dto;

import lombok.Data;

/**
 * 특보코드조회 요청
 * 특보구역코드 리스트
 * @author iby
 *
 */
@Data
public class PwnCdReqDTO {

	// 특보구역코드
	private String areaCd;
//	// 특보구역(대분류)
//	private String area;
	// 특보구역명
	private String areaNm;
	//
	private int num;

}
