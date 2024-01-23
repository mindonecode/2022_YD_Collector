package mone.db.dto;

import lombok.Data;

/**
 * 프로세스 상태 현황
 * @author iby
 *
 */
@Data
public class TnProcsSttusDto {

	// 수집일시
    private String colct_dt;
    // 프로세스 ID
    private String procs_id;
    // 프로세스 상태코드
    private String procs_sttus_cd;


}
